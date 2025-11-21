package Config;

import Repository.ShoppingCartRepositoryImpl;
import kioskService.*;

public class ShoppingCartConfig {

    public CalcMoneyInterface calcMoneyAdapter() {
        return new CalcMoneyAdapter(
                new ShoppingCartRepositoryImpl()
        );
    }

    public ShoppingCartService shoppingCartService() {
        return new ShoppingCartServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    }

    public OrderService orderService() {
        return new OrderServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    }
}
