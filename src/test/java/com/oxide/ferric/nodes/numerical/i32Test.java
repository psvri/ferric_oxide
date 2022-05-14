package com.oxide.ferric.nodes.numerical;

import org.junit.jupiter.api.Test;

import static com.oxide.ferric.Utils.executeStatements;
import static org.junit.jupiter.api.Assertions.*;

class i32Test {
    @Test
    void testLiteral() {
        assertEquals((int) executeStatements("10i32;"), 10);
    }
}