package com.github.leecho.cloud.config.admin.webhook;

import com.github.leecho.cloud.config.admin.webhook.entity.WebHook;
import com.github.leecho.cloud.config.admin.webhook.service.WebHookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Slf4j
@Component
@EnableAsync
public class WebHookExecutor {

	private RestTemplate restTemplate;

	@Autowired
	private WebHookService webHookService;

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
	}

	public void execute(String event, Object content) {
		List<WebHook> webHooks = webHookService.getByEvent(event);
		webHooks.forEach(webHook -> {
			Map<String, Object> result = this.execute(webHook, content);
			if (log.isDebugEnabled()) {
				log.debug("Execute webhook successful on config published , result: {}", result);
			}
		});
	}

	@Async
	public Map<String, Object> execute(WebHook webHook, Object content) {
		ResponseEntity<Map> entity = restTemplate.postForEntity(webHook.getApi(), content, Map.class);
		return (Map<String, Object>) entity.getBody();
	}
}
