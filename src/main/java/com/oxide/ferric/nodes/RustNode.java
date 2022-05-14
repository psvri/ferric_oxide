package com.oxide.ferric.nodes;

import com.oxide.ferric.types.RustTypesGen;
import com.oxide.ferric.types.UnitType;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public abstract class RustNode extends Node {
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        return RustTypesGen.expectInteger(executeGeneric(frame));
    };

    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        return RustTypesGen.expectDouble(executeGeneric(frame));
    }

    public UnitType executeUnit(VirtualFrame frame) throws UnexpectedResultException {
        return RustTypesGen.expectUnitType(executeGeneric(frame));
    }

    public abstract Object executeGeneric(VirtualFrame frame);
}
