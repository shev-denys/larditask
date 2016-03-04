package spring.boot.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import spring.boot.web.entities.Address;
import spring.boot.web.entities.User;

import java.lang.String;
import java.util.List;
import java.lang.Long;

@Repository
//@NoRepositoryBean
public interface AddressRepository extends CrudRepository<Address, Long> {

	List<Address> findByUserAndFirstNameContains (User user, String firstName);
	List<Address> findByUserAndMiddleNameContains(User user, String middleName);
	List<Address> findByUserAndMobilePhoneContains(User user, String middleName);
	List<Address> findByUser(User user);
}
