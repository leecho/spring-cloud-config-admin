package com.github.leecho.cloud.config.admin.config.repository;

import com.github.leecho.cloud.config.admin.config.entity.Publish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
public interface PublishRepository extends JpaRepository<Publish, Integer> {

	/**
	 * 获取发布记录
	 *
	 * @param configId
	 * @param current
	 * @return
	 */
	Publish getByConfig_IdAndCurrent(Integer configId, Boolean current);

	Page<Publish> findByConfig_Id(Integer configId, Pageable pageable);

}
