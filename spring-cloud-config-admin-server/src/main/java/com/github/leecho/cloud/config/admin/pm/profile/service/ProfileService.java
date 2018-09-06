package com.github.leecho.cloud.config.admin.pm.profile.service;


import com.github.leecho.cloud.config.admin.pm.profile.entity.Profile;

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
}
