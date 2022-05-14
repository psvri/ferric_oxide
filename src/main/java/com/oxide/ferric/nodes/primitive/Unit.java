package com.oxide.ferric.nodes.primitive;

import com.oxide.ferric.types.UnitType;
import com.oxide.ferric.nodes.RustNode;
import com.oracle.truffle.api.frame.VirtualFrame;

public class Unit extends RustNode{

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return new UnitType();
    }
}
