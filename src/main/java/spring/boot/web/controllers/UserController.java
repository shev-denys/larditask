package spring.boot.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.boot.web.entities.User;
import spring.boot.web.services.AddressBookValidator;
import spring.boot.web.services.interfaces.UserAddressBook;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserAddressBook abService;

	@Autowired
	private AddressBookValidator validator;
	
	@RequestMapping("")
	@ResponseBody
	public ResponseEntity<List<User>> finaAll() {

		List<User> users = new ArrayList<User>();
		abService.findAllUsers().forEach(user -> users.add(user));
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/create",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> create(@RequestParam String login,@RequestParam String fio,@RequestParam String password) {
		User user = null;
		try {
			user = new User();
			user.setFio(fio);
			user.setLoginName(login);
			user.setPassword(password);
			abService.addUser(user);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Error creating the user: " + ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("User succesfully created! (id = " + user.getId() + ")", HttpStatus.CREATED);
	}

	@RequestMapping(value ="/delete/{id}",  method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> delete(@PathVariable long id) {
		try {
			User user = new User();
			user.setId(id);
			abService.deleteUser(user);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Error deleting the user:" + ex.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("User succesfully deleted!",HttpStatus.OK) ;
	}

	@RequestMapping("/get/{login}")
	@ResponseBody
	public ResponseEntity<String> getByEmail(@PathVariable String login) {
		String userId;
		try {
			User user = abService.getUser(login);
			userId = String.valueOf(user.getId());
		} catch (Exception ex) {
			return new ResponseEntity<String>("User not found",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("The user id is: " + userId,HttpStatus.OK);
	}

	
} 
