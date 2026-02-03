package org.revature.ecommproject.dto;

public class ProductRequestDTO {

    private String name;
    private float price;
    private String color;

    public ProductRequestDTO(String name, float price, String color) {
        this.name = name;
        this.price = price;
        this.color = color;
    }

    public ProductRequestDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
