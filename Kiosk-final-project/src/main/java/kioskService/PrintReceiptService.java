package kioskService;

import Domain.*;

// 영수증 출력 서비스 인터페이스
// 주문 ID로 주문 정보를 조회하여 영수증에 표시할 데이터를 제공하는 기능을 정의
public interface PrintReceiptService {
    public Order getOrderData(Long orderId);
}
