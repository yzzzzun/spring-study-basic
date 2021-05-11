package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

	@Test
	@DisplayName("")
	void statefulServiceSingletonTest() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
			TestConfig.class);

		StatefulService statefulService1 = ac.getBean(StatefulService.class);
		StatefulService statefulService2 = ac.getBean(StatefulService.class);
		int userAPrice = statefulService1.order("userA", 10000);
		int userBPrice = statefulService2.order("userB", 20000);

		//ThreadA: 사용자 A 주문금액을 조회
		Assertions.assertThat(userAPrice).isEqualTo(10000);
	}

	static class TestConfig {
		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
	}
}
