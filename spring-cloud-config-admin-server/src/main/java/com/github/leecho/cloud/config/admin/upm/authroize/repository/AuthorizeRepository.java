package com.github.leecho.cloud.config.admin.upm.authroize.repository;

import com.github.leecho.cloud.config.admin.upm.authroize.entity.Authorize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-7
 **/
public interface AuthorizeRepository extends JpaRepository<Authorize, Integer> {

	/**
	 * 根据角色ID获取授权
	 *
	 * @param roleId
	 * @return
	 */
	List<Authorize> findByRole_Id(Integer roleId);

}
