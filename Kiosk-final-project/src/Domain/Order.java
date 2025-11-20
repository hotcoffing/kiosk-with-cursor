package Domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private String customerName;
    private int tableNumber;

    private OrderType orderType;
    private List<OrderItem> orderItems = new ArrayList<>();
    private LocalDateTime orderTime;

    private PaymentType paymentType;
    private OrderState orderState;

    public Order(int tableNumber, OrderType orderType, String customerName) {
        this.tableNumber = tableNumber;
        this.orderType = orderType;
        this.customerName = customerName;
        
        // 기본 초기 상태 설정
        this.orderState = OrderState.SHOPPING_CART;
        this.orderTime = null;
    }

    // 주문 목록 추가
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    // 버튼 클릭시 실행 => Service 이동 가능 (주문 시 필드 변경 사항)
    public void completeOrder(PaymentType paymentType) {
        if (this.orderState == OrderState.ORDERED) {
            throw new IllegalStateException("Order is already completed");
        }

        this.paymentType = paymentType;
        this.orderTime = LocalDateTime.now();

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderState(OrderState.ORDERED);
        }
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime() {
        this.orderTime = LocalDateTime.now();
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}