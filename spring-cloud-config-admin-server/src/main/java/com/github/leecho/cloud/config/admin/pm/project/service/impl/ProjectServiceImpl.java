package com.github.leecho.cloud.config.admin.pm.project.service.impl;

import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import com.github.leecho.cloud.config.admin.pm.project.repository.ProjectRepository;
import com.github.leecho.cloud.config.admin.pm.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-4
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public Project save(Project project) {
		return this.projectRepository.save(project);
	}

	@Override
	public Project update(Project project) {
		return this.projectRepository.save(project);
	}

	@Override
	public void delete(Integer id) {
		this.projectRepository.deleteById(id);
	}

	@Override
	public Project get(Integer id) {
		return this.projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Special profile dose not extis"));
	}

	@Override
	public List<Project> list() {
		return this.projectRepository.findAll();
	}
}
