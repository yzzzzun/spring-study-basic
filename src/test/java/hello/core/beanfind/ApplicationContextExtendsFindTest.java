package hello.core.beanfind;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;

public class ApplicationContextExtendsFindTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

	@Test
	@DisplayName("부모 타입으로 조회시 자식이 둘이상이 있으면 중복오류발생")
	void findBeanByParentTypeDuplicate() {
		assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
	}

	@Test
	@DisplayName("자식이 둘이상이 있으면 이름을 지정")
	void findBeanByParentTypeBeanName() {
		DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
		assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
	}

	@Test
	@DisplayName("특정 하위타입으로 조회")
	void findBeanByParentTypeBeanSubType() {
		DiscountPolicy rateDiscountPolicy = ac.getBean(RateDiscountPolicy.class);
		assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
	}

	@Test
	@DisplayName("부모타입으로 모두조회")
	void findBeanByParentType() {
		Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
		for (String key : beansOfType.keySet()) {
			System.out.println("key = " + key + " value = " + beansOfType.get(key));
		}
	}

	@Test
	@DisplayName("부모타입으로 모두조회 - Object")
	void findAllBeanByObjectType() {
		Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
		for (String key : beansOfType.keySet()) {
			System.out.println("key = " + key + " value = " + beansOfType.get(key));
		}
	}

	@Configuration
	static class TestConfig {

		@Bean
		public DiscountPolicy rateDiscountPolicy() {
			return new RateDiscountPolicy();
		}

		@Bean
		public DiscountPolicy fixDiscountPolicy() {
			return new FixDiscountPolicy();
		}
	}
}
