package com.github.leecho.cloud.config.admin.config.service.impl;

import com.github.leecho.cloud.config.admin.config.entity.Change;
import com.github.leecho.cloud.config.admin.config.entity.Config;
import com.github.leecho.cloud.config.admin.config.entity.Push;
import com.github.leecho.cloud.config.admin.config.event.ConfigPublishEvent;
import com.github.leecho.cloud.config.admin.config.event.ConfigPushEvent;
import com.github.leecho.cloud.config.admin.config.manager.DraftManager;
import com.github.leecho.cloud.config.admin.config.manager.PublishManager;
import com.github.leecho.cloud.config.admin.config.manager.PushManager;
import com.github.leecho.cloud.config.admin.config.model.*;
import com.github.leecho.cloud.config.admin.config.repository.ChangeRepository;
import com.github.leecho.cloud.config.admin.config.repository.ConfigRepository;
import com.github.leecho.cloud.config.admin.config.service.ConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigServiceImpl implements ConfigService, ApplicationEventPublisherAware {

	@Autowired
	private ConfigRepository configRepository;
	@Autowired
	private ChangeRepository changeRepository;

	@Autowired
	private DraftManager draftManager;

	@Autowired
	private PublishManager publishManager;

	@Autowired
	private PushManager pushManager;

	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public Config save(Config config) {
		return this.configRepository.save(config);
	}

	@Override
	public void delete(Integer id) {
		//TODO 进行数据校验，有历史数据不能删除
		this.configRepository.deleteById(id);
	}

	@Override
	public Config update(Config config) {
		return this.configRepository.save(config);
	}

	@Override
	public void commit(Change change) {
		this.draftManager.commit(change);
	}

	@Override
	public void commit(CommitOperation commitOperation) {
		this.draftManager.commit(commitOperation);
	}

	@Override
	public void discard(Integer id) {
		this.draftManager.discard(id);
	}

	@Override
	public void revert(Integer configId, String property) {
		this.draftManager.revert(configId, property);
	}

	@Override
	public void publish(PublishOperation publishOperation) {

		this.publishManager.publish(publishOperation);

		//更改配置文件状态为无修改
		this.applicationEventPublisher.publishEvent(new ConfigPublishEvent(publishOperation));

	}

	@Override
	public void push(PushOperation pushOperation) {
		Config config = this.configRepository.findById(pushOperation.getConfigId()).orElseThrow(() ->
				new IllegalArgumentException("Special config dose not exists"));

		if (CollectionUtils.isEmpty(pushOperation.getDestinations())) {
			pushOperation.getDestinations().add("**");
		}

		pushOperation.getDestinations().forEach(destination -> {
			Push result = pushManager.push(config, destination);
			this.applicationEventPublisher.publishEvent(new ConfigPushEvent(result));
		});
	}

	/**
	 * 检测更改
	 *
	 * @param configId
	 * @param items
	 * @return
	 */
	@Override
	public List<DetectedChange> detect(Integer configId, Map<String, Object> items) {
		return this.draftManager.detect(configId, items);
	}

	/**
	 * 获取更改记录
	 *
	 * @param configId
	 * @return
	 */
	@Override
	public List<ChangeVO> getChanges(Integer configId) {
		List<Change> changes = this.changeRepository.getByConfig_IdAndPublished(configId, false);
		return changes.stream().map(change -> {
			ChangeVO changeVO = new ChangeVO();
			BeanUtils.copyProperties(change, changeVO, "config", "publish");
			return changeVO;
		}).collect(Collectors.toList());
	}

	/**
	 * 获取更改记录
	 *
	 * @param publishId
	 * @return
	 */
	@Override
	public List<ChangeVO> getChangesByPublishId(Integer publishId) {
		List<Change> changes = this.changeRepository.getByPublish_Id(publishId);
		return changes.stream().map(change -> {
			ChangeVO changeVO = new ChangeVO();
			BeanUtils.copyProperties(change, changeVO, "config", "publish");
			return changeVO;
		}).collect(Collectors.toList());
	}

	/**
	 * 获取发布记录
	 *
	 * @param configId
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<PublishVO> getPublishes(Integer configId, Pageable pageable) {
		return this.publishManager.getPublishes(configId, pageable);
	}

	/**
	 * 获取发布内容
	 *
	 * @param publishId
	 * @return
	 */
	@Override
	public String getPublishContent(Integer publishId) {
		return this.publishManager.getPublishContent(publishId);
	}

	/**
	 * 回滚配置文件
	 *
	 * @param rollbackOperation
	 */
	@Override
	public void rollback(RollbackOperation rollbackOperation) {
		this.publishManager.rollback(rollbackOperation);
	}

	@Override
	public void init(Integer id, Map<String, Object> items) {
		Config config = this.configRepository.findById(id).get();
		this.draftManager.overwrite(config, items);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
