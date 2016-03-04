package spring.boot.web.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import spring.boot.web.entities.Address;
import spring.boot.web.entities.User;

@Service
public class AddressBookValidator implements Validator {

	// Address
	private static final int MIN_FIRST_NAME_LENGTH = 4;
	private static final int MIN_MIDDLE_NAME_LENGTH = 4;
	private static final int MIN_LAST_NAME_LENGTH = 4;
	private static final String PHONE_PATTERN = "^((8|\\+38)-?)?(\\(?044\\)?)?-?\\d{3}-?\\d{2}-?\\d{2}$";
	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	// Users
	private static final String LOGIN_PATTERN = "^(?!.*[_\\W]).{3,}$";
	private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d).{5,}$";
	private static final int MIN_FIO_LENGTH = 5;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz) || Address.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		if (target instanceof User)
			validate((User) (target), errors);
		if (target instanceof Address)
			validate((Address) target, errors);
	}

	public void validate(User user, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginName", "Empty login");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Empty password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fio", "Empty user name");

		if (!user.getLoginName().matches(LOGIN_PATTERN)) {
			errors.rejectValue("loginname", "Login pattern error");
		}

		if (!user.getPassword().matches(PASSWORD_PATTERN)) {
			errors.rejectValue("password", "Password pattern error");
		}

		if (user.getFio().length() < MIN_FIO_LENGTH) {
			errors.rejectValue("fio", "Fio length error");
		}
	}

	public void validate(Address address, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Empty firstName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "middleName", "Empty middleName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Empty lastName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobilePhone", "Empty mobilePhone");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Empty firstName");

		if (address.getFirstName().length() < MIN_FIRST_NAME_LENGTH) {
			errors.rejectValue("firstName", "firstName length error");
		}

		if (address.getMiddleName().length() < MIN_MIDDLE_NAME_LENGTH) {
			errors.rejectValue("middleName", "middleName length error");
		}

		if (address.getLastName().length() < MIN_LAST_NAME_LENGTH) {
			errors.rejectValue("firstName", "lastName length error");
		}

		if (!address.getEmail().matches(EMAIL_PATTERN)) {
			errors.rejectValue("email", "email pattern error");
		}

		if (!address.getMobilePhone().matches(PHONE_PATTERN)) {
			errors.rejectValue("mobilePhone", "Mobile phone pattern error");
		}
	}
}
