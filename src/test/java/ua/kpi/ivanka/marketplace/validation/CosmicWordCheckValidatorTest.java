package ua.kpi.ivanka.marketplace.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CosmicWordCheckValidatorTest {

    static ValidatorFactory factory;
    static Validator validator;

    static class Dummy {
        @NotNull
        @CosmicWordCheck
        public String name;
        Dummy(String name) { this.name = name; }
    }

    @BeforeAll
    static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void cleanup() {
        factory.close();
    }

    @Test
    void acceptsCosmicName() {
        var v = new Dummy("galaxy yarn");
        assertTrue(validator.validate(v).isEmpty());
    }

    @Test
    void rejectsNonCosmicName() {
        var v = new Dummy("regular yarn");
        assertFalse(validator.validate(v).isEmpty());
    }
}
