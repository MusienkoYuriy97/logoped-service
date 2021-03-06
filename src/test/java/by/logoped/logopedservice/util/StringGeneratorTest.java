package by.logoped.logopedservice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringGeneratorTest {
    private static final int LENGTH = 10;

    @Test
    void generate() {
        //generate
        String generate = StringGenerator.generate(LENGTH);
        //assert
        assertTrue(generate.matches("^[a-zA-Z0-9]*$")
                && (generate.length() == LENGTH));
    }
}