package spring.boot.web.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.boot.web.entities.Address;
import spring.boot.web.entities.User;
import spring.boot.web.services.AddressBookValidator;
import spring.boot.web.services.interfaces.UserAddressBook;

@Controller
@RequestMapping("/addresses")
public class AddressController {

	@Autowired
	private UserAddressBook abService;

	
	@Autowired
	private AddressBookValidator validator;
	
	
	void initData() {

		if (getCurrentUser().getAddresses() == null) {
			getCurrentUser().setAddresses(new HashSet<Address>());

			Address address = new Address("First name", "MiddleName", "Last name", "0123456", "0123456", "some address",
					"asd@asd");
			address.setUser(getCurrentUser());
			abService.addAddress(address);

			address = new Address("First name1", "MiddleName1", "Last name1", "01234567", "01234567", "some address1",
					"asd1@asd");
			address.setUser(getCurrentUser());
			abService.addAddress(address);
		}
	}

	@RequestMapping("")
	@ResponseBody
	public ResponseEntity<List<Address>> finaAll() {
		initData();
		List<Address> addresses = abService.findAddressByUser(getCurrentUser());
		// if (addresses == null)
		// initData();

		// addresses = abService.findAddressByUser(getCurrentUser());

		return new ResponseEntity<List<Address>>(addresses, HttpStatus.OK);
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public ResponseEntity<Address> add(@RequestBody Address address) {

		return new ResponseEntity<Address>(abService.addAddress(address), HttpStatus.CREATED);

	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public ResponseEntity<Address> update(@RequestBody Address address) {
		return new ResponseEntity<Address>(abService.updateAddress(address), HttpStatus.OK);
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public ResponseEntity<Address> delete(@RequestBody Address address) {
		abService.deleteAddress(address);
		return new ResponseEntity<Address>(HttpStatus.OK);
	}

	@RequestMapping("get_by_name")
	@ResponseBody
	public ResponseEntity<List<Address>> getByName(@RequestParam String name) {

		return new ResponseEntity<List<Address>>(
				abService.findAddressByUserAndFirstNameContains(getCurrentUser(), name), HttpStatus.OK);

	}

	@RequestMapping("get_by_lastname")
	@ResponseBody
	public ResponseEntity<List<Address>> getByMiddleName(@RequestParam String middleName) {
		return new ResponseEntity<List<Address>>(
				abService.findAddressByUserAndMiddleNameContains(getCurrentUser(), middleName), HttpStatus.OK);
	}

	@RequestMapping("get_by_phone")
	@ResponseBody
	public ResponseEntity<List<Address>> getByPhone(@RequestParam String phone) {
		return new ResponseEntity<List<Address>>(
				abService.findAddressByUserAndMobilePhoneContains(getCurrentUser(), phone), HttpStatus.OK);
	}

	private User getCurrentUser() {
		// Authentication authentication =
		// SecurityContextHolder.getContext().getAuthentication();
		// String name = authentication.getName();
		return abService.getUser(1L);
	}
}
