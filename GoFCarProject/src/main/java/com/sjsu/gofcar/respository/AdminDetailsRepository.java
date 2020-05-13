package com.sjsu.gofcar.respository;

import com.sjsu.gofcar.model.user.AdminDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDetailsRepository extends MongoRepository<AdminDetails, String> {
}
