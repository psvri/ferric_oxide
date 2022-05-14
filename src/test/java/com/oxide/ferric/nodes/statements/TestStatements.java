package com.oxide.ferric.nodes.statements;

import com.oxide.ferric.types.UnitType;
import org.junit.jupiter.api.Test;

import static com.oxide.ferric.Utils.executeStatements;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStatements {

    @Test
    void testSimpleStatements() {
        assertEquals((int) executeStatements("10i32 + 20i32 + 50i32;"), 80);
    }

    @Test
    void testMultipleStatements() {
        assertEquals((int) executeStatements("10i32 + 20i32 + 50i32;let a = 100; 10+100;"), 110);
    }

    @Test
    void testMultipleLetStatements() {
        assertTrue(new UnitType().equals(executeStatements("let b = 200;let a = 100;")));
    }

    @Test
    void testReadVariableStatements() {
        assertEquals((int) executeStatements("10i32 + 20i32 + 50i32;let a = 2000;a;"), 2000);
    }

    @Test
    void testMultipleReadVariableStatements() {
        assertEquals((int) executeStatements("let a: i32 = 100;let b = 200i32;let c= 300; a + b + c;"), 600);
    }
}
