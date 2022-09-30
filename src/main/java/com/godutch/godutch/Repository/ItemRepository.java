package com.godutch.godutch.Repository;

import com.godutch.godutch.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ItemRepository extends MongoRepository<Item, Integer> {

}
