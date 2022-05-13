package com.graal.rust.nodes.primitive;

import com.graal.rust.nodes.RustNode;
import com.graal.rust.types.UnitType;
import com.oracle.truffle.api.frame.VirtualFrame;

public class Unit extends RustNode{

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return new UnitType();
    }
}
