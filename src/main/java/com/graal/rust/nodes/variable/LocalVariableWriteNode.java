package com.graal.rust.nodes.variable;

import com.graal.rust.nodes.RustNode;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeChild(value = "expr", type = RustNode.class)
abstract class LocalVariableWriteNode extends RustNode {


}

