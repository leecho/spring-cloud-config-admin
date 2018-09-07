package com.github.leecho.cloud.config.admin.web.servlet.resource;

import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link } which is looking for minified version of resources.
 *
 * @author Johannes Edmeier
 */
public class PreferMinifiedFilteringResourceResolver extends AbstractResourceResolver {
	private final String extensionPrefix;

	public PreferMinifiedFilteringResourceResolver(String extensionPrefix) {
		this.extensionPrefix = extensionPrefix;
	}

	@Override
	protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
											   List<? extends Resource> locations, ResourceResolverChain chain) {
		List<Resource> newLocations = new ArrayList<>(locations.size());

		for (Resource location : locations) {
			Resource minified = findMinified(location);
			newLocations.add(minified != null ? minified : location);
		}

		return chain.resolveResource(request, requestPath, newLocations);
	}

	private Resource findMinified(Resource resource) {
		try {
			String basename = StringUtils.stripFilenameExtension(resource.getFilename());
			String extension = StringUtils.getFilenameExtension(resource.getFilename());
			Resource minified = resource
					.createRelative(basename + extensionPrefix + '.' + extension);
			if (minified.exists()) {
				if (logger.isDebugEnabled()) {
					logger.debug("Found minified file for '" + resource.getFilename() + "': '"
							+ minified.getFilename() + "'");
				}
				return minified;
			}
		} catch (IOException ex) {
			logger.trace("No minified resource for [" + resource.getFilename() + "]", ex);
		}
		return null;
	}

	@Override
	protected String resolveUrlPathInternal(String resourceUrlPath,
			List<? extends Resource> locations, ResourceResolverChain chain) {
		return chain.resolveUrlPath(resourceUrlPath, locations);
	}

}
