package com.github.leecho.cloud.config.admin.config.service.impl;

import com.github.leecho.cloud.config.admin.ServiceTestConfiguration;
import com.github.leecho.cloud.config.admin.config.entity.Change;
import com.github.leecho.cloud.config.admin.config.entity.Config;
import com.github.leecho.cloud.config.admin.config.enums.ChangeType;
import com.github.leecho.cloud.config.admin.config.model.PublishOperation;
import com.github.leecho.cloud.config.admin.config.repository.ConfigRepository;
import com.github.leecho.cloud.config.admin.config.service.ConfigService;
import com.github.leecho.cloud.config.admin.pm.profile.entity.Profile;
import com.github.leecho.cloud.config.admin.pm.profile.service.ProfileService;
import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import com.github.leecho.cloud.config.admin.pm.project.service.ProjectService;
import com.github.leecho.cloud.config.admin.utils.YamlUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-9-4
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class ConfigServiceImplTest {

	@Autowired
	private ConfigService configService;

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProfileService profileService;

	private Config config;

	@Before
	public void setUp() {

		TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken("user", "credentials");
		SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

		this.config = configRepository.findById(1).orElseGet(() -> {

			Project project = new Project();
			project.setName("测试项目");
			project.setCode("test-service");
			project.setOwner(0);
			project.setDescription("test project");
			project = projectService.save(project);

			Profile profile = new Profile();
			profile.setCode("dev");
			profile.setName("测试环境");
			profile.setEnable(true);
			profile.setProject(project);
			profile = profileService.save(profile);

			Config config = new Config();
			config.setProject(project);
			config.setProfile(profile);
			config.setLabel("master");
			config.setDescription("test configuration");
			config = this.configService.save(config);

			this.init(config);

			return config;
		});
	}

	@Test
	public void commit() {

		Change addChange = new Change();
		addChange.setConfig(this.config);
		addChange.setType(ChangeType.CREATE.getValue());
		addChange.setCurrentValue("4671");
		addChange.setProperty("spring.redis.port1");
		addChange.setMessage("新增端口");
		this.configService.commit(addChange);

		Change updateChange = new Change();
		updateChange.setConfig(this.config);
		updateChange.setType(ChangeType.UPDATE.getValue());
		updateChange.setCurrentValue("4672");
		updateChange.setProperty("spring.redis.port");
		updateChange.setMessage("修改端口");
		this.configService.commit(updateChange);

		Change deleteChange = new Change();
		deleteChange.setConfig(this.config);
		deleteChange.setType(ChangeType.DELETE.getValue());
		deleteChange.setProperty("spring.redis.timeout");
		deleteChange.setMessage("刪除超时时间");
		this.configService.commit(deleteChange);
	}

	@Test
	public void discard() {
		this.commit();
		this.configService.discard(this.config.getId());
	}

	@Test
	public void publish() {
		this.commit();
		PublishOperation publishOperation = new PublishOperation();
		publishOperation.setConfigId(config.getId());
		publishOperation.setMessage("版本发布");
		this.configService.publish(publishOperation);
		//this.configService.publish(config.getId());
	}

	public void init(Config config) {
		InputStream inputStream = ConfigServiceImplTest.class.getClassLoader().getResourceAsStream("configuration-example.yml");
		Map<String, Object> items = YamlUtils.convertYamlToMap(inputStream);
		configService.init(config.getId(), items);
	}
}