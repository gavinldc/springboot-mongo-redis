package com.doubi.lottery.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

/**
 * 通过spring容器来保证单例
 * 
 * @author gavin
 *
 */
@Component
public class MongoUtils {

	@Autowired
	private MongoConfigration configration;

	private MongoClient mongo;

	public synchronized MongoDatabase getDB(String dbname) throws UnknownHostException {

		if (mongo == null) {
			/**
			 * 从配置文件里边拿集群的host，和端口号 从配置里边拿数据库的用户名密码
			 */
			List<ServerAddress> seeds = new ArrayList<ServerAddress>();
			String[] hosts = configration.getHosts().split(",");
			String[] ports = configration.getPorts().split(",");
			for (int i = 0; i < hosts.length; i++) {
				ServerAddress seed = new ServerAddress(hosts[i], Integer.parseInt(ports[i]));
				seeds.add(seed);
			}
			String[] dbnames = configration.getDbnames().split(",");
			String[] usernames = configration.getUsernames().split(",");
			String[] passwords = configration.getPasswords().split(",");
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			for (int i = 0; i < dbnames.length; i++) {
				MongoCredential cred = MongoCredential.createScramSha1Credential(usernames[i], dbnames[i],
						passwords[i].toCharArray());
				credentials.add(cred);
			}
			try {
				mongo = createSession(seeds, credentials);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return mongo.getDatabase(dbname);
	}

	private MongoClient createSession(List<ServerAddress> seeds, List<MongoCredential> credentials)
			throws UnknownHostException {
		MongoClientOptions.Builder build = new MongoClientOptions.Builder();
		build.connectionsPerHost(50);
		build.connectTimeout(5000);
		build.maxWaitTime(5000);
		build.threadsAllowedToBlockForConnectionMultiplier(4);
		MongoClientOptions myOptions = build.build();
		MongoClient mongo = new MongoClient(seeds, credentials, myOptions);
		return mongo;
	}

	public synchronized void closeAllSession() {
		mongo.close();
	}

}
