package com.example.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.app.filter.AuthFilter;

@Configuration
public class ValidationConfig implements WebMvcConfigurer {
	
	// バリデーションメッセージのカスタマイズ
	@Override
	public Validator getValidator() {
		var validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	@Bean
	ResourceBundleMessageSource messageSource() {
		var messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("validation");
		return messageSource;
	}
	
	// 認証用フィルタの有効化
	@Bean
	FilterRegistrationBean<AuthFilter> authFilterRegistration(AuthFilter authFilter) {
		FilterRegistrationBean<AuthFilter> bean = new FilterRegistrationBean<>(authFilter);
		bean.addUrlPatterns("/admin/*", "/rental/*"); // 対象URL
		bean.setOrder(1); // フィルタ実行順
		return bean;
	}

}
