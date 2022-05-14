package com.oxide.ferric.parser;

import com.graal.rust.RustLexer;
import com.graal.rust.RustParser;
import com.oxide.ferric.nodes.RustNode;
import com.oracle.truffle.api.frame.FrameDescriptor;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.Reader;

public class RustLangParser {

    public static RustNode parse(String program, FrameDescriptor.Builder builder) {
        return parse(CharStreams.fromString(program), builder);
    }

    public static RustNode parse(Reader program, FrameDescriptor.Builder builder) throws IOException {
        return parse(CharStreams.fromReader(program), builder);
    }

    public static RustNode parse(CharStream inputStream, FrameDescriptor.Builder builder) {

        var lexer = new RustLexer(inputStream);

        var parser = new RustParser(new CommonTokenStream(lexer));

        parser.setErrorHandler(new BailErrorStrategy());

        var stmtsContext = parser.statements();

        return RustStatementsParser.statements2TruffleNode(stmtsContext, builder);
    }
}
