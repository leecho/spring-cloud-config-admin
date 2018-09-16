package com.github.leecho.cloud.config.admin.webhook.listener;

import com.github.leecho.cloud.config.admin.config.enums.ConfigEvent;
import com.github.leecho.cloud.config.admin.config.event.ConfigPublishEvent;
import com.github.leecho.cloud.config.admin.webhook.WebHookExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Slf4j
@Component
public class ConfigPublishEventListener implements ApplicationListener<ConfigPublishEvent> {

	private final WebHookExecutor webHookExecutor;


	@Autowired
	public ConfigPublishEventListener(WebHookExecutor webHookExecutor) {
		this.webHookExecutor = webHookExecutor;
	}

	@Override
	public void onApplicationEvent(ConfigPublishEvent configPublishEvent) {
		this.webHookExecutor.execute(ConfigEvent.PUBLISH.getValue(), null);
	}
}
