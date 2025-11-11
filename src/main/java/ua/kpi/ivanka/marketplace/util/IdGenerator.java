package ua.kpi.ivanka.marketplace.util;

import java.util.UUID;

public final class IdGenerator {
    private IdGenerator() {}

    public static UUID generate() {
        return UUID.randomUUID();
    }
}
