package Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Domain.OrderState.*;

// 주문 항목 도메인 클래스
// 메뉴명, 수량, 가격, 옵션 정보를 포함하는 개별 주문 항목을 관리
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

    public OrderItem(MenuItem menuItem) {
        this(orderItemIdCounter.getAndIncrement(), menuItem.getName(), menuItem.getOriginalPrice(), 1);
    }

    public OrderItem(Long id, String menuName, int price, int quantity) {
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
        this.orderState = CHOOSE_ORDER;
    }

    // 옵션 설정 (Swing actionListener 활용)
    public void setOptions(Option singleOption, List<Option> multipleOption) {
        if (singleOption == null) {
            throw new IllegalArgumentException("single option can't be null");
        }

        this.singleOption = (singleOption);
        this.multipleOptions.addAll(multipleOption);
        this.optionSummary = null;
    }

    // 옵션을 포함한 하나의 음식 가격 계산
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

    // Getter
    public Long getId() {
        return id;
    }

    // Getter and Setter
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Option getSingleOption() {
        return singleOption;
    }

    public List<Option> getMultipleOptions() {
        return multipleOptions;
    }

    public void setOptionSummary(String optionSummary) {
        this.optionSummary = optionSummary;
    }

    public void setCachedPriceWithOptions(int cachedPriceWithOptions) {
        this.cachedPriceWithOptions = cachedPriceWithOptions;
    }

    // 옵션 정보를 문자열로 반환 (단일 옵션과 다중 옵션 분리)
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

    // 같은 메뉴와 옵션인지 비교하는 메서드
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
