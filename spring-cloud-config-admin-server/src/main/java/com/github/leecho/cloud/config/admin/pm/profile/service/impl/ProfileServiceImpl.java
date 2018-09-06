package com.github.leecho.cloud.config.admin.pm.profile.service.impl;

import com.github.leecho.cloud.config.admin.pm.profile.entity.Profile;
import com.github.leecho.cloud.config.admin.pm.profile.repository.ProfileRepository;
import com.github.leecho.cloud.config.admin.pm.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public Profile update(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public void delete(Integer id) {
		this.profileRepository.deleteById(id);
	}
}
