package com.github.leecho.cloud.config.admin.config.repository;

import com.github.leecho.cloud.config.admin.config.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
public interface ConfigRepository extends JpaRepository<Config, Integer> {

	/**
	 * 根据环境获取配置文件列表
	 *
	 * @param profileId
	 * @return
	 */
	List<Config> findByProfile_Id(Integer profileId);

	/**
	 * 根据项目获取配置文件列表
	 *
	 * @param projectId
	 * @return
	 */
	List<Config> findByProject_Id(Integer projectId);

}
