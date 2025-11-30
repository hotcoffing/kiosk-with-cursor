package kioskService;

import Domain.Category;
import Domain.ChickenName;
import Domain.DrinkName;
import Domain.Order;
import Domain.SidesName;
import Domain.SnacksName;
import Domain.SoupName;

// 메뉴 선택 서비스 인터페이스
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
