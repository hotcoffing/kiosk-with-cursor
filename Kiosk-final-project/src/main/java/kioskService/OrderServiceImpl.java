package kioskService;

import Domain.*;
import Repository.*;

import static Domain.IdCounter.*;

public class OrderServiceImpl extends CalcMoneyAdapter implements OrderService {

    public OrderServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        super(shoppingCartRepository);
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

    // 최종 주문 (최종 정보 저장, 상태 갱신, 장바구니 초기화)
    @Override
    public void completeOrder(Order order, PaymentType paymentType, String customerName) {
        if (order.getOrderState() != OrderState.ORDERING) {
            throw new IllegalStateException("Now is now Ordering");
        }
        
        // 죄종 정보 저장
        order.setCustomerName(customerName);
        order.setPaymentType(paymentType);
        order.setOrderTime();
        order.setOrderItems(shoppingCartRepository.getAllOrderItems());
        order.setTotalPrice(getTotalPriceUseShoppingCart());
        
        // 주문 상태 갱신
        for (OrderItem orderItem : shoppingCartRepository.getAllOrderItems()) {
            orderItem.setOrderState(OrderState.ORDERED);
        }
        
        // 장바구니 초기화
        initOrderItemIdCounter();
        shoppingCartRepository.removeAllOrderItems();
    }

    @Override
    public void goBack(Order order) {
        order.setOrderState(OrderState.SHOPPING_CART);
    }
}
