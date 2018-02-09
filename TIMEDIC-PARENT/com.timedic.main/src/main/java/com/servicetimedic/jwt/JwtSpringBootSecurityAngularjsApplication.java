package com.servicetimedic.jwt;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.servicetimedic.jwt.jpa.JpaConfiguration;

@Import({JpaConfiguration.class})
@SpringBootApplication(scanBasePackages={"com"})
public class JwtSpringBootSecurityAngularjsApplication extends SpringBootServletInitializer
{
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
	    return application.sources(new Class[] { JwtSpringBootSecurityAngularjsApplication.class });
	}
	
	public void onStartup(ServletContext container) throws ServletException {
		//AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		//ctx.setServletContext(container);
		//ServletRegistration.Dynamic servlet = container.addServlet(arg0, arg1);
		System.setProperty("spring.profiles.active", "prod"); 
		super.onStartup(container);
	}

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "prod");
		SpringApplication.run(JwtSpringBootSecurityAngularjsApplication.class, args);
	}	
}
