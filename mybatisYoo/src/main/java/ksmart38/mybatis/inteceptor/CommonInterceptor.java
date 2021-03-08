package ksmart38.mybatis.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component //가장큰개념이다?
public class CommonInterceptor implements HandlerInterceptor{
	
	private static final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		log.info("CommonInterceptor==============================START");
		log.info("ACCESS INFO====================================START");
		log.info("PORT          	::  :::::::::::::::: {}",request.getServerName());
		log.info("method	:::::::::::::::::::: {}",request.getServerPort());
		log.info("server name	  :::::::::: {}",request.getMethod());
		log.info("URI		:::::::::::::::: {}",request.getRequestURI());
		log.info("ACCESS INFO====================================END");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("CommonInterceptor==============================END");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("CommonInterceptor==============================AFTER");
	}

}