package com.github.leecho.cloud.config.admin.config.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.leecho.cloud.config.admin.config.entity.Change;
import com.github.leecho.cloud.config.admin.config.entity.Config;
import com.github.leecho.cloud.config.admin.config.entity.Publish;
import com.github.leecho.cloud.config.admin.config.model.DetectedChange;
import com.github.leecho.cloud.config.admin.config.model.PublishRequest;
import com.github.leecho.cloud.config.admin.config.model.PublishVO;
import com.github.leecho.cloud.config.admin.config.model.RollbackRequest;
import com.github.leecho.cloud.config.admin.config.repository.ChangeRepository;
import com.github.leecho.cloud.config.admin.config.repository.ConfigRepository;
import com.github.leecho.cloud.config.admin.config.repository.PublishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
@Slf4j
@Component
public class PublishManager {

	@Autowired
	private PublishRepository publishRepository;

	@Autowired
	private ChangeRepository changeRepository;

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	private DraftManager draftManager;

	private ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * 回滚配置文件
	 *
	 * @param rollbackRequest
	 */
	public Publish rollback(RollbackRequest rollbackRequest) {

		Config config = this.configRepository.findById(rollbackRequest.getConfigId()).orElseThrow(() -> new IllegalArgumentException("Config not found"));

		Publish publish;

		//获取已发布的版本
		if (rollbackRequest.getPublishId() != null) {
			publish = publishRepository.findById(rollbackRequest.getPublishId()).orElseThrow(() -> new IllegalArgumentException("Publish not found"));
		} else {
			publish = this.publishRepository.getByConfig_IdAndCurrent(rollbackRequest.getConfigId(), true);
		}

		Assert.notNull(publish, "Publish for rollback not found");

		Map<String, Object> items;
		try {
			items = objectMapper.readValue(publish.getContent(), Map.class);
		} catch (IOException e) {
			throw new IllegalStateException("回滚数据失败");
		}

		//进行变更
		List<DetectedChange> detectedChanges = this.draftManager.detect(publish.getConfig().getId(), items);
		detectedChanges.forEach(detectedChange -> {
			Change change = new Change();
			BeanUtils.copyProperties(detectedChange, change);
			change.setConfig(config);
			change.setMessage(rollbackRequest.getMessage());
			this.draftManager.commit(change);
		});

		PublishRequest publishRequest = new PublishRequest();
		publishRequest.setConfigId(config.getId());
		publishRequest.setMessage(rollbackRequest.getMessage());

		//发布新版本
		return this.publish(publishRequest);
	}

	/**
	 * 对配置进行归档
	 *
	 * @param publishRequest 发布操作
	 */
	public Publish publish(PublishRequest publishRequest) {

		String principal = SecurityContextHolder.getContext().getAuthentication().getName();

		Config config = this.configRepository.findById(publishRequest.getConfigId()).orElseThrow(() -> new IllegalArgumentException("Special config is not found"));

		//应用草稿
		Map<String, String> items = this.draftManager.apply(config);


		Publish publish = new Publish();
		publish.setConfig(config);
		try {
			publish.setContent(objectMapper.writeValueAsString(items));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		publish.setPublishBy(principal);
		publish.setPublishDate(new Date());

		Publish current = publishRepository.getByConfig_IdAndCurrent(config.getId(), true);

		if (current != null) {
			current.setCurrent(false);
			publish.setVersion(current.getVersion() + 1);
			this.publishRepository.save(current);
		} else {
			publish.setVersion(0);
		}
		publish.setCurrent(true);
		publish.setMessage(publishRequest.getMessage());
		publish = this.publishRepository.save(publish);

		List<Change> changes = changeRepository.getByConfig_IdAndPublished(config.getId(), false);
		for (Change change : changes) {
			change.setPublish(publish);
			change.setPublished(Boolean.TRUE);
			this.changeRepository.save(change);
		}

		log.info("Config [{}:{}:{}] publish successful, publish message: {}", config.getProject().getCode(), config.getProfile().getCode(), config.getLabel(), publish.getMessage());

		return publish;
	}

	/**
	 * 获取配置文件发布记录
	 *
	 * @param configId
	 * @param pageable
	 * @return
	 */
	public Page<PublishVO> getPublishes(Integer configId, Pageable pageable) {
		Page<Publish> publishes = this.publishRepository.findByConfig_Id(configId, pageable);
		return publishes.map(publish -> {
			PublishVO publishVO = new PublishVO();
			BeanUtils.copyProperties(publish, publishVO, "config");
			return publishVO;
		});
	}

	/**
	 * 获取发布的配置文件的内容
	 *
	 * @param id
	 * @return
	 */
	public String getPublishContent(Integer id) {
		Publish publish = this.publishRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Publish not found"));
		return publish.getContent();
	}
}
