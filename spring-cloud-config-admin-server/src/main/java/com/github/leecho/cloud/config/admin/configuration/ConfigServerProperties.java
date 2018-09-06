package com.github.leecho.cloud.config.admin.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Data
@ConfigurationProperties("spring.cloud.config")
public class ConfigServerProperties {

	private String url;

	private String username;

	private String password;

	private String serviceId;

}
