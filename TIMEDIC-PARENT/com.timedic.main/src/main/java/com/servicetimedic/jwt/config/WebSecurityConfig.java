package com.servicetimedic.jwt.config;

import java.util.Arrays;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Web security configuration class
 
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

		//web.ignoring().antMatchers("/", "/index.html", "/app/**", "/register/**", "/authenticate/**", "/favicon.ico");
		web.ignoring().antMatchers("/api/users/register", "/register/**", "/authenticateBySocial/**" ,"/authenticateBySocialToken/**" ,"/authenticate/**",
				"/logged/user", "/socket/**", "/app/send/message", "/checkCaregiverPasswordIsNullOrNot", "/checkUserPasswordIsNullOrNot", "/passwordNurse", "/passwordUser");
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
		.cors().and()
		//.sessionManagement()
        	//.invalidSessionUrl("/login?invalid=1")
        	//.maximumSessions(1)
        	//.maxSessionsPreventsLogin(true)
        	//.sessionRegistry(sessionRegistry())
            //.expiredUrl("/login?time=1")
            //.and()
        //.and()
			//.antMatcher("/api/**")	// starts authorizing configurations
		.authorizeRequests()//.antMatchers("/api/users","/api/users/").hasRole("USER")	
				// authenticate all remaining URLS
				.anyRequest().authenticated()
				.and().logout().logoutSuccessHandler(logoutSuccess).deleteCookies("JSESSIONID")//.invalidateHttpSession(false).permitAll()
				.and()
				.csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				// adding JWT filter
				.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
				//.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
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
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CORSFilter());
        registrationBean.setName("CORS Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
	*/
	
	/*
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
	*/
	
	/*
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Methods", "Access-Control-Allow-Headers", "Access-Control-Max-Age",
				"Access-Control-Request-Headers", "Access-Control-Request-Method"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	*/
	
	/*
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        final CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
	        // setAllowCredentials(true) is important, otherwise:
	        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
	        configuration.setAllowCredentials(true);
	        // setAllowedHeaders is important! Without it, OPTIONS preflight request
	        // will fail with 403 Invalid CORS request
	        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	 */
	
	/*
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	*/
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


