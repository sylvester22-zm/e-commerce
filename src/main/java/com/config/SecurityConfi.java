package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.service.impl.UserSecurityService;
import com.utility.SecurityUtility;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfi extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment env;

	@Autowired
	private UserSecurityService userSecurityService;

	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}

	private static final String[] PUBLIC_MATCHERS = { 
			"/css/**", 
			"/js/**", 
			"/image/**", 
			"/", 
			"/login-register",
			"/register",
			"/myAccount",
			"/Detail",
			"/searchItem",
			"/passwordReset",
			"/forgotPassword",
			///
			/*
			 * "/product/List", "/product/edit", "/product/delete", "/product/add",
			 */
			"/fonts/**"
			};

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		 http .authorizeRequests() 
					/* antMatchers("/**") */
		 .antMatchers(PUBLIC_MATCHERS).permitAll()
		.anyRequest().authenticated();
	    // .antMatchers("/adminHome").hasAuthority("Role_Admin").anyRequest()

		 http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/");
		
		http
		.csrf().disable().cors().disable()
		.formLogin().failureUrl("/login?error")
		//.defaultSuccessUrl("/")
		.loginPage("/login").permitAll()
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
		.and()
		.rememberMe();
				
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}

}
