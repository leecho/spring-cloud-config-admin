package com.github.leecho.cloud.config.admin.config.service.impl;

import com.github.leecho.cloud.config.admin.config.entity.*;
import com.github.leecho.cloud.config.admin.config.event.ConfigPublishEvent;
import com.github.leecho.cloud.config.admin.config.event.ConfigPushEvent;
import com.github.leecho.cloud.config.admin.config.manager.DraftManager;
import com.github.leecho.cloud.config.admin.config.manager.PublishManager;
import com.github.leecho.cloud.config.admin.config.manager.PushManager;
import com.github.leecho.cloud.config.admin.config.model.*;
import com.github.leecho.cloud.config.admin.config.repository.ChangeRepository;
import com.github.leecho.cloud.config.admin.config.repository.ConfigRepository;
import com.github.leecho.cloud.config.admin.config.repository.DraftRepository;
import com.github.leecho.cloud.config.admin.config.repository.PushRepository;
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

import java.util.ArrayList;
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
	private DraftRepository draftRepository;

	@Autowired
	private PushRepository pushRepository;

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
	public List<Config> findByProfile(Integer profileId) {
		return this.configRepository.findByProfile_Id(profileId);
	}


	@Override
	public Draft commit(Change change) {
		return this.draftManager.commit(change);
	}

	@Override
	public List<Draft> commit(CommitOperation commitOperation) {
		return this.draftManager.commit(commitOperation);
	}

	@Override
	public List<Draft> discard(Integer id) {
		return this.draftManager.discard(id);
	}

	@Override
	public Draft revert(Integer configId, String property) {
		return this.draftManager.revert(configId, property);
	}

	@Override
	public Publish publish(PublishOperation publishOperation) {

		Publish publish = this.publishManager.publish(publishOperation);

		//更改配置文件状态为无修改
		this.applicationEventPublisher.publishEvent(new ConfigPublishEvent(publishOperation));

		return publish;
	}

	@Override
	public List<Push> push(PushOperation pushOperation) {
		List<Push> pushes = new ArrayList<>();
		Config config = this.configRepository.findById(pushOperation.getConfigId()).orElseThrow(() ->
				new IllegalArgumentException("Special config dose not exists"));

		if (CollectionUtils.isEmpty(pushOperation.getDestinations())) {
			pushOperation.getDestinations().add("**");
		}

		pushOperation.getDestinations().forEach(destination -> {
			Push result = pushManager.push(config, destination);
			pushes.add(result);
			this.applicationEventPublisher.publishEvent(new ConfigPushEvent(result));
		});
		return pushes;
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
	 * 获取推送记录
	 *
	 * @param configId
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<Push> getPushes(Integer configId, Pageable pageable) {
		return this.pushRepository.getByConfig_Id(configId, pageable);
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
	public Publish rollback(RollbackOperation rollbackOperation) {
		return this.publishManager.rollback(rollbackOperation);
	}

	@Override
	public List<Draft> loadItems(Integer id, Map<String, Object> items) {
		Config config = this.configRepository.findById(id).get();
		return this.draftManager.overwrite(config, items);
	}

	@Override
	public Config get(Integer id) {
		return this.configRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Special config dose not exists"));
	}

	@Override
	public List<Draft> getDrafts(Integer id) {
		return this.draftRepository.findByConfig_Id(id);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
