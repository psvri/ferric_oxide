package com.oxide.ferric;

import com.oxide.ferric.nodes.RustRootNode;
import com.oxide.ferric.nodes.RustNode;
import com.oxide.ferric.parser.RustLangParser;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;

public class Utils {

    public static Object executeStatements(String statements) {
        var builder =  FrameDescriptor.newBuilder(1);
        RustNode parser = RustLangParser.parse(statements, builder);

        RustRootNode antlr4RootNode = new RustRootNode(parser, builder.build());
        CallTarget callTarget = antlr4RootNode.getCallTarget();
        return callTarget.call();
    }
}
