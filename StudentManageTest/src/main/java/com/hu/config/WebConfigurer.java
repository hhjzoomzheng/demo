package com.hu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.hu.config.intercepors.LoginInterceptor;
 
@Configuration
public class WebConfigurer  implements WebMvcConfigurer {

	  @Autowired
	  private LoginInterceptor loginInterceptor;
 
  // 这个方法是用来配置静态资源的，比如html，js，css，等等
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  registry.addResourceHandler("/sutdentmanage/headPic/**").addResourceLocations("file:D:/sutdentmanage/headPic/");

  }
 
  // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
	  registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/index.html","/login", "/regist","/register","/css/**","/js/**","/sutdentmanage/headPic/**","/registName","/getVerify","/checkVerify","/hello.html");

  }
}