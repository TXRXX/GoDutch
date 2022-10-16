package com.godutch.godutch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
//Generates constructors
@AllArgsConstructor
@NoArgsConstructor
public class DbSequence {
    @Id
    private String id;
    private int seq;
}
