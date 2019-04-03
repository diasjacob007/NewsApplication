package com.stackroute.newsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.stackroute.newsapp.filter.JWTFilter;

@SpringBootApplication
public class NewsAppApplication {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JWTFilter());
		registrationBean.addUrlPatterns("/api/*");
		return registrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(NewsAppApplication.class, args);
	}
}
