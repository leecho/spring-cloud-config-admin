package com.github.leecho.cloud.config.admin.pm.project.controller;

import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import com.github.leecho.cloud.config.admin.pm.project.service.ProjectService;
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
@RestController
@Api(tags = "项目管理接口")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@PostMapping("/project")
	@ApiOperation("保存项目信息")
	public ResponseEntity<Project> save(@Validated @RequestBody @ApiParam(value = "项目信息", required = true) Project project) {
		return ResponseEntity.ok(projectService.save(project));
	}

	@PutMapping("/project")
	@ApiOperation(value = "修改项目信息")
	public ResponseEntity<Project> update(@Validated @RequestBody @ApiParam(value = "项目信息", required = true) Project project) {
		return ResponseEntity.ok(projectService.update(project));
	}

	@DeleteMapping(value = "/project/{id}")
	@ApiOperation(("删除项目"))
	public void delete(@PathVariable @ApiParam(name = "项目ID", required = true) Integer id) {
		projectService.delete(id);
	}

	@GetMapping(value = "/project/{id}")
	@ApiOperation("获取项目信息")
	public ResponseEntity<Project> get(@PathVariable("id") @ApiParam(value = "项目ID", required = true) Integer id) {
		return ResponseEntity.ok(this.projectService.get(id));
	}

	@GetMapping(value = "/projects")
	@ApiOperation("获取项目列表")
	public ResponseEntity<List<Project>> list() {
		return ResponseEntity.ok(this.projectService.list());
	}
}
