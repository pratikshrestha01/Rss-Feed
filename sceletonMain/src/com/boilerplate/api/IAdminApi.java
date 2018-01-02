package com.boilerplate.api;

import java.util.List;

import com.boilerplate.entity.Admin;
import com.boilerplate.model.AdminDTO;
import com.boilerplate.model.PagablePage;
import com.boilerplate.model.UserType;
import com.boilerplate.util.ClientException;

public interface IAdminApi {

	AdminDTO saveAdmin(AdminDTO adminDTO);

	AdminDTO editAdmin(AdminDTO adminDTO);

	AdminDTO getAdminWithId(long id);
	
	AdminDTO getAdminForEdit(long id);

	List<AdminDTO> findAllSuperAdmin();

	List<AdminDTO> getadminByName(String admin);

	void registerAdmin(long id);

	List<AdminDTO> getUnregisteredAdmin(String userObject);

	void transferByAdmin(String disUSer, double amount) throws ClientException;

	public AdminDTO editAdminFromAccountdetail(Admin admin);


	
	PagablePage getAllAdminPagable(int currentpage);

	void adminChangePass(long id, UserType usertype);

}
