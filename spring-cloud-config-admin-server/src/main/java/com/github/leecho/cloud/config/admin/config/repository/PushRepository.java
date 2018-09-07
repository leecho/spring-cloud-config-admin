package com.github.leecho.cloud.config.admin.config.repository;

import com.github.leecho.cloud.config.admin.config.entity.Push;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author LIQIU
 * @date 2018-9-7
 **/
public interface PushRepository extends JpaRepository<Push, Integer> {

	/**
	 * 根据配置文件ID获取推送记录
	 *
	 * @param configId 配置文件ID
	 * @return 推送记录列表
	 */
	Page<Push> getByConfig_Id(Integer configId, Pageable pageable);
}
