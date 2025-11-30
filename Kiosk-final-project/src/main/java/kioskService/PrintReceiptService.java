package kioskService;

import Domain.Order;

// 영수증 출력 서비스 인터페이스
public interface PrintReceiptService {
    public Order getOrderData(Long orderId);
}
