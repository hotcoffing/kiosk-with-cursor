package Config;

import Repository.ShoppingCartRepository;
import Repository.ShoppingCartRepositoryImpl;
import kioskService.CalcMoneyAdapter;
import kioskService.CalcMoneyInterface;
import kioskService.OrderService;
import kioskService.OrderServiceImpl;
import kioskService.SelectMenuService;
import kioskService.SelectMenuServiceImpl;
import kioskService.SelectOptionService;
import kioskService.SelectOptionServiceImpl;
import kioskService.ShoppingCartService;
import kioskService.ShoppingCartServiceImpl;

public class ShoppingCartConfig {

    // 싱글톤 패턴: Repository 인스턴스를 하나만 생성하여 모든 서비스가 공유
    private ShoppingCartRepository shoppingCartRepository;

    // Repository 인스턴스 생성 (싱글톤)
    public ShoppingCartRepository shoppingCartRepository() {
        if (shoppingCartRepository == null) {
            shoppingCartRepository = new ShoppingCartRepositoryImpl();
        }
        return shoppingCartRepository;
    }

    public CalcMoneyInterface calcMoneyAdapter() {
        return new CalcMoneyAdapter(
                shoppingCartRepository()
        );
    }

    public SelectMenuService selectMenuService() {
        return new SelectMenuServiceImpl(
                shoppingCartRepository()
        );
    }

    public SelectOptionService selectOptionService() {
        return new SelectOptionServiceImpl(
                shoppingCartRepository()
        );
    }

    public ShoppingCartService shoppingCartService() {
        return new ShoppingCartServiceImpl(
                shoppingCartRepository()
        );
    }

    public OrderService orderService() {
        return new OrderServiceImpl(
                shoppingCartRepository()
        );
    }
}
