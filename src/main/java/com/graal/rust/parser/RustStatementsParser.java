package com.graal.rust.parser;

import com.graal.rust.RustParser;
import com.graal.rust.nodes.RustNode;
import com.graal.rust.nodes.statement.*;

import java.util.HashMap;

import static com.graal.rust.parser.RustExpressionParser.expr2TruffleNode;
import static com.graal.rust.parser.RustExpressionParser.exprWithoutBlock2TruffleNode;

public class RustStatementsParser {
    public static RustNode statements2TruffleNode(RustParser.StatementsContext stmts) {
        var varMapping = new HashMap<String, Integer>();

        var statements = stmts.statement();

        var listStatement = statements.stream().map(stmt -> {
            if (stmt instanceof RustParser.LetStmtContext) {
                return letStmt2TruffleNode((RustParser.LetStmtContext) stmt, varMapping);
            } else if (stmt instanceof RustParser.EmptyStmtContext) {
                return emptyStmt2TruffleNode((RustParser.EmptyStmtContext) stmt);
            } else if (stmt instanceof RustParser.ExprStmtContext) {
                return emprStmt2TruffleNode((RustParser.ExprStmtContext) stmt);
            } else {
                throw new RuntimeException();
            }
        }).toList();

        return new StatementsNode(listStatement.toArray(new RustNode[0]));
    }

    public static LetStatementNode letStmt2TruffleNode(RustParser.LetStmtContext stmt, HashMap<String, Integer> varMapping) {
        var var_name = stmt.letStatement().varName.getText();
        var slot = varMapping.size();
        varMapping.put(var_name, slot);
        System.out.println("let expresion is " + stmt.letStatement().rhs.getText());
        return LetStatementNodeGen.create(slot, expr2TruffleNode(stmt.letStatement().rhs));
    }

    public static EmptyStatementNode emptyStmt2TruffleNode(RustParser.EmptyStmtContext stmt) {
        return new EmptyStatementNode();
    }

    public static ExpressionStatementNode emprStmt2TruffleNode(RustParser.ExprStmtContext stmt) {
        var exprStmtContext = ((RustParser.ExprStmtContext) stmt).expressionStatement();
        if (exprStmtContext instanceof RustParser.ExprStmtWithoutBlockContext) {
            var expressionWithoutBlockContext = ((RustParser.ExprStmtWithoutBlockContext) exprStmtContext).expressionWithoutBlock();
            return new ExpressionStatementNode(exprWithoutBlock2TruffleNode(expressionWithoutBlockContext));
        } else if (exprStmtContext instanceof RustParser.ExprStmtWithBlockContext) {
            return null;
        } else {
            throw new RuntimeException();
        }
    }
}
