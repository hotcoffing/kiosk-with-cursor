package Domain;

import java.util.*;

public class MenuItem {
    private Category category;
    private String name;
    private int originalPrice;
    private String imagePath;
    
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
    
    // 옵션 리스트 추가 (의미에 맞지 않는것 같아 주석 처리함)
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

    // Getter and Setter
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
