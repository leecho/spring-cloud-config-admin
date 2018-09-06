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
	 * 获取授权的环境
	 * @param projectId
	 * @param userId
	 * @return
	 */
	@Query("from Profile profile" +
			"	left join Permission permission on permission.profile.id = profile.id" +
			"	left join Member member1 on member1.role.id = permission.role.id" +
			" where profile.project.id = :projectId and member1.user.id = :userId")
	List<Profile> getAuthorizedProfiles(@Param("projectId") Integer projectId, @Param("userId") Integer userId);

}
