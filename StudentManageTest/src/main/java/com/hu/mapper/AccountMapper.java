package com.hu.mapper;

import java.util.Map;
import com.hu.model.Account;

public interface AccountMapper {
	
	/**
	 *  账号登录
	 * @param account
	 * @return
	 * @date 
	 * @author LENOVO
	 */
	Map<String,Object> login(Map<String,Object> map);

	// 账号注册
	Integer regist(Map<String,Object> map);

	// 账号查重
	Map<String,Object> select(Map<String,Object> map);
	//Ajax
	Account registName(Account account);
}
