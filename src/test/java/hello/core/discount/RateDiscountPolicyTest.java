package hello.core.discount;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hello.core.member.Grade;
import hello.core.member.Member;

class RateDiscountPolicyTest {

	RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

	@Test
	@DisplayName("vip는 10%할인이 적용되어야함.")
	void vip_o() {
		//given
		Member member = new Member(1L, "memberA", Grade.VIP);

		//when
		int discount = discountPolicy.discount(member, 10000);

		//then
		assertThat(discount).isEqualTo(1000);
	}

	@Test
	@DisplayName("vip가 아니면 할인이 적용되지 않음.")
	void vip_x() {
		//given
		Member member = new Member(1L, "memberA", Grade.BASIC);

		//when
		int discount = discountPolicy.discount(member, 10000);

		//then
		assertThat(discount).isEqualTo(0);
	}
}
