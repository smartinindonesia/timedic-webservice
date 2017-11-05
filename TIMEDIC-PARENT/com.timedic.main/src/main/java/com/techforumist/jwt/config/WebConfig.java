package com.techforumist.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry; 
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView; 

@Configuration 
public class WebConfig extends WebMvcConfigurerAdapter
{ 
	@Override 
	public void addResourceHandlers(final ResourceHandlerRegistry registry) 
	{ 
		registry.addResourceHandler("/*.js/**").addResourceLocations("/static/"); 
		registry.addResourceHandler("/*.css/**").addResourceLocations("/static/");
	}
	
	@Override 
	public void addViewControllers(ViewControllerRegistry registry)
	{ 
		registry.addViewController("/").setViewName("login"); 
		registry.addViewController("/login").setViewName("login"); 
	} 
	
	@Bean 
	public InternalResourceViewResolver setupViewResolver()
	{ 
		InternalResourceViewResolver resolver = new InternalResourceViewResolver(); 
		resolver.setPrefix ("/static/app/views"); 
		resolver.setSuffix (".html");
		resolver.setViewClass (JstlView.class); return resolver; 
	} 
}