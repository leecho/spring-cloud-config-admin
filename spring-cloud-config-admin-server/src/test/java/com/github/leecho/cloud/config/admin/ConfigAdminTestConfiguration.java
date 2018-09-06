package com.github.leecho.cloud.config.admin;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@EntityScan("com.github.leecho")
@EnableJpaRepositories(basePackages = "com.github.leecho")
@ComponentScan(value = "com.github.leecho", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {RestController.class})
})
public class ConfigAdminTestConfiguration {

}
