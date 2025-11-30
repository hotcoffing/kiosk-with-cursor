package kioskService;

import Domain.Order;
import Repository.OrderInfoRepository;

// 결제 서비스 구현 클래스
// 주문 정보 데이터베이스 저장 및 저장된 주문 ID 반환 기능 구현 클래스
public class PaymentServiceImpl implements PaymentService {

    private final OrderInfoRepository orderInfoRepository;

    // 결제 서비스 생성자
    public PaymentServiceImpl(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    // 주문 정보 데이터베이스 저장 및 주문 ID 반환 메서드
    @Override
    public Long saveOrder(Order order) {
        if (orderInfoRepository == null || order == null) {
            return null;
        }
        orderInfoRepository.addOrder(order);
        
        // 저장된 주문 ID 반환 (addOrder에서 업데이트된 ID)
        // DB에서 최대 ID를 조회하여 반환
        return orderInfoRepository.getMaxOrderId();
    }
}
