package Domain;

import java.util.ArrayList;
import java.util.List;

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
