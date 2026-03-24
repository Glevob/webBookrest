package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
}
