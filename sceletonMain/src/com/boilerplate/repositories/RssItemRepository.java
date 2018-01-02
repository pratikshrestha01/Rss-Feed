package com.boilerplate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.boilerplate.entity.Admin;

import com.boilerplate.entity.RssItem;
import com.boilerplate.model.UserType;

public interface RssItemRepository extends CrudRepository<RssItem, Long>, JpaSpecificationExecutor<RssItem> {

	@Query("select r  from RssItem r where r.title=?1 and r.description=?2")
	List<RssItem> findByNameAndDesc(String name, String desc);

}
