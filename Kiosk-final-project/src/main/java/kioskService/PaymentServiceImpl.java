package kioskService;

import Domain.Order;
import Repository.OrderInfoRepository;

// 결제 서비스 구현 클래스
// 주문 정보를 데이터베이스에 저장하고 저장된 주문 ID를 반환하는 기능을 구현
public class PaymentServiceImpl implements PaymentService {

    private final OrderInfoRepository orderInfoRepository;

    public PaymentServiceImpl(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

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
