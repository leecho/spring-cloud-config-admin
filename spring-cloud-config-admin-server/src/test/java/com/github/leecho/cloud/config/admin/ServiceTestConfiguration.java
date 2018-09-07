package com.github.leecho.cloud.config.admin;

import com.github.leecho.cloud.config.admin.annotation.EnableConfigAdminServer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@ComponentScan("com.github.leecho.cloud.config.admin")
@EntityScan("com.github.leecho.cloud.config.admin")
@EnableJpaRepositories(basePackages = "com.github.leecho.cloud.config.admin", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = RestController.class)
})
@EnableConfigAdminServer
public class ServiceTestConfiguration {

}
