package com.oxide.ferric.nodes;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class ReadLocalVariableNode extends RustNode{

    private final String varName;

    protected ReadLocalVariableNode(String varName) {
        this.varName = varName;
    }

    @Specialization
    public Object doGeneric(VirtualFrame frame) {
        var descriptor = frame.getFrameDescriptor();
        var slot = descriptor.findOrAddAuxiliarySlot(varName);
        try {
            return frame.getObject(slot);
        } catch (FrameSlotTypeException exception) {
            throw new RuntimeException("could not find local variable");
        }
    }

    //todo add remaining specializations
}
