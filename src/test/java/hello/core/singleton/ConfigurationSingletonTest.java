package hello.core.singleton;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;

public class ConfigurationSingletonTest {
	@Test
	void configurationTest() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

		MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
		OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
		MemoryMemberRepository memberRepository = ac.getBean("memberRepository", MemoryMemberRepository.class);

		MemberRepository memberRepository1 = memberService.getMemberRepository();
		MemberRepository memberRepository2 = orderService.getMemberRepository();

		assertThat(memberRepository1).isSameAs(memberRepository2);
		assertThat(memberRepository).isSameAs(memberRepository2);
		assertThat(memberRepository).isSameAs(memberRepository1);
	}

	@Test
	void configurationDeep() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

		AppConfig bean = ac.getBean(AppConfig.class);
		System.out.println("bean = " + bean.getClass());

	}
}
