package top.galaxyrockets.cslabmanagementplatform;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.annotation.Resource;

import top.galaxyrockets.cslabmanagementplatform.service.ILabScheduleRequestService;

@SpringBootTest
class CsLabManagementPlatformApplicationTests {
	
	@Resource
	private ILabScheduleRequestService labScheduleRequestService;

	@Test
	void contextLoads() {
		var result = labScheduleRequestService.page(1, 10, null);
		System.out.println(result);
	}

}
