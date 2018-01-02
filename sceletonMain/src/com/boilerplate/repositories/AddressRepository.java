package com.boilerplate.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boilerplate.entity.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>, JpaSpecificationExecutor<Address> {

}
