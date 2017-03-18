package com.doubi.lottery.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="mongo")
public class MongoConfigration {

	private String hosts;
	private String ports;
	private String dbnames;
	private String usernames;
	private String passwords;
	public String getHosts() {
		return hosts;
	}
	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
	public String getPorts() {
		return ports;
	}
	public void setPorts(String ports) {
		this.ports = ports;
	}
	public String getDbnames() {
		return dbnames;
	}
	public void setDbnames(String dbnames) {
		this.dbnames = dbnames;
	}
	public String getUsernames() {
		return usernames;
	}
	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	
	
	
}
