package kr.co.inogard.enio.api.commons.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.codec.Charsets;

public class MaxByteLengthValidator implements ConstraintValidator<MaxByteLength, String> {
	private int max;

	public void initialize(MaxByteLength constraintAnnotation) {
		this.max = constraintAnnotation.value();
	}

	public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
		return object == null || object.getBytes(Charsets.UTF_8).length <= this.max;
	}
}