package com.spring.bootPractice.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.spring.bootPractice.member.handler.CustomAccessDeniedHandler;
import com.spring.bootPractice.member.handler.LoginFailureHandler;
import com.spring.bootPractice.member.handler.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration{
	
	@Bean	//service, passwordEncoder 자동설정 됨
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration)throws Exception{
		return authConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/resources/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests((authz)->authz			//요청에 대한 권한 설정
							.antMatchers("/member/**").hasAnyRole("GUEST","USER","ADMIN")
							.antMatchers("/admin/**").hasRole("ADMIN")
							.antMatchers("/**").permitAll()
							.anyRequest().authenticated()
			);
		http
			.formLogin()		// form 로그인 설정
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.usernameParameter("id")
				.passwordParameter("password")
				.successHandler(new LoginSuccessHandler())
				.failureHandler(new LoginFailureHandler())
			.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
			.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler());
		return http.build();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
		accessDeniedHandler.setErrorPage("/denied");
		return accessDeniedHandler;
	}
}