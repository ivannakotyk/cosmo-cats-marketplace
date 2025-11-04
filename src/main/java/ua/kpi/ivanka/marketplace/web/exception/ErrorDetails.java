package ua.kpi.ivanka.marketplace.web.exception;

public record ErrorDetails(
        int status,
        String error,
        String message,
        String path
) {}
