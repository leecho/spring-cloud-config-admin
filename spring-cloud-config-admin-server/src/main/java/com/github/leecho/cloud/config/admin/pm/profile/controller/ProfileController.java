package com.github.leecho.cloud.config.admin.pm.profile.controller;

import com.github.leecho.cloud.config.admin.pm.profile.entity.Profile;
import com.github.leecho.cloud.config.admin.pm.profile.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-7
 **/
@Api(tags = "环境管理接口")
@RestController
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@PostMapping("/profile")
	@ApiOperation("保存环境信息")
	public ResponseEntity<Profile> save(@Validated @RequestBody @ApiParam(value = "环境信息", required = true) Profile profile) {
		return ResponseEntity.ok(profileService.save(profile));
	}

	@PutMapping("/profile")
	@ApiOperation(value = "修改环境信息")
	public ResponseEntity<Profile> update(@Validated @RequestBody @ApiParam(value = "环境信息", required = true) Profile profile) {
		return ResponseEntity.ok(profileService.update(profile));
	}

	@DeleteMapping(value = "/profile/{id}")
	@ApiOperation(("删除环境"))
	public void delete(@PathVariable @ApiParam(name = "环境ID", required = true) Integer id) {
		profileService.delete(id);
	}

	@GetMapping(value = "/profile/{id}")
	@ApiOperation("获取环境信息")
	public ResponseEntity<Profile> get(@PathVariable("id") @ApiParam(value = "环境ID", required = true) Integer id) {
		return ResponseEntity.ok(this.profileService.get(id));
	}

	@GetMapping(value = "/profiles")
	@ApiOperation("获取环境列表")
	public ResponseEntity<List<Profile>> list() {
		return ResponseEntity.ok(this.profileService.list());
	}

	@GetMapping(value = "/profiles/{projectId}")
	@ApiOperation("获取环境列表")
	public ResponseEntity<List<Profile>> findByProjectId(@PathVariable("projectId") @ApiParam(value = "项目ID", required = true) Integer projectId) {
		return ResponseEntity.ok(this.profileService.findByProjectId(projectId));
	}

}
