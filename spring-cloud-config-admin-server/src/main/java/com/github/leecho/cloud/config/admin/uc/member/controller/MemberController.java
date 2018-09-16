package com.github.leecho.cloud.config.admin.uc.member.controller;

import com.github.leecho.cloud.config.admin.uc.member.entity.Member;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LIQIU
 * @date 2018-9-10
 **/
@Api(description = "成员接口")
@RestController
public class MemberController {

	@PostMapping
	public ResponseEntity<Member> add(Member member) {
		return ResponseEntity.ok(null);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id) {
		return ResponseEntity.ok(null);
	}
}
