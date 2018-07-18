package uni.miskolc.ips.ilona.tracking.util.validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * THIS CLASS IS NOT THREADSAFE!
 * 
 * @author Patrik / A5USL0
 *
 */
public class ValidityStatusHolder {

	private List<String> errors;

	public ValidityStatusHolder() {
		errors = new ArrayList<String>();
	}

	public boolean isValid() {
		if (errors.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void appendValidityStatusHolder(ValidityStatusHolder statusHolder) {
		if (statusHolder == null) {
			return;
		}

		List<String> externalErrors = statusHolder.getErrors();
		if (externalErrors == null) {
			return;
		}

		for (String error : externalErrors) {
			errors.add(error);
		}
	}

	public void addValidityError(String validityError) {
		if (validityError == null) {
			return;
		}
		errors.add(validityError);
	}

	public void addValidityErrors(Collection<String> validityErrors) {
		if (validityErrors == null) {
			return;
		}
		for (String error : validityErrors) {
			if (error != null) {
				errors.add(error);
			}
		}
	}

	public String getErrorsAsString() {
		StringBuilder builder = new StringBuilder();
		for (String error : errors) {
			builder.append(error + " ");
		}
		return builder.toString();
	}

	public List<String> getErrors() {
		return new ArrayList<>(errors);
	}
}
