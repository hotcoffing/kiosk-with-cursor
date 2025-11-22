package Domain;

public class Option {
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
