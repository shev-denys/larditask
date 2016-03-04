package spring.boot.web.entities;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class User implements Serializable {
	
	@Transient
	private transient static final AtomicLong count = new AtomicLong(0L);
	
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;
	@Column(unique = true, name = "login")
	private String loginName;
	private String password;
	private String fio;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Address> addresses;

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public User() {
	}

	public User(User user) {
		super();
		this.loginName = user.getLoginName();
		this.password = user.getPassword();
		this.fio = user.getFio();
		this.addresses = user.getAddresses();
	}

	public static void setCount(Long num){
		count.set(num);
	}
	
	public static Long getCount()
	{
		return count.get();
	}
	
	public Long generateId() {
		setId(count.incrementAndGet());
		return getId();
	}

	public void update(User user) {
		this.setFio(user.getFio());
		this.setLoginName(user.getLoginName());
		this.setPassword(user.getPassword());
	}
	
}
