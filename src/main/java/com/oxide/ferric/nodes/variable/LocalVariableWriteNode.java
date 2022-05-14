package com.oxide.ferric.nodes.variable;

import com.oxide.ferric.nodes.RustNode;
import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild(value = "expr", type = RustNode.class)
abstract class LocalVariableWriteNode extends RustNode {


}

