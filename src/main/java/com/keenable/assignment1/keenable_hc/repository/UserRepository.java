package com.keenable.assignment1.keenable_hc.repository;

import com.keenable.assignment1.keenable_hc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check if a user with the given username exists (even if soft deleted)
    boolean existsByUserName(String userName);
    boolean existsByUserNameIn(String[] userName);

    // Find a user by username (ignoring soft-deleted users)
    Optional<User> findByUserNameAndIsDeletedFalse(String userName);

    // Find a user by username (ignoring soft-deleted users) and admin is false
    Optional<User> findByUserNameAndIsDeletedFalseAndIsAdminFalse(String userName);

    // Find a user by password (for demo purposes)
    Optional<User> findByPassword(String password);
}