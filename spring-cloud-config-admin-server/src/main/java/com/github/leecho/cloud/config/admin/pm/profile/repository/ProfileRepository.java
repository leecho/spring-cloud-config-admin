package com.github.leecho.cloud.config.admin.pm.profile.repository;

import com.github.leecho.cloud.config.admin.pm.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
public interface ProfileRepository extends JpaRepository<Profile, Integer> {


	/**
	 * 根据项目ID获取环境列表
	 * @param projectId
	 * @return
	 */
	List<Profile> findByProject_Id(Integer projectId);
}
