package com.github.leecho.sample.config.admin;

import com.github.leecho.cloud.config.admin.annotation.EnableConfigAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Config Admin Application
 *
 * @author LIQIU
 */
@Configuration
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigAdminServer
public class ConfigAdminApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ConfigAdminApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository()).and()
				.authorizeRequests().antMatchers("/swagger-ui.html", "/v2/api-docs").permitAll();*/
		//super.configure(http);
		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
	}
}
