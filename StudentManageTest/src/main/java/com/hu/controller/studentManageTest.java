package com.hu.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.hu.model.Account;
import com.hu.model.Student;
import com.hu.service.AccountService;
import com.hu.service.StudentService;
import com.hu.util.RandomValidateCodeUtil;

@Controller
public class studentManageTest {
	@Autowired
	private StudentService studentService;
	@Autowired
	private AccountService accountService;

	@RequestMapping("register")
	public ModelAndView register(@RequestParam Map<String, Object> map, ModelAndView modelAndView) {
		String account = (String) map.get("account");
		String password = (String) map.get("password");
		if ("".equals(account) && "".equals(password)) {
			modelAndView.setViewName("registFail");
			return modelAndView;
		} else if (account == null && account == null) {
			modelAndView.setViewName("registFail");
			return modelAndView;
		} else {
			Map<String, Object> select = accountService.select(map);
			if (select == null) {
				// UUID赋值
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				map.put("id", uuid);
				String gradeid = UUID.randomUUID().toString().replaceAll("-", "");
				map.put("gradeId", gradeid);
				Integer regist = accountService.regist(map);
				Integer studentInteger = studentService.regist(map);
				if (regist > 0 && studentInteger > 0) {
					modelAndView.setViewName("registsuccess");
				} else {
					modelAndView.setViewName("registFail");
				}
				return modelAndView;
			} else {
				modelAndView.setViewName("registFail");
				return modelAndView;
			}
		}
	}

	@RequestMapping("regist")
	public String regist() {
		return "regist";
	}

	@RequestMapping("login")
	public ModelAndView Login(@RequestParam Map<String, Object> map, ModelAndView modelAndView, HttpSession session) {
		String inputStr = (String) map.get("verifyInput");
		String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
		String account = (String) map.get("account");
		String password = (String) map.get("password");
		if (random.equals(inputStr)) {
			if (account != null && password != null) {
				Map<String, Object> loginUser = accountService.login(map);
				if (loginUser != null) {
					// 将用户对象添加到Session中
					session.setAttribute("session_user", account);
					// 重定向到主页面的跳转方法
					modelAndView.setViewName("mine");
				} else {
					modelAndView.setViewName("loginFail");
				}
			}
		} else {
			modelAndView.setViewName("YzmFail");
		}

		return modelAndView;
	}

	@RequestMapping("studentList")
	public String studentList(Model model) {
		List<Student> list = studentService.studentList();
		model.addAttribute("studentList", list);
		return "studentList";
	}

	@RequestMapping("update")
	public ModelAndView update(Model model, Student student, ModelAndView modelAndView) {
		Student stu = studentService.select(student);
		model.addAttribute("update", stu);
		modelAndView.setViewName("update");
		return modelAndView;
	}

	@RequestMapping("studentUpdate")
	public String studentUpdate(Model model, Student student, MultipartFile headPicload, BindingResult bindingResult) {
		studentService.studentUpdate(student, headPicload);
		Student stu = studentService.select(student);
		model.addAttribute("personalDetails", stu);
		return "personalDetails";
	}

	@PostMapping("selectName")
	@ResponseBody
	@Transactional
	public List<Student> selectName(Model model, Student student) {
		List<Student> list = studentService.selectName(student);
		model.addAttribute("studentList", list);
		return list;
	}

	@RequestMapping("deleteStudent")
	public Model selectPhone(Model model, Student student, ModelAndView modelAndView, Account account) {
		studentService.deleteStudent(student);
		studentService.deleteAccount(account);
		List<Student> list = studentService.studentList();
		model.addAttribute("studentList", list);
		return model;
	}

	@RequestMapping("personalDetails")
	public ModelAndView personalDetails(Model model, Student student, ModelAndView modelAndView) {
		Student stu = studentService.select(student);
		model.addAttribute("personalDetails", stu);
		modelAndView.setViewName("personalDetails");
		return modelAndView;
	}

	@RequestMapping("return")
	public ModelAndView Return(ModelAndView modelAndView, Model model) {
		List<Student> list = studentService.studentList();
		model.addAttribute("studentList", list);
		modelAndView.setViewName("studentList");
		return modelAndView;
	}

	@RequestMapping("registName")
	@ResponseBody
	public Account registerSelectName(Account account, Model model) {
		if ("".equals(account.getAccount())) {
			account.setAccount("1");
			return account;
		} else {
			Account acc = accountService.registName(account);
			model.addAttribute("regist", acc);
			return acc;
		}
	}

	@RequestMapping(value = "getVerify")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
			response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
			randomValidateCode.getRandcode(request, response);// 输出验证码图片方法
		} catch (Exception e) {

		}
	}

	/**
	 * 校验验证码
	 */
	@RequestMapping(value = "checkVerify", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public boolean checkVerify(@RequestParam String verifyInput, HttpSession session) {
		try {
			// 从session中获取随机数
			String inputStr = verifyInput;
			String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
			if (random == null) {
				return false;
			}
			if (random.equals(inputStr)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request,ModelAndView modelAndView){
		HttpSession session = request.getSession();//获取当前session
		if(session!=null){
			request.getSession().removeAttribute("session_user");
			request.getSession().invalidate();
		}
		 modelAndView.setViewName("/index.html");
		return modelAndView;		
	}

}
