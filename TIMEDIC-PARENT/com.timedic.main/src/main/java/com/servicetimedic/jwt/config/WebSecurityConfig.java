package com.servicetimedic.jwt.config;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Web security configuration class
 * 
 * @author Sarath Muraleedharan
 *
 */
//@Configurable
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
// Modifying or overriding the default spring boot security.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private SessionRegistry sessionRegistry;
	
	@Autowired
    ServletContext servletContext;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	LogoutSuccess logoutSuccess;
	
	// This method is for overriding some configuration of the WebSecurity
	// If you want to ignore some request or request patterns then you can
	// specify that inside this method
	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring()
				// ignoring the "/", "/index.html", "/app/**", "/register",
				// "/favicon.ico"
				.antMatchers("/", "/index.html", "/app/**", "/register", "/authenticate", "/favicon.ico");
	}

	// This method is used for override HttpSecurity of the web Application.
	// We can specify our authorization criteria inside this method.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		//.formLogin().loginPage("/#/login").and()
		/*
		.formLogin()//.loginPage("app/views/login.html")
		.and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
        .and()
        */
		.sessionManagement()
        	//.invalidSessionUrl("/login?invalid=1")
        	.maximumSessions(1)
        	.maxSessionsPreventsLogin(true)
        	.sessionRegistry(sessionRegistry())
            //.expiredUrl("/login?time=1")
            .and()
        .and()
				// starts authorizing configurations
		.authorizeRequests()//.antMatchers("/api/users","/api/users/").hasRole("USER")	
				// authenticate all remaining URLS
				.anyRequest().authenticated().and().logout().logoutSuccessHandler(logoutSuccess).deleteCookies("JSESSIONID").invalidateHttpSession(false).permitAll()
				.and()
				.csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				// adding JWT filter
				.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
				// enabling the basic authentication
				.httpBasic();
				// configuring the session as state less. Which means there is
				// no session in the server
				//.sessionManagement()
					//.sessionAuthenticationStrategy(concurrentSessionControlAuthenticationStrategy())
					//.maximumSessions(1)
		            //.maxSessionsPreventsLogin(true);
		            //.expiredUrl("/login?expired" )
		            //.sessionRegistry(sessionRegistry)
		            //.and()
		            //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		            //.invalidSessionUrl("/");
		/*
		http.headers().contentTypeOptions();
        http.headers().xssProtection();
        http.headers().cacheControl();
        http.headers().httpStrictTransportSecurity();
        http.headers().frameOptions();
        */
        //servletContext.getSessionCookieConfig().setHttpOnly(true);
		
				// disabling the CSRF - Cross Site Request Forgery
				//.csrf().disable();
		//System.out.println("Bangke");
	}
	
	// Work around https://jira.spring.io/browse/SEC-2855
	@Bean
	public SessionRegistry sessionRegistry() 
	{
	    SessionRegistry sessionRegistry = new SessionRegistryImpl();
	    return sessionRegistry;
	}
	
	@Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() 
	{
        return new HttpSessionEventPublisher();
    }
	
	/*
	@Bean
	public static ServletListenerRegistrationBean httpSessionEventPublisher() 
	{ 
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher()); 
	}
	*/
	
	 
	 
	/*
	 @Bean
	  public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {

	      ConcurrentSessionControlAuthenticationStrategy strategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
	      
	      //strategy.setMaximumSessions(1);
	      //strategy.
	      strategy.setExceptionIfMaximumExceeded(true);
	      strategy.setMessageSource(messageSource);

	      return strategy;
	 }
	 */
}


