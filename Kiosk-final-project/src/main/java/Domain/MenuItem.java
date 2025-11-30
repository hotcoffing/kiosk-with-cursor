package Domain;
// 메뉴 항목 정보 도메인 클래스
public class MenuItem {
    private Category category;
    private String name;
    private int originalPrice;
    private String imagePath;
    
    // 메뉴 아이템 생성자
    public MenuItem(Category category, String name, int OriginalPrice, String imagePath) {
        this.category = category;
        this.name = name;
        this.originalPrice = OriginalPrice;
        this.imagePath = imagePath;
    }
    
    // 이미지 경로 없는 메뉴 아이템 생성자
    public MenuItem(Category category, String name, int OriginalPrice) {
        this(category, name, OriginalPrice, "");
        System.out.println("Error : " + this.name + " has No ImagePath");
    }

    // 카테고리 반환 메서드
    public Category getCategory() {
        return category;
    }

    // 메뉴 이름 반환 메서드
    public String getName() {
        return name;
    }

    // 원가 반환 메서드
    public int getOriginalPrice() {
        return originalPrice;
    }

    // 이미지 경로 반환 메서드
    public String getImagePath() {
        return imagePath;
    }
}
