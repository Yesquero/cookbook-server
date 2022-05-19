package ru.mirea.recipebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.mirea.recipebook.service.AuthenticationProviderService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProviderService authenticationProviderService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.authorizeHttpRequests()
			.mvcMatchers("/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/v2/**", "/v3/**", "/swagger-resources/**").permitAll()
			.mvcMatchers("/api/image", "/api/image/**").permitAll()
			.mvcMatchers("/api/user/register", "/api/user/login").permitAll()
			.mvcMatchers("/api/category/all").permitAll()
			.mvcMatchers("/api/recipe/recipes", "/api/recipe/info/*").permitAll()
			.anyRequest().authenticated()
			.and()
			.cors().disable()
			.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProviderService);
	}

}
