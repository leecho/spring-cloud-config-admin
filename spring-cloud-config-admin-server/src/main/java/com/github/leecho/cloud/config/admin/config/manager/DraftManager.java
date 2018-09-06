package com.github.leecho.cloud.config.admin.config.manager;

import com.github.leecho.cloud.config.admin.config.entity.Change;
import com.github.leecho.cloud.config.admin.config.entity.Config;
import com.github.leecho.cloud.config.admin.config.entity.Draft;
import com.github.leecho.cloud.config.admin.config.entity.Item;
import com.github.leecho.cloud.config.admin.config.enums.ChangeType;
import com.github.leecho.cloud.config.admin.config.model.CommitOperation;
import com.github.leecho.cloud.config.admin.config.model.DetectedChange;
import com.github.leecho.cloud.config.admin.config.repository.ChangeRepository;
import com.github.leecho.cloud.config.admin.config.repository.ConfigRepository;
import com.github.leecho.cloud.config.admin.config.repository.DraftRepository;
import com.github.leecho.cloud.config.admin.config.repository.ItemRepository;
import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 草稿管理器
 *
 * @author LIQIU
 * @date 2018-9-3
 **/
@Component
@Slf4j
public class DraftManager {

	@Autowired
	private DraftRepository draftRepository;

	@Autowired
	private ChangeRepository changeRepository;

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	private ItemRepository itemRepository;


	public void commit(Change change) {

		String principal = SecurityContextHolder.getContext().getAuthentication().getName();

		Integer configId = change.getConfig().getId();

		Config config = this.configRepository.findById(configId).orElseThrow(() -> new IllegalArgumentException("Special config#" + configId + " is not found"));

		Draft currentDraft = draftRepository.getByConfig_IdAndAndProperty(configId, change.getProperty());

		Assert.notNull(change.getType(), "Change type must not be null");

		if (ChangeType.CREATE.getValue().equals(change.getType())) {

			Assert.isNull(currentDraft, String.format("Duplicate property:%s", change.getProperty()));

			Draft draft = Draft.builder().config(config).property(change.getProperty()).value(change.getCurrentValue())
					.originValue(change.getCurrentValue()).createdBy(principal).createdDate(new Date()).lastUpdateBy(principal).lastUpdateDate(new Date()).build();

			this.draftRepository.save(draft);

			log.info("Created config item [{}:{}], context:[application:{},profile:{},label:{}],message: {}",
					change.getProperty(), change.getCurrentValue(), config.getProject().getCode(), config.getProfile().getCode(),
					config.getLabel(), change.getMessage());
		} else {

			Assert.notNull(currentDraft, String.format("Special property: %s is not found", change.getProperty()));

			change.setOriginValue(currentDraft.getValue());

			if (ChangeType.UPDATE.getValue().equals(change.getType())) {

				Assert.isTrue(!currentDraft.getValue().equals(change.getCurrentValue()), "The change type is update but the value not changed");

				currentDraft.setValue(change.getCurrentValue());
				currentDraft.setLastUpdateBy(SecurityContextHolder.getContext().getAuthentication().getName());
				currentDraft.setLastUpdateDate(new Date());
				this.draftRepository.save(currentDraft);
				log.info("Updated config item [{}:{} -> {}], context:[application:{},profile:{},label:{}],message: {}",
						change.getProperty(), currentDraft.getValue(), change.getCurrentValue(), config.getProject().getCode(),
						config.getProfile().getCode(), config.getLabel(), change.getMessage());
			} else {
				this.draftRepository.delete(currentDraft);
				log.info("Deleted config item [{}:{}],context:[application:{},profile:{},label:{}],message: {}",
						change.getProperty(), currentDraft.getValue(), config.getProject().getCode(), config.getProfile().getCode(),
						config.getLabel(), change.getMessage());
			}
		}

		change.setChangeBy(principal);
		change.setChangeDate(new Date());
		changeRepository.save(change);
	}

	/**
	 * 还原草稿
	 *
	 * @param configId
	 * @param property
	 */
	public void revert(Integer configId, String property) {

		Draft draft = this.draftRepository.getByConfig_IdAndAndProperty(configId, property);
		Assert.notNull(draft, "Special property:" + property + " is not found");

		draft.setValue(draft.getOriginValue());
		this.draftRepository.save(draft);

	}

