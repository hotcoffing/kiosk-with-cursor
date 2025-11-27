package kioskService;

import Domain.*;

// 주문 서비스 인터페이스
// 주문 항목 수량 조절, 주문 완료 처리, 주문 상태 변경 기능을 정의
public interface OrderService {
    public void plusOrderItem(Long id);
    public void minusOrderItem(Long id);
    public void completeOrder(Order order, PaymentType paymentType, String customerName);
    public void goBack(Order order);
}
