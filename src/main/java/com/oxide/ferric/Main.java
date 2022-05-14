package com.oxide.ferric;

import com.oxide.ferric.nodes.RustRootNode;
import com.oxide.ferric.parser.RustLangParser;
import com.oracle.truffle.api.frame.FrameDescriptor;

public class Main {
    public static void main(String[] args) {

        var builder =  FrameDescriptor.newBuilder(1);

        var parser = RustLangParser.parse("let a = 123;a + 20000;", builder);
        FrameDescriptor frameDescriptor = builder.build();
        System.out.println(parser);

        var antlr4RootNode = new RustRootNode(parser, frameDescriptor);
        var callTarget = antlr4RootNode.getCallTarget();
        var result = callTarget.call();
        System.out.println(result);
    }
}