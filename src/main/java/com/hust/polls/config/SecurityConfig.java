package com.hust.polls.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hust.polls.security.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled=true,
		jsr250Enabled=true,
		prePostEnabled=true
		)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	@Bean
	public JWTAuthenticationFilter filter() {
		return new JWTAuthenticationFilter();
	};

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
			.and()
		.csrf()
			.disable()
		.exceptionHandling()
			.authenticationEntryPoint(unauthorizedHandler)
			.and()
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		.authorizeRequests()
			.antMatchers("/",
                    "/favicon.ico",
                    "/**/*.png",
                    "/**/*.gif",
                    "/**/*.svg",
                    "/**/*.jpg",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js")
			.permitAll()
			.antMatchers("/api/auth/**").permitAll()
			.antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
            .permitAll()
            .antMatchers(HttpMethod.GET,"/api/polls/**", "/api/users/**")
            .permitAll()
            .anyRequest().authenticated();
		http.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);
	}
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}
	
}
