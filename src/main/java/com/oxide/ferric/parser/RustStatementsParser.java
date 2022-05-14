package com.oxide.ferric.parser;

import com.graal.rust.RustParser;
import com.oxide.ferric.nodes.RustNode;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oxide.ferric.nodes.statement.*;

import static com.oxide.ferric.parser.RustExpressionParser.expr2TruffleNode;
import static com.oxide.ferric.parser.RustExpressionParser.exprWithoutBlock2TruffleNode;

public class RustStatementsParser {
    public static RustNode statements2TruffleNode(RustParser.StatementsContext stmts, FrameDescriptor.Builder builder) {
        var statements = stmts.statement();

        var listStatement = statements.stream().map(stmt -> {
            if (stmt instanceof RustParser.LetStmtContext) {
                return letStmt2TruffleNode((RustParser.LetStmtContext) stmt, builder);
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

    public static LetStatementNode letStmt2TruffleNode(RustParser.LetStmtContext stmt, FrameDescriptor.Builder builder) {
        var varName = stmt.letStatement().varName.getText();

        String varType;
        if (stmt.letStatement().varType == null) {
            varType = null;
        } else {
            varType = stmt.letStatement().varType.getText();
        }
        builder.addSlot(stringToFrameSlotKind(varType), varName, null);
        System.out.println("let expresion is " + stmt.letStatement().rhs.getText() + " with type " + varType);
        return LetStatementNodeGen.create(varName, expr2TruffleNode(stmt.letStatement().rhs));
    }

    public static FrameSlotKind  stringToFrameSlotKind(String varType) {
        if (varType == null) {
            return FrameSlotKind.Int;
        } else if (varType.equals("i32")) {
            return FrameSlotKind.Int;
        } else {
            throw new RuntimeException("Invalid type");
        }
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
