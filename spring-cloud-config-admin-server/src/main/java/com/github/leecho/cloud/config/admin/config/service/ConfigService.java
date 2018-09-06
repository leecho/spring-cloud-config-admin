package com.github.leecho.cloud.config.admin.config.service;

import com.github.leecho.cloud.config.admin.config.entity.Change;
import com.github.leecho.cloud.config.admin.config.entity.Config;
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

	void commit(Change change);

	/**
	 * 批量变更操作
	 *
	 * @param commitOperation 提交操作
	 */
	void commit(CommitOperation commitOperation);

	/**
	 * 还原配置文件
	 *
	 * @param id 配置文件ID
	 */
	void discard(Integer id);


	/**
	 * 还原草稿中配置项
	 *
	 * @param configId 配置文件ID
	 * @param property 配置项
	 */
	void revert(Integer configId, String property);

	/**
	 * 发布配置文件
	 *
	 * @param publishOperation 推送操作
	 */
	void publish(PublishOperation publishOperation);

	/**
	 * 推送配置文件
	 *
	 * @param pushOperation 推送请求
	 */
	void push(PushOperation pushOperation);

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
	 * 根据发布记录ID获取发布的配置文件内容
	 *
	 * @param publishId 配置文件ID
	 * @return 配置内容
	 */
	String getPublishContent(Integer publishId);

	/**
	 * 回滚配置文件
	 *
	 * @param rollbackOperation 回滚请求
	 */
	void rollback(RollbackOperation rollbackOperation);

	/**
	 * 初始化配置
	 *
	 * @param id    配置文件ID
	 * @param items 配置项
	 */
	void init(Integer id, Map<String, Object> items);
}
