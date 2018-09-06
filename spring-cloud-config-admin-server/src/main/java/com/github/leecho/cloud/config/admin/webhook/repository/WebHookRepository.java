package com.github.leecho.cloud.config.admin.webhook.repository;

import com.github.leecho.cloud.config.admin.webhook.entity.WebHook;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
public interface WebHookRepository extends JpaRepository<WebHook, Integer> {
}
