package com.oxide.ferric;

import com.oxide.ferric.nodes.RustRootNode;
import com.oxide.ferric.parser.RustLangParser;
import com.oracle.truffle.api.frame.FrameDescriptor;

import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {


        var builder =  FrameDescriptor.newBuilder();

        var reader = new BufferedReader(new FileReader(args[0]));

        var parser = RustLangParser.parse(reader, builder);
        FrameDescriptor frameDescriptor = builder.build();
        System.out.println(parser);

        var antlr4RootNode = new RustRootNode(parser, frameDescriptor);
        var callTarget = antlr4RootNode.getCallTarget();
        var result = callTarget.call();
        System.out.println(result);
    }
}