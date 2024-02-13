package in.kunal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.kunal.entity.Customer;
@Repository
public interface CustomerRepo   extends JpaRepository<Customer, Integer> {
	
	public Customer findBycname(String cname);

}
