package com.github.leecho.cloud.config.admin.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * @date 2018-9-7
 **/
@Data
@ConfigurationProperties("spring.cloud.config.admin")
public class ConfigAdminServerProperties {

	private String contextPath = "";

}
