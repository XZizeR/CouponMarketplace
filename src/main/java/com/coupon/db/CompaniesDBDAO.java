package com.coupon.db;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.coupon.beans.Company;
import com.coupon.repository.CompaniesRepository;

@Repository
public class CompaniesDBDAO {
	
	@Autowired
	private CompaniesRepository repo;
	
//	EXIST - COMPANY
	public Company isCompanyExist(String email,String password) {
		return repo.findCompanyByEmailAndPassword(email, password);
	}
	
//	CREATE COMPANY
	public void addCompany(Company company) {
		repo.save(company);
	}
	
//	UPDATE COMPANY
	public void updateCompany(Company company) {
		if(repo.existsById(company.getCompanyID()))
			repo.save(company);
	}

//	DELETE COMPANY
	public void deleteCompany(long companyId) {
		repo.deleteById(companyId);
	}
	
//	GET-ONE COMAPNY
	public Company getOneCompany(long companyId){
		Optional<Company> opt = repo.findById(companyId);
		if(opt.isPresent())
			return opt.get();
		else
			return null;
	}

//	GET-ALL COMPANOES
	public List<Company> getAllCompanies(){
		return repo.findAll();
	}
	
}