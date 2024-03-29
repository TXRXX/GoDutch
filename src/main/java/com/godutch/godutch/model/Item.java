package com.godutch.godutch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
//Generates constructors
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Transient //มันมีไว้สำหรับกำหนด field นั้นๆใน java class ให้กับ Hibernate รู้ว่าไม่ต้องสร้าง column นั้นใน table
    public static final String SEQUENCE_NAME="itemId";

    @Id
    @Generated
    private int id;
    private String item_name;
    private double price;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
