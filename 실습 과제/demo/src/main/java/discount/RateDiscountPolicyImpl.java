package discount;

import member.Grade;
import member.Member;

public class RateDiscountPolicyImpl implements DiscountPolicy {

    public int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        }
        else {
            return 0;
        }

    }
}
