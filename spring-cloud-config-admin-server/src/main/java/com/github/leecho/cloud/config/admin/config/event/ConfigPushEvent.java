package com.github.leecho.cloud.config.admin.config.event;

import org.springframework.context.ApplicationEvent;

/**
 * 推配置推送事件
 * @author LIQIU
 * @date 2018-9-4
 **/
public class ConfigPushEvent extends ApplicationEvent {
	public ConfigPushEvent(Object source) {
		super(source);
	}
}
