package kioskService;

import Domain.*;
import Repository.*;

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
