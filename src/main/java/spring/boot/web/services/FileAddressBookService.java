package spring.boot.web.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.springframework.dao.DuplicateKeyException;
import spring.boot.web.entities.Address;
import spring.boot.web.entities.User;
import spring.boot.web.services.interfaces.UserAddressBook;

public class FileAddressBookService extends FileOperator implements UserAddressBook {

	@PostConstruct
	public void init() {
		readData();
	}

	@Override
	public Address addAddress(Address address) {
		boolean finded = false;
		if (address.getId() != null)
			finded = getUsers().values().stream().anyMatch(userInner -> userInner.getAddresses().stream()
					.anyMatch(addressInner -> addressInner.getId().equals(address.getId())));

		User user = getUsers().get(address.getUser().getId());
		if (finded)
			throw new DuplicateKeyException("Duplicate key exception");

		address.generateId();
		user.getAddresses().add(address);
		address.setUser(user);

		writeData();
		return address;

	}

	@Override
	public Address updateAddress(Address address) {
		address.getUser().getAddresses().parallelStream().forEach(addressInner -> {
			if (addressInner.getId() == address.getId()) {
				addressInner.update(address);
				writeData();
			}
		});
		return address;
	}

	@Override
	public void deleteAddress(Address address) {
		User user = address.getUser();
		Iterator<Address> iterator = user.getAddresses().iterator();
		while (iterator.hasNext()) {
			Address addressInner = iterator.next();
			if (address.getId() == addressInner.getId())
				;
			iterator.remove();
		}
		writeData();

	}

	@Override
	public List<Address> findAddressByUserAndFirstNameContains(User user, String firstName) {
		final String stringToFind = "(.*)" + firstName + "(.*)";
		List<Address> finded = user.getAddresses().stream()
				.filter(address -> (address.getFirstName().matches(stringToFind))).collect(Collectors.toList());
		return finded;
	}

	@Override
	public List<Address> findAddressByUserAndMiddleNameContains(User user, String middleName) {
		final String stringToFind = "(.*)" + middleName + "(.*)";
		List<Address> finded = user.getAddresses().stream()
				.filter(address -> (address.getMiddleName().matches(stringToFind))).collect(Collectors.toList());
		return finded;
	}

	@Override
	public List<Address> findAddressByUserAndMobilePhoneContains(User user, String mobilePhone) {
		final String stringToFind = "(.*)" + mobilePhone + "(.*)";
		List<Address> finded = user.getAddresses().stream()
				.filter(address -> (address.getMobilePhone().matches(stringToFind))).collect(Collectors.toList());
		return finded;
	}

	@Override
	public List<Address> findAddressByUser(User user) {
		return new ArrayList<>(getUsers().get(user.getId()).getAddresses());
	}

	@Override
	public User addUser(User user) {
		if (user.getId() != null && getUsers().containsKey(user.getId()))
			throw new DuplicateKeyException("Duplicate key exception");

		if(getUsers().values().stream().anyMatch(userInner -> userInner.getLoginName().equals(user.getLoginName())))
			throw new DuplicateKeyException("Duplicate login exception");
		
		user.generateId();
		getUsers().put(user.getId(), user);
		writeData();
		return user;
	}

	@Override
	public void deleteUser(User user) {
		getUsers().remove(user.getId());

	}

	@Override
	public User updateUser(User user) {

		User userToUpdate = getUsers().get(user.getId());
		userToUpdate.update(user);

		return userToUpdate;
	}

	@Override
	public User getUser(String login, String password) {
		if (login == null && password == null)
			throw new IllegalArgumentException("Both arguments cann't be null!");

		return getUsers().values().stream().filter(user -> ((login == null
				|| login.equals(user.getLoginName()) && (password == null || user.getPassword().equals(password)))))
				.findFirst().get();
	}

	@Override
	public User getUser(String login) {
		if (login == null)
			throw new IllegalArgumentException("Argument cann't be null!");

		return getUser(login, null);
	}

	@Override
	public User getUser(Long userID) {
		return getUsers().get(userID);
	}

	@Override
	public Collection<User> findAllUsers() {
		return getUsers().values();
	}

}
