package com.github.leecho.cloud.config.admin.config.controller;

import com.github.leecho.cloud.config.admin.config.entity.Config;
import com.github.leecho.cloud.config.admin.config.model.*;
import com.github.leecho.cloud.config.admin.config.service.ConfigService;
import com.github.leecho.cloud.config.admin.utils.PropertiesUtils;
import com.github.leecho.cloud.config.admin.utils.YamlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/save")
	@ApiOperation("保存配置文件信息")
	public Config save(@Validated @RequestBody Config config) {
		return configService.save(config);
	}

	@PostMapping(value = "/update")
	@ApiOperation(value = "修改配置文件信息")
	public Config update(@Validated @RequestBody Config config) {
		return configService.update(config);
	}

	@PostMapping(value = "/delete/{id}")
	@ApiOperation(("删除配置文件"))
	public void delete(@PathVariable @ApiParam(name = "配置文件ID", required = true) Integer id) {
		configService.delete(id);
	}

	@PostMapping(value = "/publish")
	@ApiOperation("发布配置")
	public void publish(@RequestBody @Validated @ApiParam(value = "发布配置", required = true)
								PublishOperation publishOperation) {
		this.configService.publish(publishOperation);
	}

	@PostMapping(value = "/rollback")
	@ApiOperation("回滚配置")
	public void rollback(@RequestBody @Validated @ApiParam(value = "回滚配置", required = true)
								 PublishOperation publishOperation) {
		this.configService.publish(publishOperation);
	}


	@PostMapping(value = "/getPublishes/{id}")
	@ApiOperation("获取发布记录")
	public ResponseEntity<Page<PublishVO>> getPublishes(@PathVariable("id") @ApiParam(value = "配置文件ID", required = true) Integer id,
														@ApiParam(value = "分页数据", required = true) Pageable pageable) {
		return ResponseEntity.ok(this.configService.getPublishes(id, pageable));
	}

	@ApiOperation("推送配置")
	@PostMapping(value = "/push")
	public void push(@RequestBody @Validated @ApiParam(value = "推送配置操作", required = true)
							 PushOperation pushOperation) {
		this.configService.push(pushOperation);
	}

	@ApiOperation(value = "提交变更")
	@PostMapping(value = "/commit")
	public void commit(@RequestBody @Validated @ApiParam(value = "提交变更操作", required = true)
							   CommitOperation commitOperation) {
		this.configService.commit(commitOperation);
	}


	@ApiOperation(value = "获取变更记录")
	@PostMapping(value = "/getChanges/{id}")
	public ResponseEntity<List<ChangeVO>> getChanges(@PathVariable("id") @ApiParam(value = "配置文件ID", required = true) Integer id) {
		return ResponseEntity.ok(this.configService.getChanges(id));
	}


	@PostMapping(value = "/discard/{id}")
	@ApiOperation("放弃更改")
	public void discard(@PathVariable @ApiParam(value = "配置文件ID", required = true) Integer id) {
		this.configService.discard(id);
	}

	@PostMapping("/revert/{id}/{property}")
	@ApiOperation(("还原配置项"))
	public void revert(@PathVariable @ApiParam(value = "配置文件ID", required = true) Integer id,
					   @PathVariable @ApiParam(value = "配置项", required = true) String property) {
		this.configService.discard(id);
	}

	@PostMapping("/detect")
	@ApiOperation(value = "根据内容获取变更")
	public ResponseEntity<?> detect(@RequestBody @Validated @ApiParam(value = "检测变更操作", required = true)
											DetectOperation detectOperation) {
		Map<String, Object> data;

		if (!configFormats.contains(detectOperation.getFormat())) {
			return ResponseEntity.badRequest().body("配置文件格式非法");
		}

		try {
			if (YAML_FORMAT.equals(detectOperation.getFormat())) {
				data = YamlUtils.convertYamlToMap(detectOperation.getContent());
			} else {
				data = PropertiesUtils.conventPropertiesToMap(detectOperation.getContent());
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("读取配置文件内容发生错误");
		}
		return ResponseEntity.ok(this.configService.detect(detectOperation.getConfigId(), data));
	}
}
