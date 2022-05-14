package com.oxide.ferric.nodes.statement;

import com.oxide.ferric.types.UnitType;
import com.oxide.ferric.nodes.RustNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeChild(value = "expr", type = RustNode.class)
public abstract class LetStatementNode extends RustNode {

    private final String varName;

    public LetStatementNode(String varName) {
        this.varName = varName;
    }

    @Specialization
    public Object writeGeneric(VirtualFrame frame, Object exprValue) {
        var descriptor = frame.getFrameDescriptor();
        var slot = descriptor.findOrAddAuxiliarySlot(this.varName);
        frame.setObject(slot, exprValue);

        return new UnitType();
    }


    //todo add more specializations
}
