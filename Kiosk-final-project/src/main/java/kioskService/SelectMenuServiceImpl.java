package kioskService;

import Domain.*;
import Repository.ShoppingCartRepository;

import static Domain.Category.*;
import static Domain.ChickenName.*;
import static Domain.DrinkName.*;
import static Domain.SidesName.*;
import static Domain.SnacksName.*;
import static Domain.SoupName.*;

public class SelectMenuServiceImpl extends CalcMoneyAdapter implements SelectMenuService {
    
    public SelectMenuServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        super(shoppingCartRepository);
    }

    @Override
    public Category getMenuCategory(String categoryName) {
        if (categoryName.equals("치킨")) {
            return CHICKEN;
        }
        else if (categoryName.equals("국물류")) {
            return SOUP;
        }
        else if (categoryName.equals("안주류")) {
            return SNACKS;
        }
        else if (categoryName.equals("사이드")) {
            return SIDES;
        }
        else if (categoryName.equals("음료 / 술")) {
            return DRINKS;
        }
        else {
            return null;
        }
    }

    @Override
    public ChickenName getChickenName(String chickenName) {
        if (chickenName.equals("후라이드치킨")) {
            return FRIED;
        }
        else if (chickenName.equals("양념치킨")) {
            return SEASONED;
        }
        else if (chickenName.equals("뿌링클")) {
            return FRINKLE;
        }
        else if (chickenName.equals("맛초킹")) {
            return MATCHOKING;
        }
        else if (chickenName.equals("골드킹")) {
            return GOLDKING;
        }
        else if (chickenName.equals("콰삭킹")) {
            return QUASACKING;
        }
        else {
            return null;
        }
    }

    @Override
    public SoupName getSoupName(String soupName) {
        if (soupName.equals("오뎅탕")) {
            return ODEN;
        }
        else if (soupName.equals("짬뽕탕")) {
            return JJAMBBONG;
        }
        else if (soupName.equals("조개탕")) {
            return SEASHELL;
        }
        else if (soupName.equals("순대국")) {
            return SUNDAE;
        }
        else {
            return null;
        }
    }

    @Override
    public SnacksName getSnacksName(String snacksName) {
        if (snacksName.equals("쥐포")) {
            return JIWPO;
        }
        else if (snacksName.equals("먹태")) {
            return MUKTAE;
        }
        else if (snacksName.equals("오징어")) {
            return SQUID;
        }
        else {
            return null;
        }
    }

    @Override
    public SidesName getSidesName(String sidesName) {
        if (sidesName.equals("떡볶이")) {
            return TTEOKBOKKI;
        }
        else if (sidesName.equals("감자튀김")) {
            return FRENCHFRIES;
        }
        else if (sidesName.equals("치즈볼")) {
            return CHEESEBALL;
        }
        else if (sidesName.equals("소떡소떡")) {
            return SOTTEOK;
        }
        else {
            return null;
        }
    }

    @Override
    public DrinkName getDrinkName(String drinkName) {
        if (drinkName.equals("콜라")) {
            return COKE;
        }
        else if (drinkName.equals("사이다")) {
            return CIDER;
        }
        else if (drinkName.equals("소주")) {
            return SOJU;
        }
        else if (drinkName.equals("맥주")) {
            return BEER;
        }
        else {
            return null;
        }
    }

    @Override
    public void goMain(Order order) {
        order.setOrderState(OrderState.MAIN);
    }

    @Override
    public void goShoppingCart(Order order) {
        order.setOrderState(OrderState.SHOPPING_CART);
    }

    @Override
    public void goOrder(Order order) {
        order.setOrderState(OrderState.ORDERING);
    }

}
