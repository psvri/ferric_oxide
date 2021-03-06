package com.oxide.ferric.nodes.statement;

import com.oxide.ferric.nodes.RustNode;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

public class StatementsNode extends RustNode {

    @Children
    private final RustNode[] expressions;

    public StatementsNode(RustNode[] expressions) {
        this.expressions = expressions;
    }

    @ExplodeLoop
    public Object executeGeneric(VirtualFrame frame) {
        for (int i = 0; i < this.expressions.length - 1; i++) {
            System.out.println("expression is " + this.expressions[i].getSourceSection() + " value is " + this.expressions[i].executeGeneric(frame));
        }

        return this.expressions[this.expressions.length - 1].executeGeneric(frame);
    }
}
