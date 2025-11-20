package discount;

import com.example.demo.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);

}
