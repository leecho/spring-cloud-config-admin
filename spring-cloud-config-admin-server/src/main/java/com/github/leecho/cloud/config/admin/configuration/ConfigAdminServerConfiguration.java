package com.github.leecho.cloud.config.admin.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@ComponentScan("com.github.leecho.cloud.config.admin")
@EntityScan("com.github.leecho.cloud.config.admin")
@EnableJpaRepositories(basePackages = "com.github.leecho.cloud.config.admin")
@EnableConfigurationProperties({ConfigServerProperties.class, ConfigAdminServerConfiguration.class})
public class ConfigAdminServerConfiguration {

	@LoadBalanced
	@Bean
	public RestTemplate configAdminRestTemplate() {
		return new RestTemplate();
	}

}
