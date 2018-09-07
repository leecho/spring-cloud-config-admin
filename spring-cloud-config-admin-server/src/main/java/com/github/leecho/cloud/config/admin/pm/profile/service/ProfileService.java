package com.github.leecho.cloud.config.admin.pm.profile.service;


import com.github.leecho.cloud.config.admin.pm.profile.entity.Profile;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
public interface ProfileService {

	/**
	 * 保存项目
	 *
	 * @param project
	 * @return
	 */
	Profile save(Profile project);

	/**
	 * 修改项目
	 *
	 * @param project
	 * @return
	 */
	Profile update(Profile project);

	/**
	 * 删除项目
	 *
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 获取环境配置信息
	 *
	 * @param id
	 * @return
	 */
	Profile get(Integer id);

	/**
	 * 获取环境配置列表
	 *
	 * @return
	 */
	List<Profile> list();

	/**
	 * 根据项目ID获取环境列表
	 * @param projectId
	 * @return
	 */
	List<Profile> findByProjectId(Integer projectId);
}
