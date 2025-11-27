package kioskService;

import Domain.Order;

// 결제 서비스 인터페이스
// 주문 정보를 데이터베이스에 저장하는 기능을 정의
public interface PaymentService {
    Long saveOrder(Order order);
}
