package ua.kpi.ivanka.marketplace.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Locale;

public class CosmicWordCheckValidator implements ConstraintValidator<CosmicWordCheck, String> {
    private static final List<String> COSMIC = List.of(
            "star","galaxy","comet","nebula","cosmo","orbit","astro","space","lunar","solar"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        String v = value.toLowerCase(Locale.ROOT);
        return COSMIC.stream().anyMatch(v::contains);
    }
}
