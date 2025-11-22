package Domain;

public class Option {
    private final String name;
    private final int price;
    private final OptionType type;


    public Option(String name, int price, OptionType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public OptionType getType() {
        return type;
    }
}
