package com.github.leecho.cloud.config.admin.uc.user.repository;

import com.github.leecho.cloud.config.admin.uc.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LIQIU
 * @date 2018-9-10
 **/
public interface UserRepository extends JpaRepository<User, Integer> {
}
