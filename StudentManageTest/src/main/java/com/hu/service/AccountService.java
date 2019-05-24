package com.hu.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hu.mapper.AccountMapper;
import com.hu.model.Account;

@Service
public class AccountService {
	
   @Autowired
   private AccountMapper accountMapper;
   
   public Integer regist(Map<String,Object> map) {
	   return accountMapper.regist(map);
   }
   public Map<String,Object> select(Map<String,Object> map) {
	   return accountMapper.select(map);
   }
   public Map<String,Object> login(Map<String,Object> map) {
	   return accountMapper.login(map);
   }
   
   /**
    * 
    * @param account
    * @return
    */
   public Account registName(Account account){
	   return accountMapper.registName(account);
   }

}
