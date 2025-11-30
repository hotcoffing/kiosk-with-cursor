package kioskService;

import Domain.Order;
import Domain.OrderItem;
import Domain.OrderState;
import Domain.PaymentType;
import Repository.ShoppingCartRepository;

import static Domain.IdCounter.initOrderItemIdCounter;
import static Domain.IdCounter.orderIdCounter;

// 주문 서비스 구현 클래스
// 주문 완료 처리, 주문 항목 수량 조절, 주문 상태 관리 기능 구현 클래스
public class OrderServiceImpl extends CalcMoneyAdapter implements OrderService {

    // 주문 서비스 생성자
    public OrderServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        super(shoppingCartRepository);
    }

    // 주문 항목 수량 증가 메서드
    @Override
    public void plusOrderItem(Long id) {
        OrderItem orderItem = shoppingCartRepository.getOrderItemById(id);
        shoppingCartRepository.addOrderItem(orderItem);
    }

    // 주문 항목 수량 감소 메서드
    @Override
    public void minusOrderItem(Long id) {
        OrderItem orderItem = shoppingCartRepository.getOrderItemById(id);
        shoppingCartRepository.removeOrderItem(orderItem);
    }

    // 주문 완료 처리 메서드
    @Override
    public void completeOrder(Order order, PaymentType paymentType, String customerName) {
        if (order.getOrderState() != OrderState.ORDERING) {
            throw new IllegalStateException("Now is now Ordering");
        }
        
        // 최종 정보 저장
        order.setCustomerName(customerName);
        order.setPaymentType(paymentType);
        order.setOrderTime();
        order.setOrderItems(shoppingCartRepository.getAllOrderItems());
        order.setTotalPrice(getTotalPriceUseShoppingCart());
        order.setOrderState(OrderState.ORDERED);
        
        // 주문 상태 갱신
        for (OrderItem orderItem : shoppingCartRepository.getAllOrderItems()) {
            orderItem.setOrderState(OrderState.ORDERED);
        }
        
        // 장바구니 초기화
        initOrderItemIdCounter();
        shoppingCartRepository.removeAllOrderItems();
        
        // 주문 완료 후 ID 카운터 +1 증가 (다음 주문을 위해)
        long nowId = orderIdCounter.get();
        orderIdCounter.set(nowId + 1);
        
        // 새로운 Order 객체 생성 (다음 주문을 위해)
        // Order의 ID는 final이므로 새로 생성해야 함
        // 하지만 여기서는 Order 객체를 생성하지 않고, 
        // 다음에 Order가 생성될 때 증가된 ID를 사용하도록 함
    }

    // 주문 화면에서 뒤로가기 메서드
    @Override
    public void goBack(Order order) {
        order.setOrderState(OrderState.SHOPPING_CART);
    }
}
