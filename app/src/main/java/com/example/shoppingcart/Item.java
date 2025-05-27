package com.example.shoppingcart;

public class Item {
    final private String name;
    private int quantity;
    final private int imageResId;
    final private double price;

    public Item(String name, int quantity, int imageResId, double price) {
        this.name = name;
        this.quantity = quantity;
        this.imageResId = imageResId;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}