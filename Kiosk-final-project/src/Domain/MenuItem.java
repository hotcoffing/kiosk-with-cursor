package Domain;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private String name;
    private int originalPrice;
    private String imagePath;

    private List<Option> singleOptionsList = new ArrayList<>();
    private List<Option> multipleOptionsList = new ArrayList<>();

    public MenuItem(String name, int OriginalPrice, String imagePath) {
        this.name = name;
        this.originalPrice = OriginalPrice;
        this.imagePath = imagePath;
    }
    
    // 이미지 주소 에러 미리 방지 (추후 삭제 필요)
    public MenuItem(String name, int OriginalPrice) {
        this(name, OriginalPrice, "");
        System.out.println("Error : " + this.name + " has No ImagePath");
    }
    
    // 옵션 추가
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
    }

    // Getter and Setter
    public String getName() {
        return name;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public List<Option> getSingleOptionsList() {
        return singleOptionsList;
    }

    public List<Option> getMultipleOptionsList() {
        return multipleOptionsList;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
