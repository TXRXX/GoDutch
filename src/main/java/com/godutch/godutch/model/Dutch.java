package com.godutch.godutch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Dutch {
    @Id
    private String id;
    private String topic;
    private List<Item> item;

    public Dutch(String topic, List<Item> item) {
        this.topic = topic;
        this.item = item;
    }
}
