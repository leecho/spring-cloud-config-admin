package com.github.leecho.cloud.config.admin.pm.profile.service.impl;

import com.github.leecho.cloud.config.admin.pm.profile.entity.Profile;
import com.github.leecho.cloud.config.admin.pm.profile.repository.ProfileRepository;
import com.github.leecho.cloud.config.admin.pm.profile.service.ProfileService;
import com.github.leecho.cloud.config.admin.pm.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProfileServiceImpl implements ProfileService {

	private final ProfileRepository profileRepository;

	private final ProjectRepository projectRepository;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, ProjectRepository projectRepository) {
		this.profileRepository = profileRepository;
		this.projectRepository = projectRepository;
	}

	@Override
	public Profile save(Profile profile) {
		profile.setProject(this.projectRepository.findById(profile.getProject().getId()).orElseThrow(() -> new IllegalArgumentException("Special Project dose not exists")));
		profile.setEnable(true);
		return profileRepository.save(profile);
	}

	@Override
	public Profile update(Profile profile) {
		profile.setProject(this.projectRepository.findById(profile.getProject().getId()).orElseThrow(() -> new IllegalArgumentException("Special Project dose not exists")));
		return profileRepository.save(profile);
	}

	@Override
	public void delete(Integer id) {
		this.profileRepository.deleteById(id);
	}

	@Override
	public Profile get(Integer id) {
		return this.profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Special profile dose not extis"));
	}

	@Override
	public List<Profile> list() {
		return this.profileRepository.findAll();
	}

	@Override
	public List<Profile> findByProjectId(Integer projectId) {
		return this.profileRepository.findByProject_Id(projectId);
	}
}
