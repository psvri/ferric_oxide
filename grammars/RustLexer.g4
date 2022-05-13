lexer grammar RustLexer;

@header {
    package com.graal.rust;
}

//keywords from https://doc.rust-lang.org/stable/reference/keywords.html
//strict
KW_AS : 'as';
KW_BREAK : 'break';
KW_CONST : 'const';
KW_CONTINUE : 'continue';
KW_CRATE : 'crate';
KW_ELSE : 'else';
KW_ENUM : 'enum';
KW_EXTERN : 'extern';
KW_FALSE : 'false';
KW_FN : 'fn';
KW_FOR : 'for';
KW_IF : 'if';
KW_IMPL : 'impl';
KW_IN : 'in';
KW_LET : 'let';
KW_LOOP : 'loop';
KW_MATCH : 'match';
KW_MOD : 'mod';
KW_MOVE : 'move';
KW_MUT : 'mut';
KW_PUB : 'pub';
KW_REF : 'ref';
KW_RETURN : 'return';
KW_SELFVALUE : 'self';
KW_SELFTYPE : 'Self';
KW_STATIC : 'static';
KW_STRUCT : 'struct';
KW_SUPER : 'super';
KW_TRAIT : 'trait';
KW_TRUE : 'true';
KW_TYPE : 'type';
KW_UNSAFE : 'unsafe';
KW_USE : 'use';
KW_WHERE : 'where';
KW_WHILE : 'while';

// 2018+
KW_ASYNC : 'async';
KW_AWAIT : 'await';
KW_DYN : 'dyn';

//reserved
KW_ABSTRACT : 'abstract';
KW_BECOME : 'become';
KW_BOX : 'box';
KW_DO : 'do';
KW_FINAL : 'final';
KW_MACRO : 'macro';
KW_OVERRIDE : 'override';
KW_PRIV : 'priv';
KW_TYPEOF : 'typeof';
KW_UNSIZED : 'unsized';
KW_VIRTUAL : 'virtual';
KW_YIELD : 'yiel';

//2018+
KW_TRY : 'try';

//weak
KW_UNION : 'union';
KW_STATICLIFETIME : '\'static';

// identifiers from https://doc.rust-lang.org/stable/reference/identifiers.html
IDENTIFIER : NON_KEYWORD_IDENTIFIER | RAW_IDENTIFIER;
//todo Except crate, self, super, Self
RAW_IDENTIFIER : 'r#'IDENTIFIER_OR_KEYWORD;

//todo Except a strict or reserved keyword
NON_KEYWORD_IDENTIFIER : IDENTIFIER_OR_KEYWORD;
IDENTIFIER_OR_KEYWORD : [\p{XID_Start}][\p{XID_Continue}]* | '_'[\p{XID_Continue}]+;



// comments from https://doc.rust-lang.org/stable/reference/comments.html
LINE_COMMENT : ('//'(~[/!] | '//') ~'\n'* | '//') -> channel(HIDDEN);

fragment ISOLATEDCR: '\n\r';

//todo add rest of the comments blocks

WHITESPACE: [\p{Zs}] -> channel(HIDDEN);
NEWLINE: ('\r\n' | [\r\n]) -> channel(HIDDEN);

