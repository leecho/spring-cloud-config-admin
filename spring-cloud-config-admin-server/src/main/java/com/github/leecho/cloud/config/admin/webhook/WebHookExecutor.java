package com.github.leecho.cloud.config.admin.webhook;

import com.github.leecho.cloud.config.admin.webhook.entity.WebHook;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Component
@EnableAsync
public class WebHookExecutor {

	private RestTemplate restTemplate;

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
	}

	@Async
	public Map<String,Object> execute(WebHook webHook, Object content) {
		ResponseEntity<Map> entity = restTemplate.postForEntity(webHook.getApi(), content, Map.class);
		return (Map<String,Object>)entity.getBody();
	}
}
