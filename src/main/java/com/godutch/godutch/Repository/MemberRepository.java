package com.godutch.godutch.Repository;

import com.godutch.godutch.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member,Integer> {

}
