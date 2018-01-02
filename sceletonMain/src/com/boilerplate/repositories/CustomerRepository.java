package com.boilerplate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.boilerplate.entity.Customer;
import com.boilerplate.model.UserType;

public interface CustomerRepository extends CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

	@Query("select c from Customer c where c.firstName=?1")
	Customer findByCustomer(String firstName);	
	
	@Query("select c from Customer c where c.id=?1")
	Customer findById(Long id);	
	
	@Query("select c from Customer c order by c.firstName")
	List<Customer> findCustomer();
	
	@Query("select c from Customer c order by c.id desc")
	List<Customer> getAllCustomer();
	
	@Query("select c from Customer c where c.id not in (select u.associatedId from User u where u.userType=?1)")
	List<Customer> getAllUnregisteredcustoemr(UserType userType);
	
	@Query("select c from Customer c where c.isRegistered =?1 and c.fullName  like CONCAT(?2, '%')")
	List<Customer> getUnregisteredcustoemr(boolean isregistered, String objectName);
	
	
	@Query("select c from Customer c where c.mobileNo=?1")
	Customer getCustomerByMobileNo(String mobileNo);

	@Query("select c from Customer c where c.parentId=?1")
	List<Customer> getCustomerByPointId(long id);
	
}
	