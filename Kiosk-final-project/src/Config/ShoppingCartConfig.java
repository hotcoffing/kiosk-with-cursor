package config;

import Repository.ShoppingCartRepositoryImpl;
import kioskService.*;

public class AppConfig {
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

    public CheckOrderListService checkOrderListService() {
        return new CheckOrderListServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    }

    public ShoppingCartService shoppingCartService() {
        return new ShoppingCartServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    }
}
