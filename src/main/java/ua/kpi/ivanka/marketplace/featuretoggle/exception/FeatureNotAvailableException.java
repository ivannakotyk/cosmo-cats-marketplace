package ua.kpi.ivanka.marketplace.featuretoggle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FeatureNotAvailableException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE =
            "Feature '%s' is disabled";

    public FeatureNotAvailableException(String propertyKey) {
        super(String.format(MESSAGE_TEMPLATE, propertyKey));
    }
}
