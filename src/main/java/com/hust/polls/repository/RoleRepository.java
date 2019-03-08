package com.hust.polls.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hust.polls.model.Role;
import com.hust.polls.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
	Optional<Role> findByName(RoleName name);
}
