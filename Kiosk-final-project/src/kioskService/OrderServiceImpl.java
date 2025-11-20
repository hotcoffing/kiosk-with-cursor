package kioskService;


import Domain.OrderItem;
import Repository.ShoppingCartRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final ShoppingCartRepository shoppingCartRepository;

    public OrderServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    // 장바구니 및 주문 창에서 최종 금액 확인 가능
    @Override
    public int getTotalPrice() {
        List<OrderItem> orderItems = shoppingCartRepository.findAllOrderItems();
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPriceWithOption();
        }

        return totalPrice;
    }
}
