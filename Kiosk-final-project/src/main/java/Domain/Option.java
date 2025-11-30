package Domain;

import java.io.Serializable;

// 옵션 도메인 클래스
// 메뉴에 추가할 수 있는 옵션(단일/복수)의 이름, 가격, 타입 정보 관리 클래스
public class Option implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int price;
    private OptionType type;

    // 옵션 생성자
    public Option(String name, int price, OptionType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // 옵션 이름 반환 메서드
    public String getName() {
        return name;
    }

    // 옵션 가격 반환 메서드
    public int getPrice() {
        return price;
    }

    // 옵션 유형 반환 메서드
    public OptionType getType() {
        return type;
    }

    // 옵션 이름 설정 메서드
    public void setName(String name) {
        this.name = name;
    }
    // 옵션 가격 설정 메서드
    public void setPrice(int price) {
        this.price = price;
    }

    // 옵션 유형 설정 메서드
    public void setType(OptionType type) {
        this.type = type;
    }
}
