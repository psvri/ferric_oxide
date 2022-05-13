package com.graal.rust.parser;

import com.graal.rust.RustParser;
import com.graal.rust.nodes.RustNode;
// import com.graal.rust.nodes.binary.AdditionGen;
import com.graal.rust.nodes.binary.AdditionNodeGen;
import com.graal.rust.nodes.primitive.I32;

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
        }
        else {
            return null;
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
