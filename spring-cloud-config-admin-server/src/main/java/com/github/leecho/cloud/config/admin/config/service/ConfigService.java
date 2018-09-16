package com.github.leecho.cloud.config.admin.config.service;

import com.github.leecho.cloud.config.admin.config.entity.*;
import com.github.leecho.cloud.config.admin.config.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
public interface ConfigService {

	/**
	 * 添加配置文件
	 *
	 * @param config 配置文件数据
	 * @return 配置文件
	 */
	Config save(Config config);

	/**
	 * 删除配置文件
	 *
	 * @param id 配置文件ID
	 */
	void delete(Integer id);

	/**
	 * 修改配置文件
	 *
	 * @param config 配置文件
	 * @return 配置文件
	 */
	Config update(Config config);

	List<Config> findByProfile(Integer profileId);

	/**
	 * 提交变更
	 *
	 * @param change
	 */
	Draft commit(Change change);

	/**
	 * 批量变更操作
	 *
	 * @param commitRequest 提交操作
	 */
	List<Draft> commit(CommitRequest commitRequest);

	/**
	 * 还原配置文件
	 *
	 * @param id 配置文件ID
	 */
	List<Draft> discard(Integer id);


	/**
	 * 还原草稿中配置项
	 *  @param configId 配置文件ID
	 * @param property 配置项
	 */
	Draft revert(Integer configId, String property);

	/**
	 * 发布配置文件
	 *
	 * @param publishRequest 推送操作
	 */
	Publish publish(PublishRequest publishRequest);

	/**
	 * 推送配置文件
	 *
	 * @param pushOperation 推送请求
	 */
	List<Push> push(PushRequest pushOperation);

	/**
	 * 根据提供的配置项与数据库中的数据进行对比，检测更改
	 *
	 * @param configId 配置文件ID
	 * @param items    配置项
	 * @return 变更列表
	 */
	List<DetectedChange> detect(Integer configId, Map<String, Object> items);

	/**
	 * 根据配置文件获取未发布的更改记录
	 *
	 * @param configId 配置文件ID
	 * @return 变更记录列表
	 */
	List<ChangeVO> getChanges(Integer configId);

	/**
	 * 根据配置文件和发布记录ID获取更改记录
	 *
	 * @param publishId 配置文件ID
	 * @return 变更记录列表
	 */
	List<ChangeVO> getChangesByPublishId(Integer publishId);

	/**
	 * 根据配置文件分页查询发布记录
	 *
	 * @param configId 配置文件ID
	 * @param pageable 分页配置
	 * @return 变更记录列表
	 */
	Page<PublishVO> getPublishes(Integer configId, Pageable pageable);

	/**
	 * 获取推送记录
	 *
	 * @param configId 配置文件ID
	 * @param pageable 分页配置
	 * @return 推送记录列表
	 */
	Page<Push> getPushes(Integer configId, Pageable pageable);

	/**
	 * 根据发布记录ID获取发布的配置文件内容
	 *
	 * @param publishId 配置文件ID
	 * @return 配置内容
	 */
	String getPublishContent(Integer publishId);

	/**
	 * 回滚配置文件
	 *
	 * @param rollbackRequest 回滚请求
	 */
	Publish rollback(RollbackRequest rollbackRequest);

	/**
	 * 初始化配置
	 *
	 * @param id    配置文件ID
	 * @param items 配置项
	 */
	List<Draft> loadItems(Integer id, Map<String, Object> items);

	/**
	 * 获取配置文件信息
	 *
	 * @param id
	 * @return 配置信息
	 */
	Config get(Integer id);

	/**
	 * 获取草稿内容
	 *
	 * @param id 配置文件ID
	 * @return 草稿列表
	 */
	List<Draft> getDrafts(Integer id);
}
