package kioskService;

import Domain.*;
import java.util.*;

public interface CheckOrderListService {
    public List<OrderItem> getOrderItemsByUserInfo(int tableNumber, String name);
    public int gettotalPriceByUserInfo(int tableNumber, String name);
}
