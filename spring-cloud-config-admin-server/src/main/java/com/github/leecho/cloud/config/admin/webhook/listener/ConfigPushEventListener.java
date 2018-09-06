package com.github.leecho.cloud.config.admin.webhook.listener;

import com.github.leecho.cloud.config.admin.config.enums.ConfigEvent;
import com.github.leecho.cloud.config.admin.config.event.ConfigPushEvent;
import com.github.leecho.cloud.config.admin.webhook.WebHookExecutor;
import com.github.leecho.cloud.config.admin.webhook.entity.WebHook;
import com.github.leecho.cloud.config.admin.webhook.service.WebHookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Slf4j
@Component
public class ConfigPushEventListener implements ApplicationListener<ConfigPushEvent> {

	private final WebHookExecutor webHookExecutor;

	private final WebHookService webHookService;

	@Autowired
	public ConfigPushEventListener(WebHookExecutor webHookExecutor, WebHookService webHookService) {
		this.webHookExecutor = webHookExecutor;
		this.webHookService = webHookService;
	}

	@Override
	public void onApplicationEvent(ConfigPushEvent configPushEvent) {
		List<WebHook> webHooks = webHookService.getByEvent(ConfigEvent.PUSH.getValue());
		webHooks.forEach(webHook -> {
			Map<String, Object> result = webHookExecutor.execute(webHook, null);
			if (log.isDebugEnabled()) {
				log.debug("Execute webhook successful on config pushed , result: {}", result);
			}
		});

	}
}