package Domain;

import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import Repository.OrderInfoRespositoryImpl;

// 주문 및 주문 항목 ID 자동 증가 관리 클래스
public class IdCounter {
    protected static final AtomicLong orderItemIdCounter = new AtomicLong(1000L);
    public static final AtomicLong orderIdCounter = new AtomicLong(2000L);

    // 주문 항목 ID 카운터 초기화 메서드
    public static void initOrderItemIdCounter() {
        orderItemIdCounter.set(1000L);
    }

    // 주문 ID 카운터 초기화 메서드
    public static void initOrderIdCounter(long nextId) {
        orderIdCounter.set(nextId);
    }

    // 데이터베이스에서 주문 ID 동기화 메서드
    public static void syncOrderIdFromDatabase(DataSource dataSource) {
        try {
            OrderInfoRespositoryImpl repository = new OrderInfoRespositoryImpl(dataSource);
            System.out.println("Order ID counter DB 동기화");
        } catch (Exception e) {
            System.err.println("Order ID counter DB 동기화 실패" + e.getMessage());
        }
    }
}