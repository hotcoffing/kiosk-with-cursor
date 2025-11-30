package Domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static Domain.OrderState.CANCELED;

// 주문 정보 도메인 클래스
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

    // 기본 생성자
    public Order() {
        this(orderIdCounter.getAndIncrement());
    }

    // ID 지정 생성자
    public Order(Long id) {
        this.id = id;
        initializeDefaults();
    }

    // 주문 기본값 초기화 메서드
    private void initializeDefaults() {
        this.tableNumber = 0;
        this.orderType = OrderType.DINE_IN; // 기본값 매장
        this.orderState = CANCELED;
        this.orderTime = null;
        this.orderItems = null;
        this.totalPrice = 0;
    }

    // 주문 ID 반환 메서드
    public Long getId() {
        return id;
    }

    // 주문자 이름 반환 메서드
    public String getCustomerName() {
        return customerName;
    }

    // 주문자 이름 설정 메서드
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // 테이블 번호 반환 메서드
    public int getTableNumber() {
        return tableNumber;
    }

    // 테이블 번호 설정 메서드
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    // 주문 유형 반환 메서드
    public OrderType getOrderType() {
        return orderType;
    }

    // 주문 유형 설정 메서드
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    // 결제 유형 반환 메서드
    public PaymentType getPaymentType() {
        return paymentType;
    }

    // 결제 유형 설정 메서드
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    // 주문 시간 반환 메서드
    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    // 현재 시간으로 주문 시간 설정 메서드
    public void setOrderTime() {
        this.orderTime = LocalDateTime.now();
    }

    // 주문 시간 설정 메서드
    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    // 주문 상태 반환 메서드
    public OrderState getOrderState() {
        return orderState;
    }

    // 주문 상태 설정 메서드
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    // 주문 항목 목록 반환 메서드
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    // 주문 항목 목록 설정 메서드
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // 총 가격 반환 메서드
    public int getTotalPrice() {
        return totalPrice;
    }

    // 총 가격 설정 메서드
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}