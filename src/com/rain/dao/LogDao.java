package com.rain.dao;

import java.util.ArrayList;

import com.rain.util.SQLUtil;

public class LogDao {

	public boolean addLoginLog(String content) {
		String sql = "insert into `log`(content,createTime) values(?,now())";
		Object[] paramArray = new Object[1];
		paramArray[0] = content;

		return SQLUtil.getInstance().update(sql, paramArray);
	}

	public ArrayList<String[]> getList() {

		String sql = "select * from log ";

		return SQLUtil.getInstance().search(sql);
	}
}
