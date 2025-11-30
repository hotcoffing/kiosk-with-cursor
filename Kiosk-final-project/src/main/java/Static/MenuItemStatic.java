package Static;

import Domain.Category;
import static Domain.Category.CHICKEN;
import static Domain.Category.DRINKS;
import static Domain.Category.SIDES;
import static Domain.Category.SNACKS;
import static Domain.Category.SOUP;
import Domain.MenuItem;

// 메뉴 아이템 정적 데이터 클래스
// 모든 메뉴 아이템(치킨, 국물, 간식, 사이드, 음료) 정적 정의 및 제공 클래스
public class MenuItemStatic {
    // 치킨 아이템
    private static final MenuItem fried = createMenuItem(CHICKEN, "후라이드치킨", 20000, "/image/CHICKEN/후라이드치킨.png");
    private static final MenuItem seasoned = createMenuItem(CHICKEN, "양념치킨", 22000, "/image/CHICKEN/양념치킨.png");
    private static final MenuItem frinkle = createMenuItem(CHICKEN, "뿌링클", 23000, "/image/CHICKEN/뿌링클.png");
    private static final MenuItem matchoking = createMenuItem(CHICKEN, "맛초킹", 23000, "/image/CHICKEN/맛초킹.png");
    private static final MenuItem goldking = createMenuItem(CHICKEN, "골드킹", 23000, "/image/CHICKEN/골드킹.png");
    private static final MenuItem quasacking = createMenuItem(CHICKEN, "콰삭킹", 23000, "/image/CHICKEN/콰삭킹.png");

    // 국물 아이템
    private static final MenuItem oden = createMenuItem(SOUP, "오뎅탕", 12000, "/image/SOUP/오뎅탕.png");
    private static final MenuItem jjambbong = createMenuItem(SOUP, "짬뽕탕", 15000, "/image/SOUP/짬뽕탕.png");
    private static final MenuItem seashell = createMenuItem(SOUP, "조개탕", 15000, "/image/SOUP/조개탕.png");
    private static final MenuItem sundae = createMenuItem(SOUP, "순대국", 13000, "/image/SOUP/순대국.png");
    private static final MenuItem armyStew = createMenuItem(SOUP, "부대찌게", 13000, "/image/SOUP/부대찌게.png");
    private static final MenuItem gopchangHotpot = createMenuItem(SOUP, "곱창전골", 18000, "/image/SOUP/곱창전골.png");

    // 안주류 메뉴
    private static final MenuItem jwipo = createMenuItem(SNACKS, "쥐포", 10000, "/image/SNACKS/쥐포.png");
    private static final MenuItem muktae = createMenuItem(SNACKS, "먹태", 12000, "/image/SNACKS/먹태.png");
    private static final MenuItem squid = createMenuItem(SNACKS, "오징어", 12000, "/image/SNACKS/오징어.png");
    private static final MenuItem sausage = createMenuItem(SNACKS, "소세지", 12000, "/image/SNACKS/소세지.png");
    private static final MenuItem chickenFeet = createMenuItem(SNACKS, "닭발", 15000, "/image/SNACKS/닭발.png");
    private static final MenuItem friedChickenGizzard = createMenuItem(SNACKS, "닭똥집튀김", 15000, "/image/SNACKS/닭똥집튀김.png");

    // 사이드 메뉴
    private static final MenuItem tteokbokki = createMenuItem(SIDES, "떡볶이", 6000, "/image/SIDES/떡볶이.png");
    private static final MenuItem frenchFries = createMenuItem(SIDES, "감자튀김", 5000, "/image/SIDES/감자튀김.png");
    private static final MenuItem cheeseBall = createMenuItem(SIDES, "치즈볼", 5000, "/image/SIDES/치즈볼.png");
    private static final MenuItem sotteok = createMenuItem(SIDES, "소떡소떡", 3000, "/image/SIDES/소떡소떡.png");
    private static final MenuItem jjolmyeon = createMenuItem(SIDES, "쫄면", 4000, "/image/SIDES/쫄면.png");
    private static final MenuItem rice = createMenuItem(SIDES, "공기밥", 1000, "/image/SIDES/공기밥.png");