CHAR_LITERAL :
   '\''( ~['\\n\r\t] | QUOTE_ESCAPE | ASCII_ESCAPE | UNICODE_ESCAPE ) '\'';

fragment QUOTE_ESCAPE :
   '\'' | '\\"';

fragment ASCII_ESCAPE :
      '\\x'OCT_DIGIT HEX_DIGIT
      | '\n'
      | '\r'
      | '\t'
      | '\\'
      | '\\0'
      ;

//  ( HEX_DIGIT _* )1..6
fragment UNICODE_ESCAPE :
   '\\u{' HEX_DIGIT HEX_DIGIT? HEX_DIGIT? HEX_DIGIT? HEX_DIGIT? HEX_DIGIT? '}';

STRING_LITERAL :
   '"' (
      ~["\\ISOLATEDCR]
      | QUOTE_ESCAPE
      | ASCII_ESCAPE
      | UNICODE_ESCAPE
      | STRING_CONTINUE
   )* '"';

STRING_CONTINUE : '\n';

RAW_STRING_LITERAL : 'r' RAW_STRING_CONTENT;

RAW_STRING_CONTENT :
      '"' .*? '"'
   | '#' RAW_STRING_CONTENT '#';

BYTE_LITERAL : 'b\'' ( ASCII_FOR_CHAR | BYTE_ESCAPE ) '\'';

// todo  except ', \, \n, \r or \t
fragment ASCII_FOR_CHAR : ASCII;

fragment BYTE_ESCAPE :
      '\\x' HEX_DIGIT HEX_DIGIT
      | '\n'
      | '\r'
      | '\t'
      | '\\'
      | '\\0'
      | '\\\''
      | '\\"'
      ;

BYTE_STRING_LITERAL : 'b"' ( ASCII_FOR_STRING | BYTE_ESCAPE | STRING_CONTINUE )* '"';

// todo  except ', \, \n, \r or \t
fragment ASCII_FOR_STRING : ASCII;

RAW_BYTE_STRING_LITERAL : 'br' RAW_BYTE_STRING_CONTENT;

RAW_BYTE_STRING_CONTENT : '"' ASCII*? '"' | '#' RAW_BYTE_STRING_CONTENT '#';

fragment ASCII : [\u0000-\u0255];

INTEGER_LITERAL :
   ( DEC_LITERAL | BIN_LITERAL | OCT_LITERAL | HEX_LITERAL ) INTEGER_SUFFIX?;

fragment DEC_LITERAL :
   DEC_DIGIT (DEC_DIGIT|'_')*;

fragment BIN_LITERAL :
            '0b' (BIN_DIGIT|'_')* BIN_DIGIT (BIN_DIGIT|'_')*;

fragment OCT_LITERAL :
   '0o' (OCT_DIGIT|'_')* OCT_DIGIT (OCT_DIGIT|'_')*;

fragment HEX_LITERAL :
            '0x' (HEX_DIGIT|'_')* HEX_DIGIT (HEX_DIGIT|'_')*;

fragment BIN_DIGIT : [0-1];
fragment OCT_DIGIT : [0-7];
fragment DEC_DIGIT : [0-9];
fragment HEX_DIGIT : [0-9a-fA-F];
fragment INTEGER_SUFFIX :
      'u8'
      | 'u16'
      | 'u32'
      | 'u64'
      | 'u128'
      | 'usize'
      | 'i8'
      | 'i16'
      | 'i32'
      | 'i64'
      | 'i128'
      | 'isize'
      ;

TUPLE_INDEX: INTEGER_LITERAL;

FLOAT_LITERAL :
      DEC_LITERAL '.'
   | DEC_LITERAL FLOAT_EXPONENT
   | DEC_LITERAL '.' DEC_LITERAL FLOAT_EXPONENT?
   | DEC_LITERAL ('.' DEC_LITERAL)? FLOAT_EXPONENT? FLOAT_SUFFIX;

FLOAT_EXPONENT : [eE] [+-]? (DEC_DIGIT|'_')* DEC_DIGIT (DEC_DIGIT|'_')*;

FLOAT_SUFFIX : 'f32' | 'f64';

BOOLEAN_LITERAL : KW_TRUE | KW_FALSE;

LIFETIME_TOKEN : '\'' IDENTIFIER_OR_KEYWORD | '\'_';

LIFETIME_OR_LABEL : '\'' NON_KEYWORD_IDENTIFIER;

// Punctuation
PLUS: '+';
MINUS: '-';
STAR: '*';
SLASH: '/';
PERCENT: '%';
CARET: '^';
NOT: '!';
AND: '&';
OR: '|';
ANDAND: '&&';
OROR: '||';
SHL: '<<';
SHR: '>>';
PLUSEQ: '+=';
MINUSEQ: '-=';
STAREQ: '*=';
SLASHEQ: '/=';
PERCENTEQ: '%=';
CARETEQ: '^=';
ANDEQ: '&=';
OREQ: '|=';
SHLEQ: '<<=';
SHREQ: '>>=';
EQ: '=';
EQEQ: '==';
NE: '!=';
GT: '>';
LT: '<';
GE: '>=';
LE: '<=';
AT: '@';
UNDERSCORE: '_';
DOT: '.';
DOTDOT: '..';
DOTDOTDOT: '...';
DOTDOTEQ: '..=';
COMMA: ',';
SEMI: ';';
COLON: ':';
PATHSEP: '::';
RARROW: '->';
FATARROW: '=>';
POUND: '#';
DOLLAR: '$';
QUESTION: '?';

// Delimiters
LCURLYBRACE: '{';
RCURLYBRACE: '}';
LSQUAREBRACKET: '[';
RSQUAREBRACKET: ']';
LPAREN: '(';
RPAREN: ')';