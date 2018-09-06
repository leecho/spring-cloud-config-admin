package com.github.leecho.cloud.config.admin.webhook.service;

import com.github.leecho.cloud.config.admin.webhook.entity.WebHook;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
public interface WebHookService {

	/**
	 * 通过事件获取webhook
	 *
	 * @param event 事件名称
	 * @return webhook列表
	 */
	List<WebHook> getByEvent(String event);

}
