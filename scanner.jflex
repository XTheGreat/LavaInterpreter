/*-***
 *
 * This file defines a stand-alone lexical analyzer for a subset of the Lava
 * programming language.  This is the same lexer that will later be integrated
 * with a CUP-based parser.  Here the lexer is driven by the simple Java test
 * program in ./LavaLexerTest.java, q.v.  See 330 Lecture Notes 2 and the
 * Assignment 2 writeup for further discussion.
 *
 */


import java_cup.runtime.*;


%%
/*-*
 * LEXICAL FUNCTIONS:
 */

%cup
%implements sym

%{

/**
 * Return a new Symbol with the given token id, and with the current line and
 * column numbers.
 */

  private Symbol sym(int sym) {
    return new Symbol(sym);
  }

/**
 * Return a new Symbol with the given token id, the current line and column
 * numbers, and the given token value.  The value is used for tokens such as
 * identifiers and numbers.
 */

  private Symbol sym(int sym, Object val) {
    return new Symbol(sym, val);
  }

%}


/*-*
 * PATTERN DEFINITIONS:
 */
letter          = [A-Za-z]
digit           = [0-9]
alphanumeric    = {letter}|{digit}
other_id_charlit= [_]
space 			= [ ]
identifier      = {letter}({letter}|{digit})*
intlit          = {digit}+
floatlit 		= ({intlit})|({intlit}+"."+{intlit})
singlequote 	= \\\'
doublequote 	= \\\"
tab 			= \\\t
return 			= \\\r
newline 		= \\\n
validstr 		= ({doublequote}|{tab}|{return}|{newline})
strlit 			= \"([^\"\t\r\n]|{validstr})*\"
validchar 		= ({singlequote}|{tab}|{return}|{newline})
charlit 		= \'([^\'\t\r\n\\]|((\\)([t|r|n|\\|'])))\'
whitespace      = [ \n\t]
lineComment		= "\\"[^\n]*
blockComment	= \\\*([^*])*\*\\
comment         = {lineComment}|{blockComment}
bool 			= ("true")|("false")
type 			= ("int")|("char")|("bool")|("float")|("string")



%%
/**
 * LEXICAL RULES:
 */

"true" 			{ return sym(TRUE, yytext()); }
"false" 		{ return sym(FALSE, yytext()); }
"else"          { return sym(ELSE); }
"end"           { return sym(END); }
"if"            { return sym(IF); }
"of"            { return sym(OF); }
"class"			{ return sym(CLASS); }
"then"          { return sym(THEN); }
"void"          { return sym(VOID); }
null 			{return sym(NULL); }
"var"           { return sym(VAR); }
"read"			{ return sym(READ); }
"print"			{ return sym(PRINT); }
"printline"		{ return sym(PRINTLINE); }
"final"			{ return sym(FINAL); }
"while"			{ return sym(WHILE); }
"return"		{ return sym(RETURN); }
"?"				{ return sym(QUESTION); }
"||"      		{ return sym(OR); }
"&&"            { return sym(AND); }
"*"             { return sym(TIMES); }
"+"             { return sym(PLUS); }
"-"             { return sym(MINUS); }
"+"            	{ return sym(PPLUS); }
"-"            	{ return sym(PMINUS); }
"+"             { return sym(UPLUS); }
"-"             { return sym(UMINUS); }
"/"             { return sym(DIV); }
";"             { return sym(SEMI); }
":"             { return sym(COLON); }
","             { return sym(COMMA); }
"("             { return sym(LPAR); }
")"             { return sym(RPAR); }
"{"             { return sym(LCURL); }
"}"             { return sym(RCURL); }
"["             { return sym(LSQR); }
"]"             { return sym(RSQR); }
"=="            { return sym(EQ); }
">"             { return sym(GTR); }
"<"             { return sym(LESS); }
"<="            { return sym(LESS_EQ); }
">="            { return sym(GTR_EQ); }
"<>"            { return sym(NOT_EQ); }
"~"             { return sym(TILDA); }
"="            	{ return sym(ASSMNT); }
"."             { return sym(DOT); }
<<EOF>>         { return sym(EOF); }
{type}       	{ return sym(TYPE, yytext()); }
{identifier}    { return sym(IDENT, yytext()); }
{intlit}   		{ return sym(INT, yytext()); }
{floatlit} 		{ return sym(FLOAT, yytext()); }
{charlit} 		{ return sym(CHAR, yytext()); }
{strlit} 		{ return sym(STRING, yytext()); }
{comment}       { /* Ignore comments. */}
{whitespace}    { /* Ignore whitespace. */ }
.               { System.out.println("Illegal char, '" + yytext() +
                    "' line: " + yyline + ", column: " + yychar); }
