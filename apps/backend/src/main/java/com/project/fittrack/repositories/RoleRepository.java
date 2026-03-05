package com.project.fittrack.repositories;

import com.project.fittrack.entities.Role;
import com.project.fittrack.enums.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(AppRole name);
}
