package com.github.leecho.cloud.config.admin.config.repository;

import com.github.leecho.cloud.config.admin.config.entity.Push;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LIQIU
 * @date 2018-9-7
 **/
public interface PushRepository extends JpaRepository<Push, Integer> {
}
