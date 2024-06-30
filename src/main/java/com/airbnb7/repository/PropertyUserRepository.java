package com.airbnb7.repository;

import com.airbnb7.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyUserRepository extends JpaRepository<PropertyUser, Long> {

    Optional<PropertyUser> findByUsername(String username);

}