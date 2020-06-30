package com.coupon.db;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.coupon.beans.Company;
import com.coupon.repository.CompaniesRepository;

@Repository // like @Component that "speaks" with the DB
public class CompaniesDBDAO {
	
	@Autowired
	private CompaniesRepository repo;
	
	// Company exists
	public Company isCompanyExist(String email,String password) {
		return repo.findCompanyByEmailAndPassword(email, password);
	}
	
	// Create a Company
	public void addCompany(Company company) {
		repo.save(company);
	}
	
	// Update a Company
	public void updateCompany(Company company) {
		if(repo.existsById(company.getCompanyID()))
			repo.save(company);
	}
	
	// Delete a Company
	public void deleteCompany(long companyId) {
		repo.deleteById(companyId);
	}
	
	// Show a Company
	public Company getOneCompany(long companyId){
		Optional<Company> opt = repo.findById(companyId);
		if(opt.isPresent())
			return opt.get();
		else
			return null;
	}
	
	// Show all Companies
	public List<Company> getAllCompanies(){
		return repo.findAll();
	}
	
}