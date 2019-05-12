# uses JFlex >= 1.3.2, and CUP >= 0.10j
#
# targets:
#
# make all                                                            
#    generates lexer, and parser, and compiles all *.java files
#                                                                     
# make run (or just: make)                                           
#    starts the program on a test example
#                                                                     

JAVA=java
JAVAC=javac
# Root of the project
JFLEX=$(JAVA) -jar jflex-full-1.7.0.jar
CUPJAR=./java-cup-11b.jar
CUP=$(JAVA) -jar $(CUPJAR)
CP=.:$(CUPJAR)

default: test

.SUFFIXES: $(SUFFIXES) .class .java

.java.class:
	$(JAVAC) -cp $(CP) $*.java

FILE=	Yylex.java      parser.java    sym.java \
		AST.java\
		ProgramListAST.java\
		ProgramAST.java FielddeclAST.java MemberdeclAST.java MethoddeclAST.java\
		ArgdeclAST.java\
		ExprAST.java StmtAST.java TypeAST.java ArgsAST.java\
		Main.java

run: output.txt

output.txt: all
	$(JAVA) -cp $(CP) Main < sample1.txt > output/sample1-output.txt
	cat output/sample1-output.txt
	$(JAVA) -cp $(CP) Main < sample2.txt > output/sample2-output.txt
	cat output/sample2-output.txt
	$(JAVA) -cp $(CP) Main < sample3.txt > output/sample3-output.txt
	cat output/sample3-output.txt
	$(JAVA) -cp $(CP) Main < sample4.txt > output/sample4-output.txt
	cat output/sample4-output.txt
	

all: Yylex.java parser.java $(FILE:java=class)

clean:
	rm -f *.class *~ *.bak Yylex.java parser.java sym.java

Yylex.java: scanner.jflex
	$(JFLEX) scanner.jflex

parser.java: parser.cup
	$(CUP) -interface < parser.cup -dump

test: output.txt
	@(diff output.txt output.good && echo "Test OK!") || echo "Test failed!"