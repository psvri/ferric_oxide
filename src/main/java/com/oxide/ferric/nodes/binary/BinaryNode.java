package com.oxide.ferric.nodes.binary;

import com.oxide.ferric.nodes.RustNode;
import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild(value = "left",  type = RustNode.class)
@NodeChild(value = "right", type = RustNode.class)
public abstract class BinaryNode extends RustNode {
}
