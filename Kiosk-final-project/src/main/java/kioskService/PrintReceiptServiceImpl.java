package kioskService;

import Domain.Order;
import Domain.OrderState;
import Repository.OrderInfoRespositoryImpl;

// 영수증 출력 서비스 구현 클래스
// 데이터베이스에서 주문 정보 조회 및 영수증 데이터 제공 기능 구현 클래스
public class PrintReceiptServiceImpl implements PrintReceiptService {
    private final OrderInfoRespositoryImpl orderInfoRespositoryImpl;

    // 영수증 출력 서비스 생성자
    public PrintReceiptServiceImpl(OrderInfoRespositoryImpl orderInfoRespositoryImpl) {
        this.orderInfoRespositoryImpl = orderInfoRespositoryImpl;
    }

    // 주문 ID로 주문 정보 조회 메서드
    public Order getOrderData(Long orderId) {
        Order order = orderInfoRespositoryImpl.getOrderById(orderId);

        if ((order != null) && (order.getOrderState() == OrderState.ORDERED)) {
            return order;
        }

        return null;
    }
}
