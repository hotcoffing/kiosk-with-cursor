package kioskService;

import Domain.*;
import Repository.*;

import java.util.*;

public class CalcMoneyAdapter implements CalcMoneyInterface {

    protected final ShoppingCartRepository shoppingCartRepository;

    public CalcMoneyAdapter(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }
    
    // ShoppingCart 총 금액 갱신
    @Override
    public int getTotalPriceUseShoppingCart() {
        List<OrderItem> orderItems = shoppingCartRepository.getAllOrderItems();
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getQuantity() != 0) {
                totalPrice += orderItem.getPriceWithOptions() * orderItem.getQuantity();
            }
        }

        return totalPrice;
    }

    @Override
    public int getLocalPriceInOption(MenuItem item, List<Option> options) {
        // 항상 0을 반환하지만 인터페이스 구현을 위해 필요
        return 0;
    }
}
