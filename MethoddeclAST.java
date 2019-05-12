/**
 * AST node for a program statement
 * 
 * The non terminal stmt is the sum of multiple variants and
 * therefore modelled as an abstract class.
 * 
 */ 

import java.util.*;

abstract class MethoddeclAST implements AST {
	public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA){return null;}
	public ST processSymbolTable(int scope){return null;};
	public TypeCheckList TypeCheck(ST symTable){return null;} // for type checking

	public void ExecuteMethod(SymbolTable SA){}
	public void ExecuteMethodValue(SymbolTable SA, List<String> listof){}
}

