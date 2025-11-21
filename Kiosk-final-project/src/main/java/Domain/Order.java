package Domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static Domain.OrderState.SHOPPING_CART;

public class Order extends IdCounter {
    private final Long id;
    private int tableNumber;

    private OrderType orderType;
    private OrderState orderState;
    private PaymentType paymentType;
    private String customerName;
    private LocalDateTime orderTime;
    private List<OrderItem> orderItems;
    private int totalPrice;

    public Order(int tableNumber, OrderType orderType) {
        this.id = orderIdCounter.getAndIncrement();
        this.tableNumber = tableNumber;
        this.orderType = orderType;

        // 기본 초기 상태 설정
        this.orderState = OrderState.CANCELED;
        this.orderTime = null;
        this.orderItems = null;
        this.totalPrice = 0;
    }

    // Getter
    public Long getId() {
        return id;
    }

    // Getter and Setter
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

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}