
package pl.bdygasinski.gameoflife.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatrixValidator.class)
@Documented
public @interface ValidMatrix {
    String message() default "Invalid matrix: must have non-empty rows and all rows must be of equal length";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
