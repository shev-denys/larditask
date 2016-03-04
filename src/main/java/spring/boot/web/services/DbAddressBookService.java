package spring.boot.web.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.boot.web.configuration.DataSourceConfig;
import spring.boot.web.entities.Address;
import spring.boot.web.entities.User;
import spring.boot.web.repositories.AddressRepository;
import spring.boot.web.repositories.UserRepository;
import spring.boot.web.services.interfaces.AddressService;
import spring.boot.web.services.interfaces.UserAddressBook;
import spring.boot.web.services.interfaces.UserService;

@Transactional
public class DbAddressBookService implements UserAddressBook {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Collection<User> findAllUsers() {
		Set<User> users = new HashSet<>();
		userRepository.findAll().forEach(user -> users.add(user));
		
		return users;
		
	}

	@Override
	public User addUser(User user) {
		if (user.getId() != null)
			return null;
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Override
	public User updateUser(User user) {
		User userToUpdate = getUser(user.getId());
		if (userToUpdate == null) {
			return null;
		}
		userToUpdate.setAddresses(user.getAddresses());
		userToUpdate.setLoginName(user.getLoginName());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setFio(user.getFio());

		return userRepository.save(userToUpdate);
	}

	@Override
	public User getUser(String login, String password) {
		// List<User> usersFinded = userRepository.findByLoginName(login);
		// for (User user : usersFinded)
		// if (user.getPassword().equals(password))
		// return user;
		return null;
	}

	@Override
	public User getUser(String login) {
		// List<User> usersFinded = userRepository.findByLoginName(login);
		// if (usersFinded.size() > 0)
		// return usersFinded.get(0);
		return null;
	}

	@Override
	public User getUser(Long userID) {
		return userRepository.findOne(userID);
	}

	@Override
	public Address addAddress(Address address) {
		if (address.getId() != null)
			return null;
		return addressRepository.save(address);
	}

	@Override
	public Address updateAddress(Address address) {

		Address addressToUpdate = addressRepository.findOne(address.getId());
		if (addressToUpdate == null) {
			return null;
		}

		addressToUpdate.setEmail(address.getEmail());
		addressToUpdate.setFirstName(address.getFirstName());
		addressToUpdate.setMiddleName(address.getMiddleName());
		addressToUpdate.setHomeAddress(address.getHomeAddress());
		addressToUpdate.setLastName(address.getLastName());
		addressToUpdate.setMobilePhone(address.getMobilePhone());
		// addressToUpdate.setUserId(address.getUserId());

		return addressRepository.save(addressToUpdate);
	}

	@Override
	public void deleteAddress(Address address) {
		addressRepository.delete(address);
	}

	@Override
	public List<Address> findAddressByUserAndFirstNameContains(User user, String firstName) {
		return addressRepository.findByUserAndFirstNameContains(user, firstName);
	}

	@Override
	public List<Address> findAddressByUserAndMiddleNameContains(User user, String middleName) {
		return addressRepository.findByUserAndMiddleNameContains(user, middleName);
	}

	@Override
	public List<Address> findAddressByUserAndMobilePhoneContains(User user, String middleName) {
		return addressRepository.findByUserAndMobilePhoneContains(user, middleName);
	}

	@Override
	public List<Address> findAddressByUser(User user) {
		return addressRepository.findByUser(user);
	}

}
