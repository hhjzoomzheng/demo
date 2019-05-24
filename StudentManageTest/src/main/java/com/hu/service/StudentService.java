package com.hu.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hu.mapper.StudentMapper;
import com.hu.model.Account;
import com.hu.model.Student;

@Service
public class StudentService {
	@Autowired
	private StudentMapper studentMapper;
	@Value("${savePathString}")
	private String savePathString;

	public Integer regist(Map<String, Object>map) {
		return studentMapper.regist(map);
	}

	public List<Student> studentList() {
		return studentMapper.studentList();
	}

	public int studentUpdate(Student student,MultipartFile headPicload) {
		if (!"".equals(headPicload.getOriginalFilename())) {
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			// 获得文件原始名称
			String fileName = headPicload.getOriginalFilename();
			// 获得文件后缀名称
			String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			// 生成最新的uuid文件名称
			String newFileName = uuid + "." + suffixName;		
			String picString = savePathString + "/" + newFileName;
			String h=selectPic(student);			
			String pic=savePathString+ "/" +h;
			
			try {
				File file1 = new File(pic);
				file1.delete();
				headPicload.getOriginalFilename();
				File file = new File(savePathString + "/" + headPicload.getOriginalFilename());
				if (!file.exists() && headPicload != null && !headPicload.isEmpty()) {
					
					InputStream inputStream = headPicload.getInputStream();
					FileOutputStream out = new FileOutputStream(picString);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = inputStream.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					inputStream.close();
					// 关闭输出流
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			student.setHeadPic(newFileName);
		}else {
			String h=selectPic(student);
			student.setHeadPic(h);
		}
				
		int a=studentMapper.update(student);
		
		return a;
	}

	public Student select(Student student) {
		return studentMapper.select(student);
	}
	@Transactional
	public List<Student> selectName(Student student) {
		return studentMapper.selectName(student);
	}
	
	public String selectPic(Student student) {
		Student s=studentMapper.selectPic(student);
		String headPic1=s.getHeadPic();
		return headPic1;
	}

	public void deleteStudent(Student student) {
		studentMapper.deleteStudents(student);
	}
	public void deleteAccount(Account account) {
		studentMapper.deleteAccount(account);
	}
}
