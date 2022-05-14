package com.oxide.ferric;

import org.junit.jupiter.api.Test;

import static com.oxide.ferric.Utils.executeStatements;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionEvalTest {

    @Test
    void testConsecutiveAdd() {
        assertEquals((int)executeStatements("10i32 + 20i32 + 50i32;"), 80);
    }
}
