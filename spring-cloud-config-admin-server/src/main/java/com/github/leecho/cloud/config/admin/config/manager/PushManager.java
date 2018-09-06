package com.github.leecho.cloud.config.admin.config.manager;

import com.github.leecho.cloud.config.admin.configuration.ConfigServerProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Component
public class PushManager {

	@Autowired
	@Qualifier("cloudClientRestTemplate")
	private RestTemplate restTemplate;

	@Autowired
	private ConfigServerProperties configServerProperties;

	/**
	 * 推送配置
	 *
	 * @param destination
	 */
	public void push(String destination) {
		String serviceUrl = this.getConfigServerUrl() + "/bus/refresh";
		destination = StringUtils.isNotEmpty(destination) ? destination : "*";
		if (!"*".equals(destination)) {
			serviceUrl += "?destination" + destination;
		}
		restTemplate.postForLocation(URI.create(serviceUrl), null);
	}

	private String getConfigServerUrl() {
		if (StringUtils.isNotEmpty(configServerProperties.getUrl())) {
			return configServerProperties.getUrl();
		} else {
			if (StringUtils.isNotEmpty(configServerProperties.getUsername())) {
				return String.format("http://%s:%s@%s", configServerProperties.getUsername(), configServerProperties.getPassword(), configServerProperties.getServiceId());
			} else {
				return String.format("http://%s", configServerProperties.getServiceId());
			}
		}
	}

}
