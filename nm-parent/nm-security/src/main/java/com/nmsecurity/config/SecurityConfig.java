package com.nmsecurity.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@EnableWebSecurity
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter{
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		log.info("inisde configureGlobal method - SecurityConfig");
		auth.authenticationProvider(keycloakAuthenticationProvider());
	}
	
	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		log.info("inisde sessionAuthenticationStrategy method - SecurityConfig");				
		return new NullAuthenticatedSessionStrategy();
	}
	
	@Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
		log.info("inisde keycloakConfigResolver method - SecurityConfig");
		return new KeycloakSpringBootConfigResolver();
        //Reason
        //keycloakConfigResolver defines that we want to use the Spring Boot properties file support instead of the default keycloak.json.
    }	

	@Bean
	public FilterRegistrationBean<KeycloakPreAuthActionsFilter> keycloakPreAuthActionsFilterRegistrationBean(KeycloakPreAuthActionsFilter filter) {
		log.info("inisde keycloakPreAuthActionsFilterRegistrationBean method - SecurityConfig");
		FilterRegistrationBean<KeycloakPreAuthActionsFilter> registrationBean = new FilterRegistrationBean<KeycloakPreAuthActionsFilter>(filter);
		registrationBean.setEnabled(false);		
		return registrationBean;
	}	

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		 log.info("inisde configure method - SecurityConfig");		
		 http.csrf()		
			.disable()
			.authorizeRequests()
			//.antMatchers("/nm/login","/nm/config","/nm/logout")
			.antMatchers("/nm/*","/aboutus.html")
			.permitAll()
			.anyRequest()
			.authenticated()			
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.sessionAuthenticationStrategy(sessionAuthenticationStrategy())
			.and()
			.addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter.class)		
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
			.and()
            .logout()
            .addLogoutHandler(keycloakLogoutHandler())
            .logoutUrl("/sso/logout")
            .permitAll()
            .logoutSuccessUrl("/nm/logout");;        
    }
	 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}


/*@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);	
	
	@Autowired private LoginRequestFilter loginRequestFilter;	
	@Autowired private JWTFilter jWTFilter;
	
	@Autowired private LoginAuthenticationProvider loginAuthenticationProvider;
	

	@Autowired private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	//@Autowired private RestAccessDeniedHandler restAccessDeniedHandler;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("inisde AuthenticationManagerBuilder configure method - SecurityConfig");		
		auth.authenticationProvider(loginAuthenticationProvider);		
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("inisde HttpSecurity configure method - SecurityConfig");
		http.csrf()		
			.disable()						
			.authorizeRequests()
			.antMatchers("/nm/login","/nm/config","/nm/logout").permitAll()
			.anyRequest()
			.authenticated()			
			.and()			
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()			
			.addFilterBefore(loginRequestFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(jWTFilter, LoginRequestFilter.class)
			.exceptionHandling()
				// .accessDeniedHandler(restAccessDeniedHandler) 
			.authenticationEntryPoint(customAuthenticationEntryPoint);		
	}	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {		
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}*/
