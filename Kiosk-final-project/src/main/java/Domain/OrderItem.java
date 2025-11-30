package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Domain.OrderState.*;

// 주문 항목 도메인 클래스
// 메뉴명, 수량, 가격, 옵션 정보를 포함하는 개별 주문 항목 관리 클래스
public class OrderItem extends IdCounter implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private String menuName;
    private int price;
    private int quantity;

    private OrderState orderState;

    private Option singleOption;
    private List<Option> multipleOptions = new ArrayList<>();
    private int cachedPriceWithOptions;
    private String optionSummary;

    // 메뉴 아이템으로 주문 항목 생성자
    public OrderItem(MenuItem menuItem) {
        this(orderItemIdCounter.getAndIncrement(), menuItem.getName(), menuItem.getOriginalPrice(), 1);
    }

    // 주문 항목 생성자
    public OrderItem(Long id, String menuName, int price, int quantity) {
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
        this.orderState = CHOOSE_ORDER;
    }

    // 단일 옵션 및 다중 옵션 설정 메서드
    public void setOptions(Option singleOption, List<Option> multipleOption) {
        if (singleOption == null) {
            throw new IllegalArgumentException("single option can't be null");
        }

        this.singleOption = (singleOption);
        this.multipleOptions.addAll(multipleOption);
        this.optionSummary = null;
    }

    // 옵션 포함 가격 계산 메서드
    public int getPriceWithOptions() {
        int singleOptionPrice;
        int multipleOptionPrice = 0;

        if (singleOption == null) {
            if (cachedPriceWithOptions > 0) {
                return cachedPriceWithOptions;
            }
            throw new IllegalArgumentException("single option can't be null or empty");
        }
        else {
            singleOptionPrice = singleOption.getPrice();
        }

        if (!multipleOptions.isEmpty()) {
            for (Option eachMultipleOption : multipleOptions) {
                multipleOptionPrice += eachMultipleOption.getPrice();
            }
        }

        cachedPriceWithOptions = price + singleOptionPrice + multipleOptionPrice;
        return cachedPriceWithOptions;
    }

    // 주문 항목 ID 반환 메서드
    public Long getId() {
        return id;
    }

    // 메뉴 이름 반환 메서드
    public String getMenuName() {
        return menuName;
    }

    // 메뉴 이름 설정 메서드
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    // 가격 반환 메서드
    public int getPrice() {
        return price;
    }

    // 가격 설정 메서드
    public void setPrice(int price) {
        this.price = price;
    }

    // 수량 반환 메서드
    public int getQuantity() {
        return quantity;
    }

    // 수량 설정 메서드
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // 주문 상태 반환 메서드
    public OrderState getOrderState() {
        return orderState;
    }

    // 주문 상태 설정 메서드
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    // 단일 옵션 반환 메서드
    public Option getSingleOption() {
        return singleOption;
    }

    // 다중 옵션 목록 반환 메서드
    public List<Option> getMultipleOptions() {
        return multipleOptions;
    }

    // 옵션 요약 설정 메서드
    public void setOptionSummary(String optionSummary) {
        this.optionSummary = optionSummary;
    }

    // 옵션 포함 가격 캐시 설정 메서드
    public void setCachedPriceWithOptions(int cachedPriceWithOptions) {
        this.cachedPriceWithOptions = cachedPriceWithOptions;
    }

    // 옵션 정보 문자열 반환 메서드
    public String getOptionsString() {
        if (singleOption == null && optionSummary != null) {
            return optionSummary;
        }

        StringBuilder optionsStr = new StringBuilder();
        
        // 단일 옵션
        if (singleOption != null) {
            optionsStr.append("단일 옵션 : ").append(singleOption.getName());
        }
        
        // 다중 옵션
        if (!multipleOptions.isEmpty()) {
            if (optionsStr.length() > 0) {
                optionsStr.append("\n");
            }
            optionsStr.append("다중 옵션 : ");
            for (int i = 0; i < multipleOptions.size(); i++) {
                if (i > 0) {
                    optionsStr.append(", ");
                }
                optionsStr.append(multipleOptions.get(i).getName());
            }
        }
        
        optionSummary = optionsStr.toString();
        return optionSummary;
    }

    // 동일한 메뉴와 옵션인지 비교 메서드
    public boolean isSameMenuAndOptions(OrderItem other) {
        if (other == null) return false;
        
        // 메뉴 이름 비교
        if (!this.menuName.equals(other.menuName)) {
            return false;
        }
        
        // 단일 옵션 비교
        if (this.singleOption == null && other.singleOption == null) {
            // 둘 다 null이면 같음
        } else if (this.singleOption == null || other.singleOption == null) {
            return false;
        } else if (!this.singleOption.getName().equals(other.singleOption.getName())) {
            return false;
        }
        
        // 다중 옵션 비교
        if (this.multipleOptions.size() != other.multipleOptions.size()) {
            return false;
        }
        
        // 다중 옵션 이름 리스트 생성 및 정렬하여 비교
        List<String> thisMultipleNames = new ArrayList<>();
        for (Option opt : this.multipleOptions) {
            thisMultipleNames.add(opt.getName());
        }
        Collections.sort(thisMultipleNames);
        
        List<String> otherMultipleNames = new ArrayList<>();
        for (Option opt : other.multipleOptions) {
            otherMultipleNames.add(opt.getName());
        }
        Collections.sort(otherMultipleNames);
        
        return thisMultipleNames.equals(otherMultipleNames);
    }
}
