package com.oxide.ferric.nodes.primitive;

import com.oxide.ferric.nodes.RustNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public class I32 extends RustNode {
    public final int value;

    public I32(int value) {
        this.value = value;
    }

    @Override
    public int executeInt(VirtualFrame frame) {
        return this.value;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return this.value;
    }
}
