package kioskService;

import Domain.Order;

public interface PaymentService {
    Long saveOrder(Order order);
}
