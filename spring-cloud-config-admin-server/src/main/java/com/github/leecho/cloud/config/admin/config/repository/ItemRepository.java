package com.github.leecho.cloud.config.admin.config.repository;

import com.github.leecho.cloud.config.admin.config.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
public interface ItemRepository extends JpaRepository<Item,Integer> {

	/**
	 * 获取当前配置
	 * @param application
	 * @param profile
	 * @param label
	 * @return
	 */
	List<Item> findByApplicationAndProfileAndLabel(String application, String profile, String label);

	/**
	 * 根据应用，环境，标签删除配置项
	 * @param application
	 * @param profile
	 * @param label
	 */
	void deleteByApplicationAndProfileAndLabel(String application, String profile, String label);

}
