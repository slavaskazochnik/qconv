package by.parfen.disptaxi.services.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.parfen.disptaxi.services.EmailValidator;

public class EmailValidatorImpl implements EmailValidator {

	// mask?
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	@Override
	public boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
		// TODO matches
	}

}
