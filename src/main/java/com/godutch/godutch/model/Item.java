package com.godutch.godutch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Item {
    @Id
    private String item_name;
    private double price;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        item_name = item_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
