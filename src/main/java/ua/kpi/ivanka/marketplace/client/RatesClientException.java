package ua.kpi.ivanka.marketplace.client;

public class RatesClientException extends RuntimeException {
    public RatesClientException(String message) {
        super(message);
    }
    public RatesClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
