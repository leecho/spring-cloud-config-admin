package com.github.leecho.cloud.config.admin.pm.project.service;

import com.github.leecho.cloud.config.admin.pm.project.entity.Project;

/**
 * @author LIQIU
 * @date 2018-9-4
 **/
public interface ProjectService {

	/**
	 * 保存项目
	 * @param project
	 */
	Project save(Project project);

	/**
	 * 修改项目
	 * @param project
	 */
	Project update(Project project);

	/**
	 * 删除项目
	 * @param id
	 */
	void delete(Integer id);
}
