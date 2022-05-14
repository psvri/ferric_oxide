package com.oxide.ferric.nodes.statement;

import com.oxide.ferric.nodes.RustNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public class ExpressionStatementNode extends RustNode {
    @Child
    private RustNode expr;

    public ExpressionStatementNode(RustNode expr) {
        this.expr = expr;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return this.expr.executeGeneric(frame);
    }
}
