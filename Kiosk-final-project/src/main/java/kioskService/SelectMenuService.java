package kioskService;

import Domain.*;

// 메뉴 선택 서비스 인터페이스
// 메뉴 카테고리 및 메뉴 이름 조회, 주문 상태 변경 기능을 정의
public interface SelectMenuService {
    public Category getMenuCategory(String categoryName);
    public ChickenName getChickenName(String chickenName);
    public SoupName getSoupName(String soupName);
    public SnacksName getSnacksName(String snacksName);
    public SidesName getSidesName(String sidesName);
    public DrinkName getDrinkName(String drinkName);

    public void goMain(Order order);
    public void goShoppingCart(Order order);
    public void goOrder(Order order);
}
