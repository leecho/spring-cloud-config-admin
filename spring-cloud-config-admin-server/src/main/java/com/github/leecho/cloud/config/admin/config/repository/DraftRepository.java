package com.github.leecho.cloud.config.admin.config.repository;

import com.github.leecho.cloud.config.admin.config.entity.Draft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
public interface DraftRepository extends JpaRepository<Draft, Integer> {

	/**
	 * 根据配置文件和配置项获取草稿
	 *
	 * @param configId
	 * @param property
	 * @return
	 */
	Draft getByConfig_IdAndAndProperty(Integer configId, String property);

	/**
	 * 删除当前配置的草稿
	 *
	 * @param configId
	 */
	void deleteByConfig_Id(Integer configId);

	/**
	 * 根据配置文件获取草稿
	 * @param configId
	 * @return
	 */
	List<Draft> findByConfig_Id(Integer configId);

}