	/**
	 * 丢弃草稿
	 *
	 * @param configId
	 */
	public void discard(Integer configId) {

		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		Config config = configRepository.findById(configId).orElseThrow(() -> new IllegalArgumentException("Config not found"));

		//删除草稿
		this.draftRepository.deleteByConfig_Id(configId);

		//同步配置到草稿
		List<Item> items = this.itemRepository.findByApplicationAndProfileAndLabel(config.getProject().getCode(), config.getProfile().getCode(), config.getLabel());
		items.forEach(item -> {
			Draft draft = Draft.builder().config(config).originValue(item.getValue()).value(item.getValue()).property(item.getProperty())
					.createdDate(new Date()).lastUpdateDate(new Date()).createdBy(principal).lastUpdateBy(principal).build();
			this.draftRepository.save(draft);
		});

		//删除未发布变更记录
		this.changeRepository.deleteByConfig_IdAndPublished(configId, Boolean.FALSE);

		log.info("Config [{}:{}:{}] draft discard successful", config.getProject().getCode(), config.getProfile().getCode(), config.getLabel());
	}

	/**
	 * 应用草稿
	 *
	 * @param config
	 */
	public Map<String, String> apply(Config config) {

		Project project = config.getProject();
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();

		List<Draft> drafts = this.draftRepository.findByConfig_Id(config.getId());
		Map<String, String> items = new HashMap<>();

		//覆盖配置项
		this.itemRepository.deleteByApplicationAndProfileAndLabel(project.getCode(), config.getProfile().getCode(), config.getLabel());
		drafts.forEach(draft -> {
			Item item = Item.builder().application(project.getCode()).profile(config.getProfile().getCode()).label(config.getLabel())
					.property(draft.getProperty()).value(draft.getValue()).publishBy(principal).publishDate(new Date()).build();
			this.itemRepository.save(item);

			draft.setOriginValue(draft.getValue());
			this.draftRepository.save(draft);

			items.put(item.getProperty(), item.getValue());
		});

		log.info("Config [{}:{}:{}] draft apply successful", config.getProject().getCode(), config.getProfile().getCode(), config.getLabel());


		return items;
	}

	public void commit(CommitOperation commitOperation) {

		Config config = this.configRepository.findById(commitOperation.getConfigId()).orElseThrow(() -> new IllegalArgumentException("Config is not found"));

		commitOperation.getChanges().forEach(commitChange -> {

			ChangeType changeType = ChangeType.getByName(commitChange.getType());

			Assert.notNull(changeType, "Invalid change type : " + commitChange.getType());

			Change change = new Change();
			change.setConfig(config);
			change.setType(changeType.getValue());
			change.setProperty(commitChange.getProperty());
			change.setMessage(commitOperation.getMessage());
			change.setCurrentValue(commitChange.getValue());
			this.commit(change);
		});
	}

	/**
	 * 检测更改
	 *
	 * @param configId
	 * @param items
	 * @return
	 */
	public List<DetectedChange> detect(Integer configId, Map<String, Object> items) {
		List<Draft> drafts = draftRepository.findByConfig_Id(configId);

		List<DetectedChange> changes = new ArrayList<>();

		drafts.forEach(draft -> {
			Object value = items.get(draft.getProperty());
			if (value == null) {
				//删除
				DetectedChange commitChange = new DetectedChange();
				commitChange.setType(ChangeType.DELETE.getName());
				commitChange.setProperty(draft.getProperty());
				commitChange.setOriginValue(draft.getValue());
				changes.add(commitChange);
			} else if (!draft.getValue().equals(String.valueOf(value))) {
				//更新
				DetectedChange commitChange = new DetectedChange();
				commitChange.setType(ChangeType.UPDATE.getName());
				commitChange.setProperty(draft.getProperty());
				commitChange.setCurrentValue(String.valueOf(value));
				commitChange.setOriginValue(draft.getValue());
				changes.add(commitChange);
			}
			//删除已比对的配置项
			items.remove(draft.getProperty());
		});

		items.entrySet().forEach(entry -> {
			DetectedChange change = new DetectedChange();
			change.setType(ChangeType.CREATE.getName());
			change.setProperty(entry.getKey());
			change.setCurrentValue(String.valueOf(entry.getValue()));
			changes.add(change);
		});

		return changes;
	}


	/**
	 * 重写配置
	 *
	 * @param config
	 * @param items
	 */
	public void overwrite(Config config, Map<String, Object> items) {

		this.draftRepository.deleteByConfig_Id(config.getId());

		String principal = SecurityContextHolder.getContext().getAuthentication().getName();

		items.forEach((key, value) -> {
			Draft draft = Draft.builder().config(config).property(key).value(String.valueOf(value)).originValue(String.valueOf(value))
					.createdDate(new Date()).createdBy(principal).lastUpdateDate(new Date()).lastUpdateBy(principal).build();
			this.draftRepository.save(draft);
		});
	}
}
