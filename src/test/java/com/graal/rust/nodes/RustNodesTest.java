package com.graal.rust.nodes;


import com.graal.rust.nodes.binary.AdditionNodeGen;
import com.graal.rust.nodes.primitive.I32;
import com.oracle.truffle.api.CallTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RustNodesTest {

    @Test
    public void intAdd() {
        RustNode exprNode =  AdditionNodeGen.create(
                new I32(12),
                new I32(34));
        RustRootNode rootNode = new RustRootNode(exprNode);
        CallTarget callTarget = rootNode.getCallTarget();

        var result = callTarget.call();

        assertEquals(46, result);
    }
}
