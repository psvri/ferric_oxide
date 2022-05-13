package com.graal.rust.nodes.statement;

import com.graal.rust.nodes.RustNode;
import com.graal.rust.types.UnitType;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

import java.util.Arrays;

@NodeChild(value = "expr", type = RustNode.class)
public abstract class LetStatementNode extends RustNode {

    protected final int slot;

    public LetStatementNode(int slot) {
        this.slot = slot;
    }

    @Specialization
    public Object writeGeneric(VirtualFrame frame, Object exprValue) {
        frame.setObject(slot, exprValue);
        System.out.println("Frame contents are" + Arrays.toString(frame.getArguments()));
        return new UnitType();
    }

}
