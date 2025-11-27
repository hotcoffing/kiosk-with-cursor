package kioskService;

import Domain.Order;

// 장바구니 서비스 인터페이스
// 장바구니 주문 항목 수량 조절 및 주문 상태 변경 기능을 정의
public interface ShoppingCartService {
    public void plusOrderItem(Long id);
    public void minusOrderItem(Long id);

    public void goChooseOrder(Order order);
    public void goOrder(Order order);
}
