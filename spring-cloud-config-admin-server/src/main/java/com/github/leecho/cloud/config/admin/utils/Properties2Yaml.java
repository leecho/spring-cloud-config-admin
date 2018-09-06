package com.github.leecho.cloud.config.admin.utils;

import com.github.leecho.cloud.config.admin.utils.yaml.ArrayProcessor;
import com.github.leecho.cloud.config.admin.utils.yaml.PropertyTree;
import com.github.leecho.cloud.config.admin.utils.yaml.TreeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.Properties;

public class Properties2Yaml {

	private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final Properties properties = new Properties();

	Properties2Yaml(String source) {
		try {
			properties.load(new StringReader(source));
		} catch (IOException e) {
			reportError(e);
		}
	}

	Properties2Yaml(File file) {
		try (InputStream input = new FileInputStream(file)) {
			properties.load(input);
		} catch (IOException e) {
			reportError(e);
		}
	}

	public static Properties2Yaml fromContent(String content) {
		return new Properties2Yaml(content);
	}

	public static Properties2Yaml fromFile(File file) {
		return new Properties2Yaml(file);
	}

	public static Properties2Yaml fromFile(Path path) {
		return new Properties2Yaml(path.toFile());
	}

	public String convert(boolean useNumericKeysAsArrayIndexes) {
		PropertyTree tree = new TreeBuilder(properties, useNumericKeysAsArrayIndexes).build();
		tree = new ArrayProcessor(tree).apply();
		return tree.toYAML();
	}

	public String convert() {
		return convert(true);
	}

	private void reportError(IOException e) {
		LOG.error("Conversion failed", e);
	}

}