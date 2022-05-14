package com.oxide.ferric.nodes.statement;

import com.oxide.ferric.nodes.RustNode;
import com.oxide.ferric.types.UnitType;
import com.oracle.truffle.api.frame.VirtualFrame;

public class EmptyStatementNode extends RustNode {
    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return new UnitType();
    }
}
