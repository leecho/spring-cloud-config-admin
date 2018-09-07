package com.github.leecho.cloud.config.admin.annotation;

import com.github.leecho.cloud.config.admin.configuration.ConfigAdminServerConfiguration;
import com.github.leecho.cloud.config.admin.configuration.ConfigAdminServerWebConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ConfigAdminServerConfiguration.class, ConfigAdminServerWebConfiguration.class})
public @interface EnableConfigAdminServer {
}
