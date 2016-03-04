package spring.boot.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import spring.boot.web.entities.User;


@Repository
//@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Long> {
//	List<User> findByLoginName(String loginName);
}
