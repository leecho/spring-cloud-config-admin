package com.github.leecho.cloud.config.admin.config.enums;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
public enum ConfigEvent {
	PUSH("push"), PUBLISH("publish");

	ConfigEvent(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}
}
