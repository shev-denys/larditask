package spring.boot.web.services.interfaces;

import java.util.List;
import java.util.Set;

import spring.boot.web.entities.Address;
import spring.boot.web.entities.User;

public interface AddressService {

	public Address addAddress(Address address);
	public Address updateAddress(Address address);
	public void deleteAddress(Address address);
	
	public List<Address> findAddressByUserAndFirstNameContains (User user, String firstName);
	public List<Address> findAddressByUserAndMiddleNameContains(User user, String middleName);
	public List<Address> findAddressByUserAndMobilePhoneContains(User user, String middleName);
	public List<Address> findAddressByUser(User user);
	
}
