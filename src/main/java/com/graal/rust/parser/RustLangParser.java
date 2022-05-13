package com.graal.rust.parser;

import com.graal.rust.RustLexer;
import com.graal.rust.RustParser;
import com.graal.rust.nodes.RustNode;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.Reader;

import static com.graal.rust.parser.RustExpressionParser.expr2TruffleNode;
import static com.graal.rust.parser.RustStatementsParser.statements2TruffleNode;

public class RustLangParser {

    public static RustNode parse(String program) {
        return parse(CharStreams.fromString(program));
    }

    public static RustNode parse(Reader program) throws IOException {
        return parse(CharStreams.fromReader(program));
    }

    public static RustNode parse(CharStream inputStream) {

        var lexer = new RustLexer(inputStream);

        var parser = new RustParser(new CommonTokenStream(lexer));

        parser.setErrorHandler(new BailErrorStrategy());

        var stmtsContext = parser.statements();

        return statements2TruffleNode(stmtsContext);
    }
}
