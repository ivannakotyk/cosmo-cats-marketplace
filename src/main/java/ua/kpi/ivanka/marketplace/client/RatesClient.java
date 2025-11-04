package ua.kpi.ivanka.marketplace.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RatesClient {

    private final RestClient ratesRestClient;

    public Map<String, Object> getRates() {
        try {
            log.info("Fetching cosmic rates...");
            return ratesRestClient
                    .get()
                    .uri("/api/v1/rates")
                    .retrieve()
                    .body(Map.class);
        } catch (RestClientException e) {
            log.error("Failed to fetch cosmic rates: {}", e.getMessage());
            throw new RatesClientException("Failed to fetch cosmic rates", e);
        }
    }
}
