package kioskService;

import Domain.Order;
import Domain.OrderItem;
import Repository.ShoppingCartRepository;


import static Domain.OrderState.CHOOSE_ORDER;
import static Domain.OrderState.ORDERING;

public class ShoppingCartServiceImpl extends CalcMoneyAdapter implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void plusOrderItem(Long id) {
        OrderItem orderItem = shoppingCartRepository.getOrderItemById(id);
        shoppingCartRepository.addOrderItem(orderItem);
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
