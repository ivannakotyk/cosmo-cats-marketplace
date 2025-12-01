package ua.kpi.ivanka.marketplace.featuretoggle.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ua.kpi.ivanka.marketplace.featuretoggle.FeatureToggleService;
import ua.kpi.ivanka.marketplace.featuretoggle.annotation.FeatureToggle;
import ua.kpi.ivanka.marketplace.featuretoggle.exception.FeatureNotAvailableException;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Around("@annotation(featureToggle)")
    public Object checkFeature(ProceedingJoinPoint joinPoint,
                               FeatureToggle featureToggle) throws Throwable {

        String key = featureToggle.value().getPropertyKey();

        log.info("Checking feature toggle: '{}'", key);

        if (!featureToggleService.isEnabled(key)) {
            log.warn("Feature '{}' is DISABLED → Method '{}' blocked",
                    key, joinPoint.getSignature().getName());
            throw new FeatureNotAvailableException(key);
        }

        log.info("Feature '{}' is ENABLED → Proceeding with method '{}'",
                key, joinPoint.getSignature().getName());

        return joinPoint.proceed();
    }
}
