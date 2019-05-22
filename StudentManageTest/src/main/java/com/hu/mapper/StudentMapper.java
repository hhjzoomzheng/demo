package com.hu.mapper;

import java.util.List;
import java.util.Map;
import com.hu.model.Account;
import com.hu.model.Student;

public interface StudentMapper {

	// 注册默认信息
	Integer regist(Map<String, Object>map);

	// 学生列表
	List<Student> studentList();

	// 信息修改
	int update(Student student);

	// 信息查询
	Student select(Student student);

	// 查找
	List<Student> selectName(Student student);

	//删除学生
	public void deleteStudents(Student student);
	public void deleteAccount(Account account);
}