    // 음료 / 주류 메뉴
    private static final MenuItem coke = createMenuItem(DRINKS, "콜라", 2000, "/image/DRINKS/콜라.png");
    private static final MenuItem cider = createMenuItem(DRINKS, "사이다", 2000, "/image/DRINKS/사이다.png");
    private static final MenuItem fanta = createMenuItem(DRINKS, "환타", 2000, "/image/DRINKS/환타.png");
    private static final MenuItem soju = createMenuItem(DRINKS, "소주", 5000, "/image/DRINKS/소주.png");
    private static final MenuItem beer = createMenuItem(DRINKS, "맥주", 5000, "/image/DRINKS/맥주.png");
    private static final MenuItem draftBeer = createMenuItem(DRINKS, "생맥주", 5000, "/image/DRINKS/생맥주.png");

    private static final MenuItem [] chickenList = new MenuItem[]{
            fried, seasoned, frinkle, matchoking, goldking, quasacking
    }; 

    private static final MenuItem [] soupList = new MenuItem[]{
            oden, sundae, armyStew, jjambbong, seashell, gopchangHotpot
    }; 

    private static final MenuItem [] snacksList = new MenuItem[]{
            jwipo, muktae, squid, sausage, chickenFeet, friedChickenGizzard
    }; 

    private static final MenuItem [] sidesList = new MenuItem[]{
            rice, sotteok, jjolmyeon, frenchFries, cheeseBall, tteokbokki
    }; 

    private static final MenuItem [] drinkList = new MenuItem[]{
            coke, cider, fanta, soju, beer, draftBeer
    }; 

    private MenuItemStatic() {

    }

    // 메뉴 아이템 생성 메서드
    private static MenuItem createMenuItem(Category category, String name, int price, String imagePath) {
        return new MenuItem(category, name, price, imagePath);
    }

    // 후라이드치킨 반환 메서드
    public static MenuItem getFried() {
        return fried;
    }

    // 뿌링클 반환 메서드
    public static MenuItem getFrinkle() {
        return frinkle;
    }

    // 양념치킨 반환 메서드
    public static MenuItem getSeasoned() {
        return seasoned;
    }

    // 맛초킹 반환 메서드
    public static MenuItem getMatchoking() {
        return matchoking;
    }

    // 골드킹 반환 메서드
    public static MenuItem getGoldking() {
        return goldking;
    }

    // 콰삭킹 반환 메서드
    public static MenuItem getQuasacking() {
        return quasacking;
    }

    // 오뎅탕 반환 메서드
    public static MenuItem getOden() {
        return oden;
    }

    // 짬뽕탕 반환 메서드
    public static MenuItem getJjambbong() {
        return jjambbong;
    }

    // 조개탕 반환 메서드
    public static MenuItem getSeashell() {
        return seashell;
    }

    // 순대국 반환 메서드
    public static MenuItem getSundae() {
        return sundae;
    }

    // 쥐포 반환 메서드
    public static MenuItem getJwipo() {
        return jwipo;
    }

    // 먹태 반환 메서드
    public static MenuItem getMuktae() {
        return muktae;
    }

    // 오징어 반환 메서드
    public static MenuItem getSquid() {
        return squid;
    }

    // 떡볶이 반환 메서드
    public static MenuItem getTteokbokki() {
        return tteokbokki;
    }

    // 감자튀김 반환 메서드
    public static MenuItem getFrenchFries() {
        return frenchFries;
    }

    // 치즈볼 반환 메서드
    public static MenuItem getCheeseBall() {
        return cheeseBall;
    }

    // 소떡소떡 반환 메서드
    public static MenuItem getSotteok() {
        return sotteok;
    }

    // 콜라 반환 메서드
    public static MenuItem getCoke() {
        return coke;
    }

    // 사이다 반환 메서드
    public static MenuItem getCider() {
        return cider;
    }

    // 소주 반환 메서드
    public static MenuItem getSoju() {
        return soju;
    }

    // 맥주 반환 메서드
    public static MenuItem getBeer() {
        return beer;
    }

    // 치킨 메뉴 목록 반환 메서드
    public static MenuItem[] getChickenList() {
        return chickenList;
    }

    // 국물류 메뉴 목록 반환 메서드
    public static MenuItem[] getSoupList() {
        return soupList;
    }

    // 안주류 메뉴 목록 반환 메서드
    public static MenuItem[] getSnacksList() {
        return snacksList;
    }

    // 사이드 메뉴 목록 반환 메서드
    public static MenuItem[] getSidesList() {
        return sidesList;
    }

    // 음료 메뉴 목록 반환 메서드
    public static MenuItem[] getDrinkList() {
        return drinkList;
    }

    // 메뉴 이름으로 메뉴 아이템 검색 메서드
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
