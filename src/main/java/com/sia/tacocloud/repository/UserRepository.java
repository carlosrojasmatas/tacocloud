package com.sia.tacocloud.repository;

import com.sia.tacocloud.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    User findByUsername(String userName);
}
