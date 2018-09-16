package com.github.leecho.cloud.config.admin.webhook.listener;

import com.github.leecho.cloud.config.admin.config.enums.ConfigEvent;
import com.github.leecho.cloud.config.admin.config.event.ConfigPushEvent;
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
public class ConfigPushEventListener implements ApplicationListener<ConfigPushEvent> {

	private final WebHookExecutor webHookExecutor;

	@Autowired
	public ConfigPushEventListener(WebHookExecutor webHookExecutor) {
		this.webHookExecutor = webHookExecutor;
	}

	@Override
	public void onApplicationEvent(ConfigPushEvent configPushEvent) {
		this.webHookExecutor.execute(ConfigEvent.PUSH.getValue(), null);

	}
}
