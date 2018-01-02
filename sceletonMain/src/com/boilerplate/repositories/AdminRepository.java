package com.boilerplate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.boilerplate.entity.Admin;
import com.boilerplate.model.UserType;




public interface AdminRepository extends CrudRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {

	@Query("select a from Admin a where a.id NOT IN (select associatedId from User u where userType=?1 ) AND a.fullName  like CONCAT(?2, '%')")
	List<Admin> findByAdminName(UserType userType,String firstName);
	
	@Query("select a from Admin a where a.id NOT IN (select associatedId from User u where userType=?1 ) AND a.fullName=?2)")
	Admin findByAdminNameForValidation(UserType userType,String firstName);
	
	@Query("select a.id from Admin a where a.fullName=?1")
	long findAdminAssociatedID(String name);
	
	@Query("select a from Admin a where a.fullName=?1")
	Admin findAdminByFullName(String name);
	
	@Query("select a from Admin a order by a.id desc")
	List<Admin> findAllAdmin();
	
	@Query("select count(a) from Admin a ")
	long countAdmin();
	
	@Query("select a from Admin a where a.isRegistered=?1 and a.fullName like CONCAT(?2, '%') and a.superAdmin=?3 order by fullName desc 10")
	List<Admin> getUnregisteredAdmin(boolean registered, String name, boolean superAdmin);
	
}
