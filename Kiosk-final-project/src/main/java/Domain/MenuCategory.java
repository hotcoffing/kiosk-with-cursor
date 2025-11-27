package Domain;

import java.util.ArrayList;
import java.util.List;

// 메뉴 카테고리 클래스
// 카테고리 이름과 해당 카테고리에 속한 메뉴 아이템 목록을 관리
public class MenuCategory {
    private String name;
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

    public MenuCategory(String name) {
        this.name = name;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    // Getter and setter
    public String getName() {
        return name;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setName(String name) {
        this.name = name;
    }
}
