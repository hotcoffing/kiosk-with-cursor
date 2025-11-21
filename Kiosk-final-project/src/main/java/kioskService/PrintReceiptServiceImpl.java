package kioskService;

import Domain.Order;
import Domain.OrderState;
import Repository.OrderInfoRespositoryImpl;

public class PrintReceiptServiceImpl implements PrintReceiptService {
    private final OrderInfoRespositoryImpl orderInfoRespositoryImpl;

    public PrintReceiptServiceImpl(OrderInfoRespositoryImpl orderInfoRespositoryImpl) {
        this.orderInfoRespositoryImpl = orderInfoRespositoryImpl;
    }

    public Order getOrderData(Long orderId) {
        Order order = orderInfoRespositoryImpl.getOrderById(orderId);

        if ((order != null) && (order.getOrderState() == OrderState.ORDERED)) {
            return order;
        }

        return null;
    }
}
