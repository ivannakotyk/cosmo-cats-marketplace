package ua.kpi.ivanka.marketplace.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RatesClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${clients.rates.base-url:http://localhost:8089}")
    private String baseUrl;

    public Map<String, Object> getRates() {
        String url = baseUrl + "/api/v1/rates";
        log.info("Fetching cosmic rates from {}", url);

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            log.info("Received rates: {}", response.getBody());
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Failed to fetch cosmic rates from {}: {}", url, e.getMessage());
            throw new RatesClientException("Failed to fetch cosmic rates", e);
        }
    }
}
