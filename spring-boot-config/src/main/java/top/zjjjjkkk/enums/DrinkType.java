package top.zjjjjkkk.enums;

import lombok.Getter;

@Getter
public enum DrinkType {
    COFFEE("咖啡", 15.0), TEA("奶茶", 12.0), JUICE("果汁", 10.0);

    private final String label;
    private final double price;

    DrinkType(String label, double price){
        this.label = label;
        this.price = price;
    }
    public String getLabel(){
        return label;
    }

    public double getPrice(){
        return price;
    }
}