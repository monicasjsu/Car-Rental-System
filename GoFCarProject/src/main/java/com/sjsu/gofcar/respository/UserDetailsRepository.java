package com.sjsu.gofcar.respository;

import com.sjsu.gofcar.model.user.UserDetails;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends MongoRepository<UserDetails, String> {

    List<UserDetails> findAllByIsActive(boolean active);
}
