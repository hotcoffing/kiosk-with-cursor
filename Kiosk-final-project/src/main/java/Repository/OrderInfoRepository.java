package Repository;

import Domain.*;
import java.util.*;

public interface OrderInfoRepository {
    void addOrder(Order orderItem);
    void removeOrder(Order orderItem);
    void removeAllOrders();

    Order getOrderById(Long id);
    Order getOrderByUserInfo(int tableNumber, String name);
    List<Order> getAllOrder();
}
