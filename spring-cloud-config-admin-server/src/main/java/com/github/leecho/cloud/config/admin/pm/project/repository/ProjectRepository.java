package com.github.leecho.cloud.config.admin.pm.project.repository;

import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	/**
	 * 获取已授权的项目
	 *
	 * @param userId
	 * @return
	 */
	@Query("from Project project " +
			" left join Member member1 on member1.project.id = project.id" +
			" where member1.user.id = :userId")
	List<Project> getAuthorizedProject(@Param("userId") Integer userId);

}
