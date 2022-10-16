package com.godutch.godutch.Repository;

import com.godutch.godutch.model.Dutch;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface DutchRepository extends MongoRepository<Dutch,Integer> {
    //เก็บฟังก์ชันใช้งานต่างๆ
}
