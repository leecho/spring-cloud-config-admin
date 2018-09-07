package com.github.leecho.cloud.config.admin.config.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.leecho.cloud.config.admin.config.entity.Config;
import com.github.leecho.cloud.config.admin.config.entity.Push;
import com.github.leecho.cloud.config.admin.config.repository.PushRepository;
import com.github.leecho.cloud.config.admin.configuration.ConfigServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Date;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Component
@Slf4j
public class PushManager {

	@Autowired
	@Qualifier("configAdminRestTemplate")
	private RestTemplate restTemplate;

	@Autowired
	private ConfigServerProperties configServerProperties;

	private String configServerUrl;

	@Autowired
	private PushRepository pushRepository;

	private ObjectMapper objectMapper = new ObjectMapper();


	@PostConstruct
	public void init() {
		if (StringUtils.isNotEmpty(configServerProperties.getUsername())) {
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(configServerProperties.getUsername(), configServerProperties.getPassword()));
		}
		if (StringUtils.isNotEmpty(configServerProperties.getUrl())) {
			configServerUrl = configServerProperties.getUrl();
		} else {
			configServerUrl = String.format("http://%s", configServerProperties.getServiceId());
		}
		configServerUrl = StringUtils.removeEnd(configServerUrl, "/") + "/bus/refresh";

		log.info("Config server url build successful, url: {}", configServerUrl);
	}

	/**
	 * 推送配置
	 *
	 * @param destination
	 */
	public Push push(Config config, String destination) {

		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		String serviceUrl = configServerUrl;
		destination = config.getProject().getCode() + ":" + config.getProfile().getCode() + ":" + destination;
		serviceUrl += "?destination=" + destination;
		Push push = Push.builder().config(config).destination(destination).pushedBy(principal).pushedDate(new Date()).build();

		Boolean success = false;

		try {
			ResponseEntity<Map> responseEntity = restTemplate.getForEntity(URI.create(serviceUrl), Map.class);
			success = responseEntity.getStatusCode().equals(HttpStatus.OK);
			try {
				push.setResult(objectMapper.writeValueAsString(responseEntity.getBody()));
			} catch (Exception e) {
				push.setResult(responseEntity.toString());
			}

			log.info("Push config successful, destination: {}, response: {} ", destination, responseEntity);

		} catch (Exception exception) {
			push.setResult(exception.getMessage());
			log.info("Push config failed, destination: {}, cause: {} ", destination, exception.getMessage());
			exception.printStackTrace();
		}
		push.setSuccess(success);
		pushRepository.save(push);
		return push;
	}
}
