package com.oxide.ferric.nodes.variable;

import com.oxide.ferric.nodes.RustNode;
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
