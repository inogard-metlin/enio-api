package kr.co.inogard.enio.api.commons.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { MaxByteLengthValidator.class })
public @interface MaxByteLength {
	String message() default "{enio.validation.MaxByteLength.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	int value();
}
