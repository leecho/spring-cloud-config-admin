package com.github.leecho.cloud.config.admin.config.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author LIQIU
 * @date 2018-9-4
 **/
public class ConfigPublishEvent extends ApplicationEvent {
	public ConfigPublishEvent(Object source) {
		super(source);
	}
}
