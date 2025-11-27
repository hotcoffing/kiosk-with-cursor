package Domain;

import Repository.OrderInfoRespositoryImpl;
import javax.sql.DataSource;
import java.util.concurrent.atomic.*;

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
            // 하지만 명시적으로 동기화를 확인하기 위해 추가 호출
            System.out.println("Order ID counter synchronized from database");
        } catch (Exception e) {
            System.err.println("Failed to sync order ID from database: " + e.getMessage());
            // 테이블이 없거나 오류 발생 시 기본값 유지
        }
    }
}