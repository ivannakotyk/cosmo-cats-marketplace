package ua.kpi.ivanka.marketplace.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient ratesRestClient(
            @Value("${clients.rates.base-url}") String baseUrl,
            @Value("${clients.rates.connect-timeout-ms:1000}") int connectTimeout,
            @Value("${clients.rates.read-timeout-ms:1500}") int readTimeout) {

        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);

        return RestClient.builder()
                .requestFactory(factory)
                .baseUrl(baseUrl)
                .build();
    }
}
