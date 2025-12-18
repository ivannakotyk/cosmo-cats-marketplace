package ua.kpi.ivanka.marketplace.repository.exception;

public class CosmicPersistenceException extends RuntimeException {

    public CosmicPersistenceException(String message) {
        super(message);
    }
    public CosmicPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}