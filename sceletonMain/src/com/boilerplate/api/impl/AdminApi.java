package com.boilerplate.api.impl;

import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boilerplate.api.IAdminApi;
import com.boilerplate.api.IUserApi;
import com.boilerplate.entity.Admin;
import com.boilerplate.entity.User;
import com.boilerplate.mail.SendMail;
import com.boilerplate.model.AdminDTO;
import com.boilerplate.model.PagablePage;
import com.boilerplate.model.Status;
import com.boilerplate.model.UserDTO;
import com.boilerplate.model.UserType;
import com.boilerplate.repositories.AdminRepository;
import com.boilerplate.repositories.CityRepository;
import com.boilerplate.repositories.UserRepository;
import com.boilerplate.util.BugMail;
import com.boilerplate.util.ClientException;
import com.boilerplate.util.ConvertUtil;
import com.boilerplate.util.PageInfo;

@Service
public class AdminApi implements IAdminApi {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CityRepository cityRepository;



	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SendMail sendMail;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserApi userApi;

	@Override
	public AdminDTO saveAdmin(AdminDTO adminDTO) {

		Admin admin = new Admin();

		admin.setStatus(Status.Active);
		admin.setFirstName(adminDTO.getFirstName());
		admin.setLastName(adminDTO.getLastName());
		admin.setFullName(adminDTO.getFirstName() + " " + adminDTO.getLastName());
		admin.setCity(cityRepository.findOne(adminDTO.getCityId()));
		admin.setAddress(adminDTO.getAddress());
		admin.setMobileNo(adminDTO.getMobileNo());

		/* admin.setTimeZone(adminDTO.getTimeZone()); */
		admin.setSuperAdmin(false);

		admin = adminRepository.save(admin);

		UserDTO userDto = new UserDTO();

		userDto.setUsername(adminDTO.getUsername());
		userDto.setPassword(adminDTO.getPassword());
		userDto.setUserType(UserType.ADMIN);
		userDto.setEmailVerification(false);
		userDto.setObjectUserId(admin.getId());
		userDto.setUserTemplateId(Long.parseLong(adminDTO.getUserTemplateId()));

		userDto = userApi.saveUser(userDto);
		
		try {
			sendMail.sendMail(adminDTO.getUsername(),
					"To Activate your email click the link"
							+ "\n<a href='http://192.168.1.254:8080/email/activate?vcode=" + "123"
							+ "'>Activate</a>");
		} catch (MessagingException e) {
			BugMail.Bugmail(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ConvertUtil.convertAdmin(admin);

	}

	@Override
	public List<AdminDTO> getUnregisteredAdmin(String userObject) {
		List<Admin> adminList = adminRepository.getUnregisteredAdmin(false, userObject, false);
		return ConvertUtil.convertAdminToDTO(adminList);
	}

	@Override
	public void registerAdmin(long id) {
		Admin admin = adminRepository.findOne(id);
		admin.setRegistered(true);
		adminRepository.save(admin);

	}

	@Override
	public List<AdminDTO> findAllSuperAdmin() {
		List<Admin> adminList = ConvertUtil.convertIterableToList(adminRepository.findAllAdmin());
		return ConvertUtil.convertAdminToDTO(adminList);

	}

	
	@Override
	public AdminDTO getAdminWithId(long id) {
		return ConvertUtil.convertAdmin(adminRepository.findOne(id));
	}

	@Override
	public AdminDTO editAdmin(AdminDTO adminDTO) {

		Admin admin = adminRepository.findOne(adminDTO.getId());

		admin.setFirstName(adminDTO.getFirstName());
		admin.setLastName(adminDTO.getLastName());
		admin.setFullName(adminDTO.getFirstName() + " " + adminDTO.getLastName());
		admin.setCity(cityRepository.findOne(adminDTO.getCityId()));
		admin.setAddress(adminDTO.getAddress());
		admin.setMobileNo(adminDTO.getMobileNo());
		admin = adminRepository.save(admin);

		User user = userRepository.findUserByAssociatedIdAndUserType(admin.getId(), UserType.ADMIN);
				return ConvertUtil.convertAdmin(admin);
	}

	@Override
	public List<AdminDTO> getadminByName(String admin) {
		// TODO Auto-generated method stub
		List<Admin> copy = ConvertUtil.convertIterableToList(adminRepository.findByAdminName(UserType.ADMIN, admin));
		return ConvertUtil.convertAdminToDTO(copy);
	}

		@Override
	public AdminDTO editAdminFromAccountdetail(Admin admin) {
		admin = adminRepository.save(admin);
		return ConvertUtil.convertAdmin(admin);
	}

	@Override
	public AdminDTO getAdminForEdit(long id) {
		AdminDTO adminDto = ConvertUtil.convertAdmin(adminRepository.findOne(id));

		User user = userRepository.findUserByAssociatedIdAndUserType(adminDto.getId(), UserType.ADMIN);
		
		return adminDto;
	}

	@Override
	public PagablePage getAllAdminPagable(int currentpage) {

		PagablePage pageble = new PagablePage();
		long totalList = adminRepository.countAdmin();
		int totalpage = (int) Math.ceil(totalList / PageInfo.pageList);
		if (currentpage > totalpage || currentpage == 0) {
			currentpage = 1;
		}

		int starting = ((currentpage * (int) PageInfo.pageList) - (int) PageInfo.pageList);
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);
		Query selectQuery = session.createQuery("select a from Admin a order by a.id desc");
		selectQuery.setFirstResult(starting);
		selectQuery.setMaxResults((int) PageInfo.pageList);
		List<Admin> admin = selectQuery.list();
		System.out.println(admin.size());
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
		pageble.setCurrentPage(currentpage);
		pageble.setObject(ConvertUtil.convertAdminToDTO(admin));
		pageble.setPageList(pagesnumbers);
		pageble.setLastpage(totalpage);

		return pageble;

	}

	@Override
	public void transferByAdmin(String disUSer, double amount) throws ClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adminChangePass(long id, UserType usertype) {
		// TODO Auto-generated method stub
		
	}

}
