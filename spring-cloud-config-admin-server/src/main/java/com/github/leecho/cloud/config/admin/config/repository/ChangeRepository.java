package com.github.leecho.cloud.config.admin.config.repository;

import com.github.leecho.cloud.config.admin.config.entity.Change;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
public interface ChangeRepository extends JpaRepository<Change, Integer> {

	/**
	 * 根据配置获取更改记录
	 *
	 * @param configId
	 * @param published
	 * @return
	 */
	List<Change> getByConfig_IdAndPublished(Integer configId, Boolean published);

	/**
	 * 根据发布记录获取更改记录
	 *
	 * @param publishId
	 * @return
	 */
	List<Change> getByPublish_Id(Integer publishId);

	/**
	 * 根据配置文件删除更改记录
	 *
	 * @param configId
	 * @param published
	 */
	void deleteByConfig_IdAndPublished(Integer configId, Boolean published);

	/**
	 * 根据配置文件和属性获取更改记录
	 *
	 * @param configId
	 * @param property
	 * @return
	 */
	List<Change> getByConfig_IdAndProperty(Integer configId, String property);

}
