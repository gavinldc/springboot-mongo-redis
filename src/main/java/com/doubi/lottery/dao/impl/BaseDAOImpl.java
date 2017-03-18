package com.doubi.lottery.dao.impl;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import com.doubi.lottery.dao.BaseDAO;
import com.doubi.lottery.mongo.MongoUtils;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;

public class BaseDAOImpl implements BaseDAO{

	/**
	 * 数据库名称
	 */
	private String dbname="master1";
	/**
	 * 在自己的实现类里边覆盖该方法重置dbname
	 * @param dbname
	 */
	protected void setDBname(String dbname){
		this.dbname=dbname;
	}
	
	@Autowired
	private MongoUtils mongoUtil;
	
	@Override
	public int count(String collectionName, Bson bson) {
		if (collectionName == null || bson == null)
			return 0;
		MongoDatabase db=null;
		try {
			db = mongoUtil.getDB(dbname);
			return (int) db.getCollection(collectionName).count(bson);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<DBObject> findByPage(String collectionName, Bson bson, int page, int size)
			throws UnknownHostException {
		return null;
	}

	@Override
	public void update(String collectionName, DBObject updateCondition, DBObject updateValue)
			throws UnknownHostException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DBObject> find(String collectionName, DBObject query, DBObject keys) throws UnknownHostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(DBObject obj, String collectionName) throws UnknownHostException {
		// TODO Auto-generated method stub
		
	}

}
