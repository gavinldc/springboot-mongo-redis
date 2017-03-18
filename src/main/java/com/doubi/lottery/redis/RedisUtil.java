package com.doubi.lottery.redis;



import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Redis
 * 
 * @author Gavin
 *
 * 2014年10月2日
 */
@Component
public class RedisUtil {

	protected Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private RedisConfigration redisConfig;
	
	/** 
     * 私有构造器. 
     */  
    public RedisUtil() {  
          
    }  
    private  Map<String,JedisPool> maps  = new HashMap<String,JedisPool>(); 
    
    /**
     * get redis connect pool
     * @param ip
     * @param port
     * @return
     */
    private JedisPool getPool(String ip,int port) {  
        String key = ip+":" +port;  
        JedisPool pool = null;  
        if(!maps.containsKey(key)) {  
            JedisPoolConfig config=new JedisPoolConfig();
            config.setMaxTotal(redisConfig.getMaxTotal());
            config.setMaxIdle(redisConfig.getMaxIdle());
            config.setMaxWaitMillis(redisConfig.getMaxWait());
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
            try{    
                pool = new JedisPool(config, ip, port,redisConfig.getMaxTimeOut());  
                maps.put(key, pool);  
            } catch(Exception e) {  
                e.printStackTrace();  
            }  
        }else{  
            pool = maps.get(key);  
        }  
        return pool;  
    }   
    
    public Jedis getJedis() {  
        Jedis jedis  = null;  
        int count =0;  
        do{  
            try{   
                jedis = getPool(redisConfig.getIp(),redisConfig.getPort()).getResource();  
                //log.info("get redis master1!");  
            } catch (Exception e) {  
                log.error("get redis master1 failed!", e);  
                 // 销毁对象    
                getPool(redisConfig.getIp(),redisConfig.getPort()).close();  
            }  
            count++;  
        }while(jedis==null&&count<redisConfig.getRetryNum());  
        return jedis;  
    }  
    
    /**
     * 释放redis 实例
     * @param jedis
     * @param ip
     * @param port
     */
    public void closeJedis(Jedis jedis,String ip,int port) {  
        if(jedis != null) {  
            getPool(ip,port).close();
        }  
    }  
   
}

