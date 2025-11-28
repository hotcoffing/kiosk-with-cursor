package Static;

import Domain.Category;
import static Domain.Category.*;
import Domain.MenuItem;

// 메뉴 아이템 정적 데이터 클래스
// 모든 메뉴 아이템(치킨, 국물, 간식, 사이드, 음료)을 정적으로 정의하고 제공
public class MenuItemStatic {
    // 치킨 아이템
    private static final MenuItem fried = createMenuItem(CHICKEN, "후라이드치킨", 20000, "/image/CHICKEN/후라이드치킨.png");
    private static final MenuItem seasoned = createMenuItem(CHICKEN, "양념치킨", 22000, "/image/CHICKEN/양념치킨.png");
    private static final MenuItem frinkle = createMenuItem(CHICKEN, "뿌링클", 23000, "/image/CHICKEN/뿌링클.png");
    private static final MenuItem matchoking = createMenuItem(CHICKEN, "맛초킹", 23000, "/image/CHICKEN/맛초킹-temp.png");
    private static final MenuItem goldking = createMenuItem(CHICKEN, "골드킹", 23000, "/image/CHICKEN/골드킹-temp.png");
    private static final MenuItem quasacking = createMenuItem(CHICKEN, "콰삭킹", 23000, "/image/CHICKEN/콰삭킹-temp.png");

    // 국물 아이템
    private static final MenuItem oden = createMenuItem(SOUP, "오뎅탕", 12000, "/image/SOUP/오뎅탕.png");
    private static final MenuItem jjambbong = createMenuItem(SOUP, "짬뽕탕", 15000, "/image/SOUP/짬뽕탕.png");
    private static final MenuItem seashell = createMenuItem(SOUP, "조개탕", 15000, "/image/SOUP/조개탕.png");
    private static final MenuItem sundae = createMenuItem(SOUP, "순대국", 13000, "/image/SOUP/순대국.png");

    // 안주류 메뉴
    private static final MenuItem jwipo = createMenuItem(SNACKS, "쥐포", 10000, "/image/SNACKS/쥐포.png");
    private static final MenuItem muktae = createMenuItem(SNACKS, "먹태", 12000, "/image/SNACKS/먹태.png");
    private static final MenuItem squid = createMenuItem(SNACKS, "오징어", 12000, "/image/SNACKS/오징어.png");

    // 사이드 메뉴
    private static final MenuItem tteokbokki = createMenuItem(SIDES, "떡볶이", 6000, "/image/SIDES/떡볶이.png");
    private static final MenuItem frenchFries = createMenuItem(SIDES, "감자튀김", 5000, "/image/SIDES/감자튀김.png");
    private static final MenuItem cheeseBall = createMenuItem(SIDES, "치즈볼", 5000, "/image/SIDES/치즈볼.png");
    private static final MenuItem sotteok = createMenuItem(SIDES, "소떡소떡", 3000, "/image/SIDES/소떡소떡.png");

    // 음료 / 주류 메뉴
    private static final MenuItem coke = createMenuItem(DRINKS, "콜라", 2000, "/image/DRINKS/콜라.png");
    private static final MenuItem cider = createMenuItem(DRINKS, "사이다", 2000, "/image/DRINKS/사이다.png");
    private static final MenuItem soju = createMenuItem(DRINKS, "소주", 5000, "/image/DRINKS/소주.png");
    private static final MenuItem beer = createMenuItem(DRINKS, "맥주", 5000, "/image/DRINKS/맥주.png");

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

    // menuName으로 MenuItem 탐색
    public static MenuItem findMenuItemByName(String menuName) {
        // 모든 리스트를 순회하며 탐색
        MenuItem[][] allLists = {chickenList, soupList, snacksList, sidesList, drinkList};
        for (MenuItem[] list : allLists) {
            for (MenuItem item : list) {
                if (item.getName().equals(menuName)) {
                    return item;
                }
            }
        }
        return null;
    }
}
