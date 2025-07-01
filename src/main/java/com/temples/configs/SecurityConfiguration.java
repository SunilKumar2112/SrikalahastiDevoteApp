package com.temples.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.temples.util.PasswordUtilService;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {


	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	PasswordUtilService passwordEncoder;
	
	@Bean
    public AuthenticationManager getAuthenticationManager() {
    	return new ProviderManager(daoAuthenticationProvider());
    }
	
	@Bean
	public SecurityFilterChain authorization(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/**").permitAll().anyRequest().authenticated())
				.formLogin(Customizer.withDefaults());


		return httpSecurity.build();
	}

	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(passwordEncoder.getPasswordEncoder());
		authenticationProvider.setUserDetailsService(userDetailsService);
		return authenticationProvider;

	}
//	@Bean
//	public DaoAuthenticationProvider daoAuthenticationProvider() {
//	    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//	    authenticationProvider.setUserDetailsService(userDetailsService);
//	    authenticationProvider.setPasswordEncoder(passwordEncoder());
//	    return authenticationProvider;
//	}

}
