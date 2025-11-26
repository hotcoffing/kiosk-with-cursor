package kioskService;

import Domain.*;

public interface OrderService {
    public void plusOrderItem(Long id);
    public void minusOrderItem(Long id);
    public void completeOrder(Order order, PaymentType paymentType, String customerName);
    public void goBack(Order order);
}
