package com.servicetimedic.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

import com.servicetimedic.jwt.jpa.JpaConfiguration;



@Import({JpaConfiguration.class})
@SpringBootApplication(scanBasePackages={"com"})
public class JwtSpringBootSecurityAngularjsApplication
{
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
	    return application.sources(new Class[] { JwtSpringBootSecurityAngularjsApplication.class });
	}
	  
	/*
	public void onStartup(ServletContext servletContext) throws ServletException
	{
	    System.setProperty("spring.profiles.active", "prod");
	    //super.onStartup(servletContext);
	}
	*/
	

	public static void main(String[] args) 
	{
		System.setProperty("spring.profiles.active", "prod");
		SpringApplication.run(JwtSpringBootSecurityAngularjsApplication.class, args);
		
	}
}
