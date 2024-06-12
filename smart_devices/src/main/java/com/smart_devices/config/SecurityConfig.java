package com.smart_devices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.smart_devices.service.oauth2.security.OAuth2UserDetailServiceCustom;
import com.smart_devices.service.oauth2.security.handler.OAuth2FailureHandlerCustom;
import com.smart_devices.service.oauth2.security.handler.OAuth2SuccessHandlerCustom;
import com.smart_devices.service.security.UserDetailsServiceCustom;

@Configuration
public class SecurityConfig {
	@Autowired
	private OAuth2UserDetailServiceCustom oAuth2UserDetailServiceCustom;
	
	@Autowired
	private OAuth2SuccessHandlerCustom oAuth2SuccessHandlerCustom;
	
	@Autowired
	private OAuth2FailureHandlerCustom oAuth2FailureHandlerCustom;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceCustom();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// TODO Auto-generated method stub

		AuthenticationManagerBuilder builder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

		builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

		AuthenticationManager manager = builder.build();
		return httpSecurity.csrf(AbstractHttpConfigurer::disable).authenticationManager(manager).httpBasic((httpBs) -> {
		}).authorizeHttpRequests(register -> {
			register.requestMatchers("/admin/**").hasAuthority("ADMIN");
			register.requestMatchers("/cart/**","/payment", "/api/**").hasAnyAuthority("USER","ADMIN");
			register.anyRequest().permitAll();
		}).exceptionHandling(exception -> {
			exception.accessDeniedPage("/403");
		}).formLogin(formLoginConfig -> {
			formLoginConfig.loginPage("/auth/sign-in")
							.loginProcessingUrl("/$2a$12$Bonr8SHeaNAmq7KblDXlzuOtkCozCpEwRwMK5iAaRyW238O5Ck")
							.permitAll();
		}).oauth2Login(formLoginConfig -> {
			formLoginConfig.loginPage("/auth/sign-in")
							.userInfoEndpoint((uIE)-> {uIE.userService(oAuth2UserDetailServiceCustom);})
							.successHandler(oAuth2SuccessHandlerCustom)
							.failureHandler(oAuth2FailureHandlerCustom);
		}).sessionManagement(sM -> {sM.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);})
	      .logout(logoutConfig -> {
			logoutConfig.invalidateHttpSession(true).deleteCookies("JSESSIONID").clearAuthentication(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
		}).build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**");
	}
}
