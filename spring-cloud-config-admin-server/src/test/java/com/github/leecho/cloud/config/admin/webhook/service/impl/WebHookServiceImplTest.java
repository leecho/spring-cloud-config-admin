package com.github.leecho.cloud.config.admin.webhook.service.impl;

import com.github.leecho.cloud.config.admin.ServiceTestConfiguration;
import com.github.leecho.cloud.config.admin.webhook.entity.WebHook;
import com.github.leecho.cloud.config.admin.webhook.repository.WebHookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author LIQIU
 * @date 2018-9-10
 **/
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ServiceTestConfiguration.class)
public class WebHookServiceImplTest {

	@Autowired
	private WebHookRepository webHookRepository;

	@Test
	public void testAdd() {
		WebHook webHook = new WebHook();
		webHook.setApi("http://localhost:8080/webhook");
		webHook.setName("Test WebHook");
		webHook.setTriggers("push,publish");
		this.webHookRepository.save(webHook);
	}
}