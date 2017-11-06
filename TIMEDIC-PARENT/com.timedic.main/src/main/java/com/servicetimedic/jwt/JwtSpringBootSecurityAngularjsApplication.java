package com.servicetimedic.jwt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import com.servicetimedic.jwt.domain.posgresql.AppUser;
import com.servicetimedic.jwt.jpa.JpaConfiguration;
import com.servicetimedic.jwt.repository.UserDbRepository;



@Import({JpaConfiguration.class})
@SpringBootApplication(scanBasePackages={"com"})
public class JwtSpringBootSecurityAngularjsApplication extends SpringBootServletInitializer 
{
	@Autowired
	private static UserDbRepository repo ;
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
	    return application.sources(new Class[] { JwtSpringBootSecurityAngularjsApplication.class });
	}
	  
	public void onStartup(ServletContext servletContext) throws ServletException
	  {
	    System.setProperty("spring.profiles.active", "prod");
	    super.onStartup(servletContext);
	  }
	
	/*
	public void run(String... args) throws Exception {
		
	}
	*/

	public static void main(String[] args) 
	{
		System.setProperty("spring.profiles.active", "prod");
		SpringApplication.run(JwtSpringBootSecurityAngularjsApplication.class, args);
		
		/*
		List<String> role = new ArrayList<String>();
		role.add("ROLE_ADMIN");
		
		AppUser firstData = new AppUser();
		firstData.setFrontName("Cakra");
		firstData.setEmail("adipurapunya@gmail.com");
		firstData.setUsername("admin");
		firstData.setPassword("admin");
		firstData.setRoles(role);
		
		repo.save(firstData);
		
		System.out.println(firstData.getEmail());
		*/
	}
}
