package kioskService;

import Domain.*;
import Repository.*;

import static Domain.OrderState.*;

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
