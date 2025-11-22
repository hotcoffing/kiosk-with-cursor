package Domain;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private final Category category;
    private final String name;
    private final int originalPrice;
    private final String imagePath;
    
    public MenuItem(Category category, String name, int OriginalPrice, String imagePath) {
        this.category = category;
        this.name = name;
        this.originalPrice = OriginalPrice;
        this.imagePath = imagePath;
    }
    
    // 이미지 주소 에러 미리 방지 (체크용)
    public MenuItem(Category category, String name, int OriginalPrice) {
        this(category, name, OriginalPrice, "");
        System.out.println("Error : " + this.name + " has No ImagePath");
    }
    
    // 옵션 리스트 추가 (치킨만 옵션을 부여하면 일단 필요하지 않아 주석 처리함)
    /*private List<Option> singleOptionsList = new ArrayList<>();
    private List<Option> multipleOptionsList = new ArrayList<>();

    public void addSingleOption(Option option) {
        if (option.getType() == OptionType.SINGLE) {
            singleOptionsList.add(option);
        }
        else {
            throw new IllegalStateException("Single Option Not Allowed");
        }
    }

    public void addMultipleOption(Option option) {
        if (option.getType() == OptionType.MULTIPLE) {
            multipleOptionsList.add(option);
        }
        else {
            throw new IllegalStateException("Multiple Option Not Allowed");
        }
    }*/

    // Getter
    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public String getImagePath() {
        return imagePath;
    }

}
