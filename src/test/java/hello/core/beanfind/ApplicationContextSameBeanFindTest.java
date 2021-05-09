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

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class ApplicationContextSameBeanFindTest {
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

	@Test
	@DisplayName("타입으로 조회시 같은 타입이 둘이상이면 중복오류 발생")
	void findBeanByTypeDuplicate() {
		assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
	}

	@Test
	@DisplayName("타입으로 조회시 같은 타입이 둘이상이면, 빈 이름을 지정해서 찾아야함")
	void findBeanByName() {
		MemberRepository memberRepository1 = ac.getBean("memberRepository1", MemberRepository.class);
		assertThat(memberRepository1).isInstanceOf(MemberRepository.class);
	}

	@Test
	@DisplayName("특정 타입을 모두 조회")
	void findAllBeanByType() {
		Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);

		for (String key : beansOfType.keySet()) {
			System.out.println("key = " + key + " value = " + beansOfType.get(key));
		}
		System.out.println("beansOfType = " + beansOfType);
		assertThat(beansOfType.size()).isEqualTo(2);
	}

	@Configuration
	static class SameBeanConfig {

		@Bean
		public MemberRepository memberRepository1() {
			return new MemoryMemberRepository();
		}

		@Bean
		public MemberRepository memberRepository2() {
			return new MemoryMemberRepository();
		}
	}

}
