package com.github.leecho.cloud.config.admin.uc.user.repository;

import com.github.leecho.cloud.config.admin.uc.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-7
 **/
public interface MemberRepository extends JpaRepository<Member, Integer> {

	/**
	 * 根据用户获取成员
	 * @param integer
	 * @return
	 */
	List<Member> findByUser_Id(Integer integer);

	/**
	 * 根据实体ID和实体类型获取成员
	 * @param entityId
	 * @param entityType
	 * @return
	 */
	List<Member> findByEntityIdAndEntityType(Integer entityId,String entityType);

}
