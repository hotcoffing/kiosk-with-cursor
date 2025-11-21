package kioskService;

import Domain.Order;

public interface PrintReceiptService {
    public Order getOrderData(Long orderId);
}
