package Domain;
// 메뉴의 카테고리, 이름, 가격, 이미지 경로 정보를 관리
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

    // Getter and Setter
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
