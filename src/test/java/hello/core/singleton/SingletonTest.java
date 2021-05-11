package hello.core.singleton;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberService;

public class SingletonTest {

	@Test
	@DisplayName("스프링 없는 순수한 DI 컨테이너")
	void puerContainer() {
		AppConfig appConfig = new AppConfig();

		//1. 조회 : 호출할때마다 객체 생성
		MemberService memberService1 = appConfig.memberService();

		//2. 조회 : 호출할때마다 객체 생성
		MemberService memberService2 = appConfig.memberService();

		// 참조값이 다름을 확인
		assertThat(memberService1).isNotSameAs(memberService2);
	}

	@Test
	@DisplayName("싱글톤 패턴을 적용한 객체 사용")
	void singletonServiceTest() {
		SingletonService singletonService1 = SingletonService.getInstance();
		SingletonService singletonService2 = SingletonService.getInstance();

		assertThat(singletonService1).isEqualTo(singletonService2);
	}

	@Test
	@DisplayName("스프링 컨테이너와 싱글톤")
	void springContainer() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
			AppConfig.class);

		MemberService memberService1 = ac.getBean("memberService", MemberService.class);
		MemberService memberService2 = ac.getBean("memberService", MemberService.class);

		// 참조값이 동일함 확인
		assertThat(memberService1).isSameAs(memberService2);
	}
}
