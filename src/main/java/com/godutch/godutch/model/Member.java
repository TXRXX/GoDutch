package com.godutch.godutch.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

@Data
//Generates constructors
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Transient //มันมีไว้สำหรับกำหนด field นั้นๆใน java class ให้กับ Hibernate รู้ว่าไม่ต้องสร้าง column นั้นใน table
    public static final String SEQUENCE_NAME="memberID";
    @Id
    private int id;
    private String name;
    private List<String> itemEat = new ArrayList<>();

    private double price; // เก็บจำนวนเงินที่ member ต้องจ่ายแต่ละคน

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getItemEat() {
        return itemEat;
    }

    public void setItemEat(List<String> itemEat) {
        this.itemEat = itemEat;
    }
}
