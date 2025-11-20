package config;

import member.MemberService;
import member.MemberServiceImpl;
import member.MemoryMemberRepository;
import discount.DiscountPolicyImpl;
import order.OrderService;
import order.OrderServiceImpl;

public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(
                new MemoryMemberRepository()
        );
    }

    public OrderService orderService() {
        return new OrderServiceImpl(
                new MemoryMemberRepository(),
                new DiscountPolicyImpl()
        );
    }
}
