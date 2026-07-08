package io.github.guilhermepagio.ms.springcloud.hrapigatewayzuul;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"jwt.secret=mock-secret-for-tests",
		"spring.cloud.config.enabled=false",
		"eureka.client.enabled=false"
})
class HrApiGatewayZuulApplicationTests {

	@Test
	void contextLoads() {
	}

}
