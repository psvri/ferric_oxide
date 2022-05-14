package com.oxide.ferric.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class RustRootNode extends RootNode  {
    @SuppressWarnings("FieldMayBeFinal")
    @Child
    private RustNode exprNode;

    public RustRootNode(RustNode exprNode) {
        this(exprNode, null);
    }

    public RustRootNode(RustNode exprNode, FrameDescriptor frameDescriptor) {
        super(null, frameDescriptor);
        this.exprNode = exprNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        System.out.println(frame.getFrameDescriptor().getNumberOfSlots());
        return this.exprNode.executeGeneric(frame);
    }
}
