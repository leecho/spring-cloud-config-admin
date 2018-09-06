package com.github.leecho.cloud.config.admin.webhook.service.impl;

import com.github.leecho.cloud.config.admin.webhook.entity.WebHook;
import com.github.leecho.cloud.config.admin.webhook.repository.WebHookRepository;
import com.github.leecho.cloud.config.admin.webhook.service.WebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@Service
public class WebHookServiceImpl implements WebHookService {

	private WebHookRepository webHookRepository;

	@Autowired
	public WebHookServiceImpl(WebHookRepository webHookRepository) {
		this.webHookRepository = webHookRepository;
	}

	@Override
	public List<WebHook> getByEvent(String event) {
		return webHookRepository.findAll().stream().filter(webHook -> webHook.getTriggers().contains(event)).collect(Collectors.toList());
	}
}
