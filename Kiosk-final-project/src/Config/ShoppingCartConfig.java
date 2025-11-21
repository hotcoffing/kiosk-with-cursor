package Config;

import Repository.ShoppingCartRepositoryImpl;
import kioskService.*;

public class ShoppingCartConfig {
    public SelectMenuService selectMenuService() {
        return new SelectMenuServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    };

    public OrderService orderService() {
        return new OrderServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    }

    public PaymentService paymentService() {
        return new PaymentServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    }

    public ShoppingCartService shoppingCartService() {
        return new ShoppingCartServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    }

    public SelectOptionService selectOptionService() {
        return new SelectOptionServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    }
}
