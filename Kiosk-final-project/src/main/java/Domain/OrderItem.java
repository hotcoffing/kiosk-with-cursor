package Domain;

import java.util.ArrayList;
import java.util.List;

import static Domain.OrderState.*;

public class OrderItem extends IdCounter {
    private final Long id;
    private String menuName;
    private int price;
    private int quantity;

    private OrderState orderState;

    private Option singleOption;
    private List<Option> multipleOptions = new ArrayList<>();

    public OrderItem(MenuItem menuItem) {
        this.id = orderItemIdCounter.getAndIncrement();
        this.menuName = menuItem.getName();
        this.price = menuItem.getOriginalPrice();
        this.quantity = 1;
        this.orderState = CHOOSE_ORDER;
    }

    // 옵션 설정 (Swing actionListener 활용)
    public void setOptions(Option singleOption,  List<Option> multipleOption) {
        if (singleOption == null) {
            throw new IllegalArgumentException("single option can't be null");
        }

        this.singleOption = (singleOption);
        this.multipleOptions.addAll(multipleOption);
    }

    // 옵션을 포함한 하나의 음식 가격 계산
    public int getPriceWithOptions() {
        int singleOptionPrice;
        int multipleOptionPrice = 0;

        if (singleOption == null) {
            throw new IllegalArgumentException("single option can't be null or empty");
        }
        else {
            singleOptionPrice = singleOption.getPrice();
        }

        if (!multipleOptions.isEmpty()) {
            for (Option EachMultipleOption : multipleOptions) {
                multipleOptionPrice += EachMultipleOption.getPrice();
            }
        }

        return price + singleOptionPrice + multipleOptionPrice;
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
}
