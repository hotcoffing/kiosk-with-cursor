package kioskService;

import Domain.*;
import Repository.*;

import static Domain.OrderState.*;

// 장바구니 서비스 구현 클래스
// 장바구니 주문 항목 수량 조절 및 주문 상태 관리 기능을 구현
public class ShoppingCartServiceImpl extends CalcMoneyAdapter implements ShoppingCartService {

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        super(shoppingCartRepository);
    }

    @Override
    public void plusOrderItem(Long id) {
        OrderItem orderItem = shoppingCartRepository.getOrderItemById(id);
        if (orderItem != null) {
            // 수량을 1만 증가시킴
            orderItem.setQuantity(orderItem.getQuantity() + 1);
        }
    }

    @Override
    public void minusOrderItem(Long id) {
        OrderItem orderItem = shoppingCartRepository.getOrderItemById(id);
        shoppingCartRepository.removeOrderItem(orderItem);
    }

    @Override
    public void goChooseOrder(Order order) {
        order.setOrderState(CHOOSE_ORDER);
    }

    @Override
    public void goOrder(Order order) {
        order.setOrderState(ORDERING);
    }
}
