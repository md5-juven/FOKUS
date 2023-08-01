package com.vc.kanbanUser.repository;

import com.vc.kanbanUser.domain.ContactUs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepos extends MongoRepository<ContactUs,String> {
}
