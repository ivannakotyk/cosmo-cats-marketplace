package ua.kpi.ivanka.marketplace.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CosmicWordCheckValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmicWordCheck {
    String message() default "Name must contain a cosmic word (star, galaxy, comet, nebula, cosmo, orbit)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
