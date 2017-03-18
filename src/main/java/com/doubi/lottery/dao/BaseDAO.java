package com.doubi.lottery.dao;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.DBObject;

public interface BaseDAO {

	public int count(String collectionName,Bson bson);

	public List<DBObject> findByPage(String collectionName, Bson bson, int page, int size)
			throws UnknownHostException;

	public void update(String collectionName, DBObject updateCondition, DBObject updateValue)
			throws UnknownHostException;

	public List<DBObject> find(String collectionName, DBObject query, DBObject keys) throws UnknownHostException;

	public void save(DBObject obj, String collectionName) throws UnknownHostException;
}
