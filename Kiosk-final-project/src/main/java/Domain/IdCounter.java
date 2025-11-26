package Domain;

import java.util.concurrent.atomic.*;

public class IdCounter {
    protected static final AtomicLong orderItemIdCounter = new AtomicLong(1000L);
    protected static final AtomicLong orderIdCounter = new AtomicLong(2000L);

    // 장바구니를 비우면 새로운 idCounter 설정
    public static void initOrderItemIdCounter() {
        orderItemIdCounter.set(1000L);
    }
}