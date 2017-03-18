package com.doubi.lottery.redis;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;


/**
 * cache operator by jedis
 * 
 * @author Gavin
 *
 * 2014年10月13日
 */

@Component
public class CacheUtil {

	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * 删除在List中的元素(出栈)
	 * HJ
	 * @param name
	 * @param i
	 * @param value
	 */
	public void deleteOnList(String name,long i, String value) {
		Jedis jedis=redisUtil.getJedis();
		jedis.lrem(name, i, value);
		jedis.close();
	}

	/**
	 * 获取List中所有的值
	 * HJ
	 * @param key
	 * @return
	 */
	public List<String> getList(String key){
		Jedis jedis=redisUtil.getJedis();
		List<String> list= jedis.lrange(key, 0, -1);
		jedis.close();
		return list;
	}
	
	
	/**
	 * 保存单个值到List
	 * HJ
	 * @param name
	 * @param value
	 */
	public void saveToList(String name ,String value){
		Jedis jedis=redisUtil.getJedis();
		jedis.lpush(name, value);
		jedis.close();
	}
	
	/**
	 * save map
	 * @param key
	 * @param map
	 */
	public void saveMap(String key,Map<String,String> map){
		Jedis jedis=redisUtil.getJedis();
		jedis.hmset(key, map);
		jedis.close();
	}
	
	/**
	 * create k，field，value
	 * @param key
	 * @param field
	 * @param value
	 */
	public void saveKFV(String key,String field,String value){
		Jedis jedis=redisUtil.getJedis();
		jedis.hset(key, field, value);
		jedis.close();
	}
	
	/**
	 * create the only code by redis
	 * redis server is singleton
	 * @param key
	 * @return
	 */
	public long getKeyId(String key){
		Jedis jedis=redisUtil.getJedis();
		long id=jedis.incr(key);
		jedis.close();
		return id;
	}
	
	/**
	 * save key-value
	 * @param key
	 * @param value
	 */
	public void save(String key,String value){
		Jedis jedis=redisUtil.getJedis();
		jedis.set(key, value);
		jedis.close();
	}
	
	/**
	 * get string value by key
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		Jedis jedis=redisUtil.getJedis();
		String value=jedis.get(key);
		jedis.close();
		return value;
	}
	
	/**
	 * get all Map<String,String> by key
	 * @param key
	 * @return
	 */
	public Map<String,String> getMap(String key){
		Jedis jedis=redisUtil.getJedis();
		Map<String,String> map=jedis.hgetAll(key);
		jedis.close();
		return map;
	}
	
	/**
	 * get value by key and field
	 * @param key
	 * @param field
	 * @return
	 */
	public String getChildValue(String key,String field){
		Jedis jedis=redisUtil.getJedis();
		String value=jedis.hget(key, field);
		jedis.close();
		return value;
	}
	
	/**
	 * exists by key and field
	 * @param key
	 * @param field
	 * @return
	 */
	public boolean exists(String key,String field){
		Jedis jedis=redisUtil.getJedis();
		boolean result=jedis.hexists(key, field);
		if(result)
		{
			if(jedis.hget(key, field)==null)
			{
				result=false;
			}
		}
		
		jedis.close();
		return result;
	}
	
	/**
	 * add zset
	 * @param key
	 * @param score
	 * @param member
	 */
	public void addSortSet(String key,double score,String member){
		Jedis jedis=redisUtil.getJedis();
		jedis.zadd(key, score, member);
		jedis.close();
	}
	
	/**
	 * remove zset by member
	 * @param key
	 * @param member
	 */
	public void removeSortSet(String key,String member){
		Jedis jedis=redisUtil.getJedis();
		jedis.zrem(key, member);
		jedis.close();
	}
	
	/**
	 * get all elements from jedis by key
	 * @param key
	 * @return
	 */
	public Set<String> getZSets(String key){
		Jedis jedis=redisUtil.getJedis();
		Set<String> set= jedis.zrange(key, 0, -1);
		jedis.close();
		return set;
	}
	
	/**
	 * 向set中添加元素
	 * @param key
	 * @param member
	 */
	public void addSet(String key,String member){
		Jedis jedis=redisUtil.getJedis();
		jedis.sadd(key, member);
		jedis.close();
	}
	
	/**
	 * 返回key中set的集合
	 * @param key
	 * @return
	 */
	public Set<String> getSets(String key){
		Jedis jedis=redisUtil.getJedis();
		Set<String> sets=jedis.smembers(key);
		jedis.close();
		return sets;
	}
	
	/**
	 * 随机返回set中的一个元素，并删除
	 * @param key
	 * @return
	 */
	public String spop(String key){
		Jedis jedis=redisUtil.getJedis();
		String res=jedis.spop(key);
		jedis.close();
		return res;
	}
	
	/**
	 * 删除名称为key的set中的元素member
	 * @param key
	 * @param member
	 */
	public void removeSet(String key,String member){
		Jedis jedis=redisUtil.getJedis();
        jedis.srem(key, member);	 
		jedis.close();
	}
	
	/**
	 * delete special key
	 * @param key
	 */
	public void removeKV(String key){
		Jedis jedis=redisUtil.getJedis();
		jedis.del(key);
		jedis.close();
	}
	
	/**
	 * delete special field to k-map
	 * @param key
	 * @param field
	 */
    public void removeFild(String key,String field){
    	Jedis jedis=redisUtil.getJedis();
    	jedis.hdel(key, field);
    	jedis.close();
    }
	
	/**
	 * 设置key的过期时间
	 * @param key
	 * @param seconds
	 */
	public void expire(String key,int seconds){
		Jedis jedis=redisUtil.getJedis();
		jedis.expire(key, seconds);
		jedis.close();
	}
	
	/**
	 * 返回符合规则的key
	 * @param repx
	 * @return
	 */
	public Set<String> keys(String repx){
		Jedis jedis=redisUtil.getJedis();
		Set<String> sets=jedis.keys(repx);
		jedis.close();
		return sets;
	}
	
}
