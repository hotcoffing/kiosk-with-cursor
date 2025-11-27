package kioskService;

import Domain.Order;
import Domain.OrderItem;
import Repository.ShoppingCartRepository;

import static Domain.OrderState.CHOOSE_ORDER;
import static Domain.OrderState.ORDERING;

// 장바구니 서비스 구현 클래스
// 장바구니 주문 항목 수량 조절 및 주문 상태 관리 기능을 구현
public class ShoppingCartServiceImpl extends CalcMoneyAdapter implements ShoppingCartService {

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        super(shoppingCartRepository);
    }

    // 장바구니 주문 항목 수량 증가
    @Override
    public void plusOrderItem(Long id) {
        OrderItem orderItem = shoppingCartRepository.getOrderItemById(id);
        if (orderItem != null) {
            // 수량을 1만 증가시킴
            orderItem.setQuantity(orderItem.getQuantity() + 1);
        }
    }

    // 장바구니 주문 항목 수량 감소
    @Override
    public void minusOrderItem(Long id) {
        OrderItem orderItem = shoppingCartRepository.getOrderItemById(id);
        shoppingCartRepository.removeOrderItem(orderItem);
    }

    // 메뉴 선택 화면으로 이동 (주문 상태 변경)
    @Override
    public void goChooseOrder(Order order) {
        order.setOrderState(CHOOSE_ORDER);
    }

    // 주문 화면으로 이동 (주문 상태 변경)
    @Override
    public void goOrder(Order order) {
        order.setOrderState(ORDERING);
    }
}
