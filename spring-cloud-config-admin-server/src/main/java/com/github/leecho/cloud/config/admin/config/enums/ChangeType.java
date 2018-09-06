package com.github.leecho.cloud.config.admin.config.enums;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
public enum ChangeType {

	CREATE("create", 0), UPDATE("update", 1), DELETE("delete", 2);

	public static ChangeType getByName(String name) {
		ChangeType[] operations = ChangeType.values();
		for (int i = 0; i < operations.length; i++) {
			ChangeType changeType = operations[i];
			if (changeType.getName().equals(name)) {
				return changeType;
			}
		}
		return null;
	}

	public static ChangeType getByValue(Integer value) {
		ChangeType[] operations = ChangeType.values();
		for (int i = 0; i < operations.length; i++) {
			ChangeType changeType = operations[i];
			if (changeType.getValue().equals(value)) {
				return changeType;
			}
		}
		return null;
	}


	ChangeType(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	private String name;

	private Integer value;

}
