package kioskService;

import Domain.Category;
import Domain.ChickenName;
import Domain.DrinkName;
import Domain.Order;
import Domain.OrderState;
import Domain.SidesName;
import Domain.SnacksName;
import Domain.SoupName;
import Repository.ShoppingCartRepository;

import static Domain.Category.CHICKEN;
import static Domain.Category.DRINKS;
import static Domain.Category.SIDES;
import static Domain.Category.SNACKS;
import static Domain.Category.SOUP;
import static Domain.ChickenName.FRIED;
import static Domain.ChickenName.FRINKLE;
import static Domain.ChickenName.GOLDKING;
import static Domain.ChickenName.MATCHOKING;
import static Domain.ChickenName.QUASACKING;
import static Domain.ChickenName.SEASONED;
import static Domain.DrinkName.BEER;
import static Domain.DrinkName.CIDER;
import static Domain.DrinkName.COKE;
import static Domain.DrinkName.SOJU;
import static Domain.SidesName.CHEESEBALL;
import static Domain.SidesName.FRENCHFRIES;
import static Domain.SidesName.SOTTEOK;
import static Domain.SidesName.TTEOKBOKKI;
import static Domain.SnacksName.JIWPO;
import static Domain.SnacksName.MUKTAE;
import static Domain.SnacksName.SQUID;
import static Domain.SoupName.JJAMBBONG;
import static Domain.SoupName.ODEN;
import static Domain.SoupName.SEASHELL;
import static Domain.SoupName.SUNDAE;

// 메뉴 선택 서비스 구현 클래스
// 메뉴 카테고리 및 메뉴 이름 조회 및 주문 상태 관리 기능 구현 클래스
public class SelectMenuServiceImpl extends CalcMoneyAdapter implements SelectMenuService {
    
    // 메뉴 선택 서비스 생성자
    public SelectMenuServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        super(shoppingCartRepository);
    }

    // 카테고리 이름으로 카테고리 열거형 반환 메서드
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

    // 국물 이름으로 국물 열거형 반환 메서드
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

    // 간식 이름으로 간식 열거형 반환 메서드
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

    // 사이드 이름으로 사이드 열거형 반환 메서드
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

    // 장바구니 화면으로 이동 메서드
    @Override
    public void goShoppingCart(Order order) {
        order.setOrderState(OrderState.SHOPPING_CART);
    }

    // 주문 화면으로 이동 메서드
    @Override
    public void goOrder(Order order) {
        order.setOrderState(OrderState.ORDERING);
    }

}
