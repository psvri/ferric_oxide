package com.graal.rust.nodes.variable;

import com.graal.rust.nodes.RustNode;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class LocalVariableReadNode extends RustNode {
    protected final int slot;

    protected LocalVariableReadNode(int slot) {
        this.slot = slot;
    }

    @Specialization
    public Object doObject(VirtualFrame frame) {
        return frame.getValue(slot);
    }
}
