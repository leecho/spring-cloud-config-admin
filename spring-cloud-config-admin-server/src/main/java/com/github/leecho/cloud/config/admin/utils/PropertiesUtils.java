package com.github.leecho.cloud.config.admin.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
public class PropertiesUtils {

	public static Map<String, Object> conventPropertiesToMap(String content) throws IOException {
		Properties properties = new Properties();
		properties.load(new StringReader(content));
		return conventPropertiesToMap(properties);
	}

	public static Map<String, Object> conventPropertiesToMap(InputStream inputStream) throws IOException {
		Properties properties = new Properties();
		properties.load(inputStream);
		return conventPropertiesToMap(properties);
	}

	public static Map<String, Object> conventPropertiesToMap(Properties properties) {
		return properties.entrySet().stream().collect(Collectors.toMap(entry -> String.valueOf(entry.getKey()),
				entry -> StringUtils.trimToEmpty(String.valueOf(entry.getValue()))));
	}
}
