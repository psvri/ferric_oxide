package com.graal.rust.nodes;

import com.graal.rust.parser.RustLangParser;
import com.oracle.truffle.api.CallTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionEvalTest {

    @Test
    void testConsecutiveAdd() {
        RustNode parser = RustLangParser.parse("10i32 + 20i32 + 50i32;");

        RustRootNode antlr4RootNode = new RustRootNode(parser);
        CallTarget callTarget = antlr4RootNode.getCallTarget();
        Object result = callTarget.call();

        assertEquals((int)result, 80);
    }
}
