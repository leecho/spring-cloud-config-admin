package com.github.leecho.cloud.config.admin.pm.project.repository;

import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
