package Domain;

import java.io.Serializable;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.*;

import static Domain.OrderState.*;

public class Order extends IdCounter implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private int tableNumber;

    private OrderType orderType;
    private OrderState orderState;
    private PaymentType paymentType;
    private String customerName;
    private LocalDateTime orderTime;
    private List<OrderItem> orderItems;
    private int totalPrice;

    public Order() {
        this(orderIdCounter.getAndIncrement());
    }

    public Order(Long id) {
        this.id = id;
        initializeDefaults();
    }

    private void initializeDefaults() {
        this.tableNumber = 0;
        this.orderType = null;
        this.orderState = CANCELED;
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

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
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