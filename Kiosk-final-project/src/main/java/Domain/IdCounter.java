package Domain;

import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import Repository.OrderInfoRespositoryImpl;

// ID 카운터 관리 클래스
// 주문 ID와 주문 항목 ID를 원자적으로 관리하고 데이터베이스와 동기화
public class IdCounter {
    protected static final AtomicLong orderItemIdCounter = new AtomicLong(1000L);
    public static final AtomicLong orderIdCounter = new AtomicLong(2000L);

    // 장바구니를 비우면 새로운 idCounter 설정
    public static void initOrderItemIdCounter() {
        orderItemIdCounter.set(1000L);
    }

    public static void initOrderIdCounter(long nextValue) {
        orderIdCounter.set(nextValue);
    }

    // 앱 실행 시 orders 테이블의 최대 ID를 가져와서 ID 동기화
    public static void syncOrderIdFromDatabase(DataSource dataSource) {
        try {
            OrderInfoRespositoryImpl repository = new OrderInfoRespositoryImpl(dataSource);
            // repository 생성 시 syncOrderIdCounter()가 자동으로 호출됨
            System.out.println("Order ID counter DB 동기화");
        } catch (Exception e) {
            // 테이블이 없거나 오류 발생 시 기본값 유지
            System.err.println("Order ID counter DB 동기화 실패" + e.getMessage());
        }
    }
}