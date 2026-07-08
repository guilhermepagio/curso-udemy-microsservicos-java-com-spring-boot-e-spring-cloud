package io.github.guilhermepagio.ms.springcloud.hroauth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"jwt.secret=mock-secret-for-tests",
		"oauth.client.name=mock-client-for-tests",
		"oauth.client.secret=mock-secret-for-tests",
		"spring.cloud.config.enabled=false",
		"eureka.client.enabled=false"
})
class HrOauthApplicationTests {

	@Test
	void contextLoads() {
	}

}
