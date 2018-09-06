package com.github.leecho.cloud.config.admin.annotation;

import com.github.leecho.cloud.config.admin.configuration.ConfigAdminServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ConfigAdminServerAutoConfiguration.class)
public @interface EnableConfigAdminServer {
}
