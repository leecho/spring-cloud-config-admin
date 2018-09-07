package com.github.leecho.cloud.config.admin.configuration;

import com.github.leecho.cloud.config.admin.web.servlet.resource.ConcatenatingResourceResolver;
import com.github.leecho.cloud.config.admin.web.servlet.resource.PreferMinifiedFilteringResourceResolver;
import com.github.leecho.cloud.config.admin.web.servlet.resource.ResourcePatternResolvingResourceResolver;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LIQIU
 * @date 2018-9-7
 **/
@EnableConfigurationProperties(ConfigAdminServerProperties.class)
public class ConfigAdminServerWebConfiguration implements WebMvcConfigurer {

	private final ServerProperties server;
	private final ResourcePatternResolver resourcePatternResolver;
	private final ConfigAdminServerProperties configAdminServerProperties;

	public ConfigAdminServerWebConfiguration(ServerProperties server, ResourcePatternResolver resourcePatternResolver, ConfigAdminServerProperties configAdminServerProperties) {
		this.server = server;
		this.resourcePatternResolver = resourcePatternResolver;
		this.configAdminServerProperties = configAdminServerProperties;
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(configAdminServerProperties.getContextPath() + "/**")
				.addResourceLocations("classpath:/META-INF/spring-cloud-config-admin-server-ui/")
				.resourceChain(true);

		registry.addResourceHandler(configAdminServerProperties.getContextPath() + "/all-modules.css")
				.resourceChain(true)
				.addResolver(new ResourcePatternResolvingResourceResolver(resourcePatternResolver,
						"classpath*:/META-INF/spring-cloud-config-admin-server-ui/*/module.css"))
				.addResolver(new ConcatenatingResourceResolver("\n".getBytes()));

		registry.addResourceHandler(configAdminServerProperties.getContextPath() + "/all-modules.js")
				.resourceChain(true)
				.addResolver(new ResourcePatternResolvingResourceResolver(resourcePatternResolver,
						"classpath*:/META-INF/spring-cloud-config-admin-server-ui/*/module.js"))
				.addResolver(new PreferMinifiedFilteringResourceResolver(".min"))
				.addResolver(new ConcatenatingResourceResolver(";\n".getBytes()));
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String contextPath = configAdminServerProperties.getContextPath();
		if (StringUtils.hasText(contextPath)) {
			registry.addRedirectViewController(contextPath, server.getServlet().getContextPath() + contextPath + "/");
		}
		registry.addViewController(contextPath + "/").setViewName("forward:index.html");
	}
}
