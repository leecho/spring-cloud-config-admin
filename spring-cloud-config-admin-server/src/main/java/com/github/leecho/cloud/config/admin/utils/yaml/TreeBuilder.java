package com.github.leecho.cloud.config.admin.utils.yaml;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;

public class TreeBuilder {

	private final Map<Object, Object> properties;
	private final static Pattern pattern = Pattern.compile("[0-9]+");
	private final boolean useNumericKeysAsArrayIndexes;

	public TreeBuilder(Map<Object, Object> properties, boolean useNumericKeysAsArrayIndexes) {
		this.properties = properties;
		this.useNumericKeysAsArrayIndexes = useNumericKeysAsArrayIndexes;
	}

	public TreeBuilder(Map<Object, Object> properties) {
		this(properties, true);
	}

	public PropertyTree build() {
		PropertyTree root = new PropertyTree();
		properties.keySet().stream()
				.collect(toMap(
						this::splitPropertyName,
						propertyName -> ValueConverter.asObject(String.valueOf(properties.get(propertyName)))))
				.forEach(root::appendBranchFromKeyValue);
		return root;
	}


	private List<String> splitPropertyName(Object property) {
		List<String> strings = Arrays.asList(String.valueOf(property).split("\\."));
		List<String> result = useNumericKeysAsArrayIndexes ? applyArrayNotation(strings) : strings;
		Collections.reverse(result);
		return result;
	}

	private List<String> applyArrayNotation(List<String> strings) {
		List<String> result = new ArrayList<>();
		for (String element : strings) {
			Matcher matcher = pattern.matcher(element);
			if (matcher.matches()) {
				int index = result.size() - 1;
				result.set(index, result.get(index) + String.format("[%s]", element));
			} else {
				result.add(element);
			}
		}
		return result;
	}

}