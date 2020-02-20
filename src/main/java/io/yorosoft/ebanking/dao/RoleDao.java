package io.yorosoft.ebanking.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.yorosoft.ebanking.model.security.Role;

/**
 * RoleDao
 */
@Repository
public interface RoleDao extends CrudRepository<Role, Long>{

    Role findByName(String name);
}