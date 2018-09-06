package com.github.leecho.cloud.config.admin.config.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-6
 **/
@RestController("应用服务接口")
@RequestMapping(value = "/application", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationController {

	@Autowired
	private DiscoveryClient discoveryClient;


	@PostMapping(value = "/getInstances/{application}")
	@ApiOperation("获取应用实例列表")
	public ResponseEntity<List<ServiceInstance>> getApplicationInstances(@PathVariable(required = true) @ApiParam(value = "配置文件ID", required = true) String application) {
		return ResponseEntity.ok(this.discoveryClient.getInstances(application.toUpperCase()));
	}

}
