package com.servicetimedic.jwt;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import com.servicetimedic.jwt.jpa.JpaConfiguration;

@Import({JpaConfiguration.class})
@SpringBootApplication(scanBasePackages={"com"})
public class JwtSpringBootSecurityAngularjsApplication extends SpringBootServletInitializer
{
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
	    return application.sources(new Class[] { JwtSpringBootSecurityAngularjsApplication.class });
	}
	
	public void onStartup(ServletContext container) throws ServletException {
		System.setProperty("spring.profiles.active", "prod"); 
		super.onStartup(container);
	}

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "prod");
		SpringApplication.run(JwtSpringBootSecurityAngularjsApplication.class, args);
	}	
}
