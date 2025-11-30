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

// 장바구니 관련 설정 클래스
// 장바구니 Repository와 관련 서비스 싱글톤 패턴 생성 및 관리 클래스
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

    // 금액 계산 어댑터 반환 메서드
    public CalcMoneyInterface calcMoneyAdapter() {
        return new CalcMoneyAdapter(
                shoppingCartRepository()
        );
    }

    // 메뉴 선택 서비스 반환 메서드
    public SelectMenuService selectMenuService() {
        return new SelectMenuServiceImpl(
                shoppingCartRepository()
        );
    }

    // 옵션 선택 서비스 반환 메서드
    public SelectOptionService selectOptionService() {
        return new SelectOptionServiceImpl(
                shoppingCartRepository()
        );
    }

    // 장바구니 서비스 반환 메서드
    public ShoppingCartService shoppingCartService() {
        return new ShoppingCartServiceImpl(
                shoppingCartRepository()
        );
    }

    // 주문 서비스 반환 메서드
    public OrderService orderService() {
        return new OrderServiceImpl(
                shoppingCartRepository()
        );
    }
}
