package kioskService;

import Domain.Order;
import Domain.OrderItem;
import Repository.OrderInfoRespositoryImpl;
import Repository.ShoppingCartRepository;

import java.util.List;

public class CheckOrderListServiceImpl implements CheckOrderListService {
    private final OrderInfoRespositoryImpl orderInfoRespositoryImpl;

    public CheckOrderListServiceImpl(OrderInfoRespositoryImpl orderInfoRespositoryImpl) {
        this.orderInfoRespositoryImpl = orderInfoRespositoryImpl;
    }

    @Override
    public List<OrderItem> getOrderItemsByUserInfo(int tableNumber, String name) {
        Order order = orderInfoRespositoryImpl.getOrderByUserInfo(tableNumber, name);

        if (order != null) {
            return order.getOrderItems();
        }

        return null;
    }

    @Override
    public int gettotalPriceByUserInfo(int tableNumber, String name) {
        Order order = orderInfoRespositoryImpl.getOrderByUserInfo(tableNumber, name);

        if (order != null) {
            return order.getTotalPrice();
        }

        return -1;
    }
}
