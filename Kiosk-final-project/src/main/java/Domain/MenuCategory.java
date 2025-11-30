package Domain;

import java.util.ArrayList;
import java.util.List;

// 메뉴 카테고리 클래스
// 카테고리 이름과 해당 카테고리에 속한 메뉴 아이템 목록 관리 클래스
public class MenuCategory {
    private String name;
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

    // 메뉴 카테고리 생성자
    public MenuCategory(String name) {
        this.name = name;
    }

    // 메뉴 아이템 추가 메서드
    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    // 카테고리 이름 반환 메서드
    public String getName() {
        return name;
    }

    // 메뉴 아이템 목록 반환 메서드
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    // 카테고리 이름 설정 메서드
    public void setName(String name) {
        this.name = name;
    }
}
