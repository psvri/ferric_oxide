package com.oxide.ferric;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestRsTest {

    @Test
    void testRsGuest() {
        Context context = Context.create();
        Value resultContext = context.eval("rs",
                "10i32 + 20i32 + 50i32;");

        assertEquals(resultContext.asInt(), 80);

        System.out.println(resultContext);
        context.close();
    }
}
