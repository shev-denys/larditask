package spring.boot.web.services.interfaces;

import java.util.Collection;
import java.util.Set;

import spring.boot.web.entities.Address;
import spring.boot.web.entities.User;

public interface UserService{
	
	public User addUser(User user);
	public void deleteUser(User user);
	public User updateUser(User user);
	public User getUser(String login, String password);
	public User getUser(String login);
	public User getUser(Long userID);
	public Collection<User> findAllUsers();
}
