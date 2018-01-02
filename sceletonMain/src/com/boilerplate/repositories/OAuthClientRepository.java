package com.boilerplate.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.boilerplate.entity.OAuthClient;

public interface OAuthClientRepository extends CrudRepository<OAuthClient,String>,JpaSpecificationExecutor<OAuthClient> {

}
