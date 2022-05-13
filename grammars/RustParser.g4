
parser grammar RustParser;

@header {
    package com.graal.rust;
}

options
{
   tokenVocab = RustLexer;
}

expression :  expressionWithoutBlock    #ExprWithoutBlock
    | expressionWithBlock               #ExprWithBlock
    ;

expressionWithoutBlock : outerAttribute+ expressionWithoutBlock #AttributeExprWithoutBlock
    | literalExpression                                         #LiteralExprWithoutBlock
    | pathExpression                                            #PathExprWithoutBlock
    | expressionWithoutBlock PLUS expressionWithoutBlock        #AdditionExprWithoutBlock
    | expressionWithoutBlock MINUS expressionWithoutBlock       #MinusExprWithoutBlock
    | expressionWithBlock                                       #ExprnWith
    ;

literalExpression :
     CHAR_LITERAL                #CharLiteralExpr
   | STRING_LITERAL              #StringLiteralExpr
   | RAW_STRING_LITERAL          #RawStringLiteralExpr
   | BYTE_LITERAL                #ByteLiteralExpr
   | BYTE_STRING_LITERAL         #ByteStringLiteralExpr
   | RAW_BYTE_STRING_LITERAL     #RawByteStringLiteralExpr
   | INTEGER_LITERAL             #IntegerLiteralExpr
   | FLOAT_LITERAL               #FloatLiteralExpr
   | BOOLEAN_LITERAL             #BooleanLiteralExpr
   ;

expressionWithBlock :  outerAttribute* ( blockExpression );

blockExpression:  LCURLYBRACE innerAttribute* statements? RCURLYBRACE;

statements:
      statement+
    | statement+ expressionWithoutBlock
    | expressionWithoutBlock
    ;

statement :
      SEMI                  #EmptyStmt
    | letStatement          #LetStmt
    | expressionStatement   #ExprStmt
    ;

letStatement :
   outerAttribute* KW_LET varName=patternNoTopAlt ( COLON type )? (EQ rhs=expression )? SEMI;

expressionStatement :
     expressionWithoutBlock SEMI #ExprStmtWithoutBlock
   | expressionWithBlock SEMI?   #ExprStmtWithBlock
   ;

// todo should take value
outerAttribute : POUND LSQUAREBRACKET RSQUAREBRACKET;

// todo should take value
innerAttribute : POUND NOT LSQUAREBRACKET RSQUAREBRACKET;




pattern : '|'? patternNoTopAlt ( '|' patternNoTopAlt )* ;

patternNoTopAlt : patternWithoutRange;

patternWithoutRange :
      literalPattern    #litPat
   | identifierPattern  #identPat
   /*| WildcardPattern
   | RestPattern
   | ReferencePattern
   | StructPattern
   | TupleStructPattern
   | TuplePattern
   | GroupedPattern
   | SlicePattern
   | PathPattern
   | MacroInvocation*/
    ;

identifierPattern : KW_REF? KW_MUT? IDENTIFIER ('@' patternNoTopAlt)?;

literalPattern :
      BOOLEAN_LITERAL
   | CHAR_LITERAL
   | BYTE_LITERAL
   | STRING_LITERAL
   | RAW_STRING_LITERAL
   | BYTE_STRING_LITERAL
   | RAW_BYTE_STRING_LITERAL
   | MINUS? INTEGER_LITERAL
   | MINUS? FLOAT_LITERAL
   ;




type :
     typeNoBounds;
   /*| ImplTraitType
   | TraitObjectType*/

typeNoBounds :
      parenthesizedType
   /*| ImplTraitTypeOneBound
   | TraitObjectTypeOneBound*/
   | typePath
   /*| TupleType
   | NeverType
   | RawPointerType
   | ReferenceType
   | ArrayType
   | SliceType
   | InferredType
   | QualifiedPathInType
   | BareFunctionType
   | MacroInvocation*/
   ;

typePath :
   '::'? typePathSegment ('::' typePathSegment)*;

typePathSegment :
   pathIdentSegment '::'? (/*GenericArgs |*/ typePathFn)?;

typePathFn :
LPAREN typePathFnInputs? RPAREN ('->' type)?;

typePathFnInputs :
type (',' type)* ','?;

parenthesizedType:
    LPAREN type RPAREN;


pathExpression :
     pathInExpression;

pathInExpression :
   PATHSEP? pathExprSegment (PATHSEP pathExprSegment)*;

pathExprSegment :
   pathIdentSegment (PATHSEP genericArgs)?;

pathIdentSegment :
   IDENTIFIER | KW_SUPER | KW_SELFVALUE | KW_SELFTYPE | KW_CRATE | DOLLAR KW_CRATE;


genericArgs:
    LT GT;
/*   | < ( GenericArg , )* GenericArg ,? >

GenericArg :
   Lifetime | Type | GenericArgsConst | GenericArgsBinding

GenericArgsConst :
      BlockExpression
   | LiteralExpression
   | - LiteralExpression
   | SimplePathSegment

GenericArgsBinding :
   IDENTIFIER = Type*/