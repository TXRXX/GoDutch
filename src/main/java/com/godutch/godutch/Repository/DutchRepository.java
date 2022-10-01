package com.godutch.godutch.Repository;

import com.godutch.godutch.model.Dutch;
import com.godutch.godutch.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DutchRepository extends MongoRepository<Dutch,Integer> {
    //เก็บฟังก์ชันใช้งานต่างๆ
}
