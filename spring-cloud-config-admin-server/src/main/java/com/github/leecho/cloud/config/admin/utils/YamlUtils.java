package com.github.leecho.cloud.config.admin.utils;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
public class YamlUtils {

	/**
	 * yaml转换成properties
	 *
	 * @param content
	 * @return
	 */
	public static Map<String, Object> convertYamlToMap(String content) {
		Yaml yaml = new Yaml();
		TreeMap<String, Map<String, Object>> config = yaml.loadAs(content, TreeMap.class);
		return toProperties(config);
	}


	/**
	 * yaml转换成properties
	 *
	 * @param inputStream
	 * @return
	 */
	public static Map<String, Object> convertYamlToMap(InputStream inputStream) {
		Yaml yaml = new Yaml();
		TreeMap<String, Map<String, Object>> config = yaml.loadAs(inputStream, TreeMap.class);
		return toProperties(config);
	}

	/**
	 * properties转换成yaml
	 *
	 * @param inputStream
	 * @return
	 */
	public static Map<String, Object> convertPropertiesToYaml(InputStream inputStream) {
		Yaml yaml = new Yaml();
		TreeMap<String, Map<String, Object>> config = yaml.loadAs(inputStream, TreeMap.class);
		return toProperties(config);
	}

	public static void main(String[] args) throws IOException {
		InputStream inputStream = Files.newInputStream(Paths.get("D:/application.yml"));
		convertYamlToMap(inputStream).entrySet().forEach(entry -> {
			System.out.println(String.format("%s=%s",entry.getKey(),entry.getValue()));
		});
	}

	private static Map<String, Object> toProperties(TreeMap<String, Map<String, Object>> config) {
		Map<String, Object> content = new HashMap<>();
		for (String key : config.keySet()) {
			flatMap(key, config.get(key), content);
		}
		return content;
	}

	private static void flatMap(String key, Map<String, Object> map, Map<String, Object> container) {
		for (String mapKey : map.keySet()) {
			if (map.get(mapKey) instanceof Map) {
				flatMap(String.format("%s.%s", key, mapKey), (Map<String, Object>) map.get(mapKey), container);
			} else {
				container.put(key + "." + mapKey, StringUtils.trimToEmpty(String.valueOf(map.get(mapKey))));
			}
		}

	}
}
