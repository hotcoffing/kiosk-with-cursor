package kioskService;

import Domain.Order;
import Repository.OrderInfoRepository;

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
