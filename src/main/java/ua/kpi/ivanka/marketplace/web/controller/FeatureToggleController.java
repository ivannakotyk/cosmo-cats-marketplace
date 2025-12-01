package ua.kpi.ivanka.marketplace.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.ivanka.marketplace.featuretoggle.FeatureToggleService;

@RestController
@RequestMapping("/api/v1/features")
@RequiredArgsConstructor
@Slf4j
public class FeatureToggleController {

    private final FeatureToggleService featureToggleService;

    @PostMapping("/{key}/enable")
    public ResponseEntity<String> enableFeature(@PathVariable String key) {
        log.info("ADMIN ACTION: Enabling feature toggle: '{}'", key);
        featureToggleService.enable(key);
        log.info("Feature '{}' successfully ENABLED", key);
        return ResponseEntity.ok("Feature '" + key + "' is now ENABLED");
    }

    @PostMapping("/{key}/disable")
    public ResponseEntity<String> disableFeature(@PathVariable String key) {
        log.info("ADMIN ACTION: Disabling feature toggle: '{}'", key);
        featureToggleService.disable(key);
        log.info("Feature '{}' successfully DISABLED", key);
        return ResponseEntity.ok("Feature '" + key + "' is now DISABLED");
    }
}