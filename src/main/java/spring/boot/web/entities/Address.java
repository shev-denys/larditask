package spring.boot.web.entities;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "addresses")
public class Address implements Serializable {
	
	@Transient
	private transient static final AtomicLong count = new AtomicLong(0L); 
	
	@Id
	@GeneratedValue
	@Column(name = "address_id")
	private Long id;

	@JoinColumn(name = "user_id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;

	private String firstName;
	private String middleName;
	private String lastName;
	private String homePhone;
	private String mobilePhone;
	private String homeAddress;
	private String email;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address(String firstName, String middleName, String lastName, String homePhone, String mobilePhone,
			String homeAddress, String email) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.homePhone = homePhone;
		this.mobilePhone = mobilePhone;
		this.homeAddress = homeAddress;
		this.email = email;
	}

	public Address() {

	}

	@Override
	public String toString() {
		return "Address [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", homePhone=" + homePhone + ", mobilePhone=" + mobilePhone + ", homeAddress=" + homeAddress
				+ ", email=" + email + "]";
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

	public void update(Address address) {
		this.firstName = address.firstName;
		this.middleName = address.middleName;
		this.lastName = address.lastName;
		this.homePhone = address.homePhone;
		this.mobilePhone = address.mobilePhone;
		this.homeAddress = address.homeAddress;
		this.email = address.email;
		
	}

}
