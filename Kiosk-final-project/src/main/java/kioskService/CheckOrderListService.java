package kioskService;

import Domain.Order;
import Domain.OrderItem;

import java.util.List;

public interface CheckOrderListService {
    public List<OrderItem> getOrderItemsByUserInfo(int tableNumber, String name);
    public int gettotalPriceByUserInfo(int tableNumber, String name);
}
