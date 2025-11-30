package kioskService;

import Domain.Order;

// 결제 서비스 인터페이스
public interface PaymentService {
    Long saveOrder(Order order);
}
