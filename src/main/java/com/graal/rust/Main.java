package com.graal.rust;

import com.graal.rust.nodes.RustNode;
import com.graal.rust.nodes.RustRootNode;
import com.graal.rust.nodes.binary.AdditionNodeGen;
import com.graal.rust.nodes.primitive.I32;
import com.graal.rust.parser.RustLangParser;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        RustNode exprNode =  AdditionNodeGen.create(
                new I32(12),
                new I32(34));
        RustRootNode rootNode = new RustRootNode(exprNode);
        CallTarget callTarget = rootNode.getCallTarget();

        var result = callTarget.call();
        System.out.println(result);

        var parser = RustLangParser.parse("10i32;");

        System.out.println(parser);

        RustRootNode antlr4RootNode = new RustRootNode(parser);
        callTarget = antlr4RootNode.getCallTarget();
        result = callTarget.call();
        System.out.println(result);

        parser = RustLangParser.parse("10i32 + 20i32;");

        System.out.println(parser);

        antlr4RootNode = new RustRootNode(parser);
        callTarget = antlr4RootNode.getCallTarget();
        result = callTarget.call();
        System.out.println(result);

        parser = RustLangParser.parse("10i32 + 20i32 + 50i32;");

        System.out.println(parser);

        antlr4RootNode = new RustRootNode(parser);
        callTarget = antlr4RootNode.getCallTarget();
        result = callTarget.call();
        System.out.println(result);

        parser = RustLangParser.parse("10i32 + 20i32 + 50i32;let a = 100; 10+100;");

        System.out.println(parser);
        FrameDescriptor frameDescriptor = FrameDescriptor.newBuilder(1).build();
        /*var a = frameDescriptor.findOrAddAuxiliarySlot("sad");
        System.out.println("slot index is " + a);
        frameDescriptor.getAuxiliarySlots().forEach((x, y) -> System.out.println(x + ":" + y));
        //frameDescriptor.setSlotKind(0, FrameSlotKind.Int);*/
        antlr4RootNode = new RustRootNode(parser, frameDescriptor);
        callTarget = antlr4RootNode.getCallTarget();
        result = callTarget.call();
        System.out.println(result);

        Context context = Context.create();
        Value resultContext = context.eval("rs",
                "10i32 + 20i32 + 50i32;");
        System.out.println(resultContext);
        context.close();

        System.out.println(RuntimeMetaData.VERSION);
    }
}