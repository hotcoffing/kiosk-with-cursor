package Domain;

import java.util.ArrayList;
import java.util.List;

import static Domain.OptionType.SINGLE;

public class OrderItem {
    private Long id;
    private String menuName;
    private int quantity;
    private int price;
    private OrderState orderState;

    private Option singleOption = new Option("", 0, SINGLE);
    private List<Option> multipleOptions = new ArrayList<>();

    public OrderItem(String menuName, int quantity, int price, OrderState orderState) {
        this.menuName = menuName;
        this.quantity = quantity;
        this.price = price;
        this.orderState = orderState;
    }

    // 옵션 설정 (Swing actionListener 활용)
    public void setSingleOption(Option option) {
        if (option == null) {
            throw new IllegalArgumentException("option can't be null");
        }
        if (option.getType() == SINGLE) {
            this.singleOption = option;
        }
    }

    public void setMultipleOptions(List<Option> options) {
        if (options == null) {
            throw new IllegalArgumentException("option can't be null");
        }
        this.multipleOptions.clear();
        for (Option EachMultipleOption : options) {
            if (EachMultipleOption.getType() == OptionType.MULTIPLE) {
                this.multipleOptions.add(EachMultipleOption);
            }
        }
    }

    // 옵션을 포함한 하나의 음식 가격 계산
    public int getPriceWithOption() {
        int singleOptionPrice = 0;
        int multipleOptionPrice = 0;

        if (singleOption != null) {
            singleOptionPrice = singleOption.getPrice();
        }

        for (Option EachMultipleOption : multipleOptions) {
            multipleOptionPrice += EachMultipleOption.getPrice();
        }

        return (price + singleOptionPrice + multipleOptionPrice) * quantity;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}
