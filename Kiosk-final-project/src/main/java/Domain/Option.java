package Domain;

import java.io.Serializable;

// 옵션 도메인 클래스
// 메뉴에 추가할 수 있는 옵션(단일/복수)의 이름, 가격, 타입 정보를 관리
public class Option implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int price;
    private OptionType type;


    public Option(String name, int price, OptionType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // Getter and Setter
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public OptionType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public void setType(OptionType type) {
        this.type = type;
    }
}
