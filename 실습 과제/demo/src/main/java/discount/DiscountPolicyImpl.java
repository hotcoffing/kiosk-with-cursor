package discount;

import member.Grade;
import member.Member;

public class DiscountPolicyImpl implements DiscountPolicy {

    private int fixDiscountAmount = 1000;

    @Override
    public int discount (Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return fixDiscountAmount;
        }
        else {
            return 0;
        }
    }


}
