package Static;

import Domain.Category;
import Domain.MenuItem;

import static Domain.Category.*;

public class MenuItemStatic {
    // 치킨 아이템
    private static final MenuItem fried = createMenuItem(CHICKEN, "후라이드치킨", 20000, "/");
    private static final MenuItem seasoned = createMenuItem(CHICKEN, "양념치킨", 22000, "/");
    private static final MenuItem frinkle = createMenuItem(CHICKEN, "뿌링클", 23000, "/");
    private static final MenuItem matchoking = createMenuItem(CHICKEN, "맛초킹", 23000, "/");
    private static final MenuItem goldking = createMenuItem(CHICKEN, "골드킹", 23000, "/");
    private static final MenuItem quasacking = createMenuItem(CHICKEN, "콰삭킹", 23000, "/");

    // 국물 아이템
    private static final MenuItem oden = createMenuItem(SOUP, "오뎅탕", 12000, "/");
    private static final MenuItem jjambbong = createMenuItem(SOUP, "짬뽕탕", 15000, "/");
    private static final MenuItem seashell = createMenuItem(SOUP, "조개탕", 15000, "/");
    private static final MenuItem sundae = createMenuItem(SOUP, "순대국", 13000, "/");

    // 안주류 메뉴
    private static final MenuItem jwipo = createMenuItem(SNACKS, "쥐포", 10000, "/");
    private static final MenuItem muktae = createMenuItem(SNACKS, "먹태", 12000, "/");
    private static final MenuItem squid = createMenuItem(SNACKS, "오징어", 12000, "/");

    // 사이드 메뉴
    private static final MenuItem tteokbokki = createMenuItem(SIDES, "떡볶이", 6000, "/");
    private static final MenuItem frenchFries = createMenuItem(SIDES, "감자튀김", 5000, "/");
    private static final MenuItem cheeseBall = createMenuItem(SIDES, "치즈볼", 5000, "/");
    private static final MenuItem sotteok = createMenuItem(SIDES, "소떡소떡", 3000, "/");

    // 음료 / 주류 메뉴
    private static final MenuItem coke = createMenuItem(DRINKS, "콜라", 2000, "/");
    private static final MenuItem cider = createMenuItem(DRINKS, "사이다", 2000, "/");
    private static final MenuItem soju = createMenuItem(DRINKS, "소주", 5000, "/");
    private static final MenuItem beer = createMenuItem(DRINKS, "맥주", 5000, "/");

    private static final MenuItem [] chickenList = new MenuItem[]{
            fried, seasoned, frinkle, matchoking, goldking, quasacking
    };

    private static final MenuItem [] soupList = new MenuItem[]{
            oden, jjambbong, seashell, sundae
    };

    private static final MenuItem [] snacksList = new MenuItem[]{
            jwipo, muktae, squid
    };

    private static final MenuItem [] sidesList = new MenuItem[]{
            tteokbokki, frenchFries, cheeseBall, sotteok
    };

    private static final MenuItem [] drinkList = new MenuItem[]{
            coke, cider, soju, beer
    };

    private MenuItemStatic() {

    }

    private static MenuItem createMenuItem(Category category, String name, int price, String imagePath) {
        return new MenuItem(category, name, price, imagePath);
    }

    public static MenuItem getFried() {
        return fried;
    }

    public static MenuItem getFrinkle() {
        return frinkle;
    }

    public static MenuItem getSeasoned() {
        return seasoned;
    }

    public static MenuItem getMatchoking() {
        return matchoking;
    }

    public static MenuItem getGoldking() {
        return goldking;
    }

    public static MenuItem getQuasacking() {
        return quasacking;
    }

    public static MenuItem getOden() {
        return oden;
    }

    public static MenuItem getJjambbong() {
        return jjambbong;
    }

    public static MenuItem getSeashell() {
        return seashell;
    }

    public static MenuItem getSundae() {
        return sundae;
    }

    public static MenuItem getJwipo() {
        return jwipo;
    }

    public static MenuItem getMuktae() {
        return muktae;
    }

    public static MenuItem getSquid() {
        return squid;
    }

    public static MenuItem getTteokbokki() {
        return tteokbokki;
    }

    public static MenuItem getFrenchFries() {
        return frenchFries;
    }

    public static MenuItem getCheeseBall() {
        return cheeseBall;
    }

    public static MenuItem getSotteok() {
        return sotteok;
    }

    public static MenuItem getCoke() {
        return coke;
    }

    public static MenuItem getCider() {
        return cider;
    }

    public static MenuItem getSoju() {
        return soju;
    }

    public static MenuItem getBeer() {
        return beer;
    }

    public static MenuItem[] getChickenList() {
        return chickenList;
    }

    public static MenuItem[] getSoupList() {
        return soupList;
    }

    public static MenuItem[] getSnacksList() {
        return snacksList;
    }

    public static MenuItem[] getSidesList() {
        return sidesList;
    }

    public static MenuItem[] getDrinkList() {
        return drinkList;
    }
}
