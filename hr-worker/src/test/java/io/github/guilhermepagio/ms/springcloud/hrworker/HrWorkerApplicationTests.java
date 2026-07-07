package io.github.guilhermepagio.ms.springcloud.hrworker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"test.config=mock-config-value-for-tests",
		"spring.cloud.config.enabled=false",
		"eureka.client.enabled=false"
})
class HrWorkerApplicationTests {

	@Test
	void contextLoads() {
	}

}
