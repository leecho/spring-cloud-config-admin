package com.github.leecho.cloud.config.admin.config.controller;

import com.github.leecho.cloud.config.admin.config.entity.Config;
import com.github.leecho.cloud.config.admin.config.entity.Draft;
import com.github.leecho.cloud.config.admin.config.entity.Publish;
import com.github.leecho.cloud.config.admin.config.entity.Push;
import com.github.leecho.cloud.config.admin.config.model.*;
import com.github.leecho.cloud.config.admin.config.service.ConfigService;
import com.github.leecho.cloud.config.admin.utils.PropertiesUtils;
import com.github.leecho.cloud.config.admin.utils.YamlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-9-4
 **/
@RestController
@RequestMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "配置文件接口")
public class ConfigController {

	private static final String SUCCESS = "success";

	private static final String YAML_FORMAT = "yaml";

	private static final String PROPERTIES_FORMAT = "properties";

	private List<String> configFormats = Arrays.asList("yaml", "properties");

	private ConfigService configService;

	@Autowired
	public ConfigController(ConfigService configService) {
		this.configService = configService;
	}

	@PostMapping
	@ApiOperation("保存配置文件信息")
	@PreAuthorize("hasPermission(#config,'save')")
	public ResponseEntity<Config> save(@Validated @RequestBody @ApiParam(value = "配置文件信息", required = true) Config config) {
		return ResponseEntity.ok(configService.save(config));
	}

	@PutMapping
	@ApiOperation(value = "修改配置文件信息")
	public ResponseEntity<Config> update(@Validated @RequestBody @ApiParam(value = "配置文件信息", required = true) Config config) {
		return ResponseEntity.ok(configService.update(config));
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(("删除配置文件"))
	public void delete(@PathVariable @ApiParam(name = "配置文件ID", required = true) Integer id) {
		configService.delete(id);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation("获取配置文件信息")
	public ResponseEntity<Config> get(@PathVariable("id") @ApiParam(value = "配置文件ID", required = true) Integer id) {
		return ResponseEntity.ok(this.configService.get(id));
	}

	@GetMapping(value = "/getConfigs/{profile}")
	@ApiOperation("获取配置文件列表")
	public ResponseEntity<List<Config>> list(@PathVariable("profile") @ApiParam(value = "配置环境ID", required = true) Integer profile) {
		return ResponseEntity.ok(this.configService.findByProfile(profile));
	}


	@GetMapping(value = "/getDrafts/{id}")
	@ApiOperation(value = "获取配置文件草稿", notes = "获取配置文件草稿")
	public ResponseEntity<List<Draft>> getDrafts(@PathVariable("id") @ApiParam(value = "配置文件ID", required = true) Integer id) {
		return ResponseEntity.ok(this.configService.getDrafts(id));
	}

	@PostMapping(value = "/publish")
	@ApiOperation("发布配置")
	public ResponseEntity<Publish> publish(@RequestBody @Validated @ApiParam(value = "发布配置", required = true)
												   PublishRequest publishRequest) {
		return ResponseEntity.ok(this.configService.publish(publishRequest));
	}

	@PostMapping(value = "/rollback")
	@ApiOperation("回滚配置")
	public ResponseEntity<Publish> rollback(@RequestBody @Validated @ApiParam(value = "回滚配置", required = true)
													RollbackRequest rollbackRequest) {
		return ResponseEntity.ok(this.configService.rollback(rollbackRequest));
	}


	@GetMapping(value = "/getPublishes/{id}")
	@ApiOperation("获取发布记录")
	public ResponseEntity<Page<PublishVO>> getPublishes(@PathVariable("id") @ApiParam(value = "配置文件ID", required = true) Integer id,
														@ApiParam(value = "分页数据", required = true) Pageable pageable) {
		return ResponseEntity.ok(this.configService.getPublishes(id, pageable));
	}

	@ApiOperation("推送配置")
	@PostMapping(value = "/push")
	public ResponseEntity<List<Push>> push(@RequestBody @Validated @ApiParam(value = "推送配置操作", required = true)
												   PushRequest pushOperation) {
		return ResponseEntity.ok(this.configService.push(pushOperation));
	}

	@GetMapping(value = "/getPushes/{id}")
	@ApiOperation("获取推送记录")
	public ResponseEntity<Page<Push>> getPushes(@PathVariable("id") @ApiParam(value = "配置文件ID", required = true) Integer id,
												@ApiParam(value = "分页数据", required = true) Pageable pageable) {
		return ResponseEntity.ok(this.configService.getPushes(id, pageable));
	}


	@ApiOperation(value = "提交变更")
	@PostMapping(value = "/commit")
	public ResponseEntity<List<Draft>> commit(@RequestBody @Validated @ApiParam(value = "提交变更操作", required = true)
													  CommitRequest commitRequest) {
		return ResponseEntity.ok(this.configService.commit(commitRequest));
	}


	@ApiOperation(value = "获取变更记录")
	@GetMapping(value = "/getChanges/{id}")
	public ResponseEntity<List<ChangeVO>> getChanges(@PathVariable("id") @ApiParam(value = "配置文件ID", required = true) Integer id) {
		return ResponseEntity.ok(this.configService.getChanges(id));
	}


	@PostMapping(value = "/discard/{id}")
	@ApiOperation("放弃更改")
	public ResponseEntity<List<Draft>> discard(@PathVariable @ApiParam(value = "配置文件ID", required = true) Integer id) {
		return ResponseEntity.ok(this.configService.discard(id));
	}

	@PostMapping("/revert/{id}/{property}")
	@ApiOperation(("还原配置项"))
	public ResponseEntity<Draft> revert(@PathVariable @ApiParam(value = "配置文件ID", required = true) Integer id,
										@PathVariable @ApiParam(value = "配置项", required = true) String property) {
		return ResponseEntity.ok(this.configService.revert(id, property));
	}

	@PostMapping("/import/{id}")
	@ApiOperation("导入配置")
	public ResponseEntity<?> importFile(@PathVariable("id") @ApiParam(value = "配置文件ID", required = true) Integer id,
										@ApiParam(value = "配置文件", required = true) MultipartFile file) {
		Map<String, Object> items;
		String format = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		try {
			if (YAML_FORMAT.equals(format)) {
				items = YamlUtils.convertYamlToMap(file.getInputStream());
			} else {
				items = PropertiesUtils.conventPropertiesToMap(file.getInputStream());
			}
			return ResponseEntity.ok(this.configService.loadItems(id, items));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("读取配置文件内容发生错误");
		}
	}

	@PutMapping("/detect")
	@ApiOperation(value = "根据内容获取变更")
	public ResponseEntity<?> detect(@RequestBody @Validated @ApiParam(value = "检测变更操作", required = true)
											DetectRequest detectRequest) {
		Map<String, Object> data;

		if (!configFormats.contains(detectRequest.getFormat())) {
			return ResponseEntity.badRequest().body("配置文件格式非法");
		}

		try {
			if (YAML_FORMAT.equals(detectRequest.getFormat())) {
				data = YamlUtils.convertYamlToMap(detectRequest.getContent());
			} else {
				data = PropertiesUtils.conventPropertiesToMap(detectRequest.getContent());
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("读取配置文件内容发生错误");
		}
		return ResponseEntity.ok(this.configService.detect(detectRequest.getConfigId(), data));
	}
}
