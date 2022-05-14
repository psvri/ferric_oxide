package com.oxide.ferric.parser;

import com.graal.rust.RustParser;
import com.oxide.ferric.nodes.ReadLocalVariableNodeGen;
import com.oxide.ferric.nodes.RustNode;
// import com.graal.rust.nodes.binary.AdditionGen;
import com.oxide.ferric.nodes.binary.AdditionNodeGen;
import com.oxide.ferric.nodes.primitive.I32;

public class RustExpressionParser {

    public static RustNode expr2TruffleNode(RustParser.ExpressionContext expr) {
        if (expr instanceof RustParser.ExprWithoutBlockContext) {
            return exprWithoutBlock2TruffleNode(((RustParser.ExprWithoutBlockContext) expr).expressionWithoutBlock());
        } else {
            return null;
        }
    }

    public static RustNode exprWithoutBlock2TruffleNode(RustParser.ExpressionWithoutBlockContext expr) {
        if (expr instanceof RustParser.LiteralExprWithoutBlockContext) {
            return literalExprWithoutBlock2TruffleNode(((RustParser.LiteralExprWithoutBlockContext) expr).literalExpression());
        } else if (expr instanceof RustParser.AdditionExprWithoutBlockContext) {
            return additionExprWithoutBlock2TruffleNode(((RustParser.AdditionExprWithoutBlockContext) expr));
        } else if (expr instanceof RustParser.PathExprWithoutBlockContext) {
            return pathExprWithoutBlock2TruffleNode(((RustParser.PathExprWithoutBlockContext) expr));
        }else {
            return null;
        }
    }

    public static RustNode pathExprWithoutBlock2TruffleNode(RustParser.PathExprWithoutBlockContext expr) {
        var pathIdentSegment = expr.pathExpression()
                .pathInExpression()
                .pathExprSegment(0)
                .pathIdentSegment();

        if (pathIdentSegment.IDENTIFIER()!=null) {
            return ReadLocalVariableNodeGen.create(pathIdentSegment.IDENTIFIER().getText());
        } else {
            throw new RuntimeException("Invalid variable identifier");
        }
    }

    public static RustNode additionExprWithoutBlock2TruffleNode(RustParser.AdditionExprWithoutBlockContext expr) {
        return AdditionNodeGen.create(
                exprWithoutBlock2TruffleNode(expr.expressionWithoutBlock(0)),
                exprWithoutBlock2TruffleNode(expr.expressionWithoutBlock(1))
        );
    }

    public static RustNode literalExprWithoutBlock2TruffleNode(RustParser.LiteralExpressionContext expr) {
        if (expr instanceof RustParser.IntegerLiteralExprContext) {
            String i32String = ((RustParser.IntegerLiteralExprContext) expr).INTEGER_LITERAL().toString();
            i32String = i32String.replace("i32", "");
            return new I32(Integer.parseInt(i32String));
        } else {
            return null;
        }
    }
}
