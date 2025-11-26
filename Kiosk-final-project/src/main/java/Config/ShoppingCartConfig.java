package Config;

import Repository.ShoppingCartRepositoryImpl;
import kioskService.*;

public class ShoppingCartConfig {

    public CalcMoneyInterface calcMoneyAdapter() {
        return new CalcMoneyAdapter(
                new ShoppingCartRepositoryImpl()
        );
    }

    public SelectMenuService selectMenuService() {
        return new SelectMenuServiceImpl(
                new ShoppingCartRepositoryImpl()
        );
    }

    public SelectOptionService selectOptionService() {
        return new SelectOptionServiceImpl(
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
