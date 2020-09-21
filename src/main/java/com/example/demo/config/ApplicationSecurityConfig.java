package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.example.demo.config.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.csrf().disable()
//			.and()
			.authorizeRequests()
			.antMatchers("/h2-console/**","image/**","data/**","/css/*","/js/*", "index","*.jsp","/vendor/**").permitAll()
//			.antMatchers("/api/**").hasRole(STUDENT.name())
//			.antMatchers(HttpMethod.GET, "/management/**").hasAnyRole(STUDENT.name(), ADMIN.name())
//			.antMatchers(HttpMethod.DELETE, "/management/**").hasAuthority(STUDENT_WRITE.getPermission())
//			.antMatchers(HttpMethod.POST, "/management/**").hasAuthority(STUDENT_WRITE.getPermission())
//			.antMatchers(HttpMethod.PUT, "/management/**").hasAuthority(STUDENT_WRITE.getPermission())
			.anyRequest()
			.authenticated()
			.and()
			.headers().frameOptions().sameOrigin()
			.and()
//			.httpBasic();
			.formLogin()
//				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/courses", true)
				.passwordParameter("password")
				.usernameParameter("username")
			.and()
			.rememberMe()
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
				.key("somesecurekeyformd5hash")
				.rememberMeParameter("remember-me")
				.userDetailsService(userDetails())		//to take care of the userDetails error
			.and()
			.logout()
				.logoutUrl("/logout")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID","remember-me")
				.logoutSuccessUrl("/login");
				
					
		
	}
	
	@Bean
	protected UserDetailsService userDetails() {
		UserDetails user = User.builder()
				.username("calvin")
				.password(passwordEncoder.encode("1234"))
//				.roles(STUDENT.name())
				.authorities(STUDENT.getGrantedAuthorities())
				.build();
		
		UserDetails admin = User.builder()
				.username("seth")
				.password(passwordEncoder.encode("1234"))
				.authorities(ADMIN.getGrantedAuthorities())
				.authorities(STUDENT.getGrantedAuthorities())
//				.roles(ADMIN.name())
				.build();
		
		UserDetails tom = User.builder()
				.username("tom")
				.password(passwordEncoder.encode("1234"))
				.authorities(ADMINTRAINEE.getGrantedAuthorities())
//				.roles(ADMINTRAINEE.name())
				.build();
		
		return new InMemoryUserDetailsManager(user, admin, tom);
	}
	
}
