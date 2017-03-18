package com.doubi.lottery;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.doubi.lottery.dao.TestDAO;
import com.mongodb.client.model.Filters;

@RestController
public class TestController {

	@Autowired
	private TestDAO testDao;
	
	
	@ResponseBody
	@RequestMapping(value="/test")
	public String test(){
		testDao.count("test", Filters.eq("aa", "aa"));
		return "aaa";
	}
	
	
}
