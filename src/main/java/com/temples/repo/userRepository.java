package com.temples.repo;

import org.springframework.stereotype.Repository;

import com.temples.model.UserEntity;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface userRepository extends JpaRepository<UserEntity,Long> {
	Optional<UserEntity> findByUserName(String userName);
}
