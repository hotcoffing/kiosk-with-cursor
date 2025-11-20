package kioskService;

import Domain.*;

public interface SelectMenuService {
    public Category getMenuCategory(String categoryName);
    public ChickenName getChickenName(String chickenName);
    public SoupName getSoupName(String soupName);
    public SnacksName getSnacksName(String snacksName);
    public SidesName getSidesName(String sidesName);
    public DrinkName getDrinkName(String drinkName);
}
