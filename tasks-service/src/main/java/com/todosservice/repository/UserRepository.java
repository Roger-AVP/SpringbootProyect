package com.todosservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.todosservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>  {
	


}
