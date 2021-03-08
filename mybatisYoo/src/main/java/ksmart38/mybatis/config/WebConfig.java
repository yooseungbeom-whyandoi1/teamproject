package ksmart38.mybatis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ksmart38.mybatis.inteceptor.CommonInterceptor;
import ksmart38.mybatis.inteceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	private final CommonInterceptor commonInterceptor;
	private final LoginInterceptor loginInterceptor;
	public WebConfig (CommonInterceptor commonInterceptor,LoginInterceptor loginInterceptor) {
		this.commonInterceptor =commonInterceptor;
		this.loginInterceptor =loginInterceptor;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(commonInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/")
				.excludePathPatterns("/favicon.ico")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/fonts/**")
				.excludePathPatterns("/img/**")
				.excludePathPatterns("/js/**");
		
		
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				.addPathPatterns("/ajax/**")
				.excludePathPatterns("/favicon.ico")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/fonts/**")
				.excludePathPatterns("/img/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/login")
				.excludePathPatterns("/logout")
				.excludePathPatterns("/addMember");
//				.excludePathPatterns("/memberList");
	}
}
