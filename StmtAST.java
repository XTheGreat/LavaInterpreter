/**
 * AST node for a program statement
 * 
 * The non terminal stmt is the sum of multiple variants and
 * therefore modelled as an abstract class.
 * 
 */ 

import java.util.*;

abstract class StmtAST implements AST {
	public ST processSymbolTable(int scope)
	{
		return null;
	}

	public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA){return null;}
	public TypeCheckList TypeCheck(ST symTable){return null;} // for type checking

	public TypeData ReturnTypeCheck(ST symTable){return null;} //used to return specific data type

	public void ExecuteMethod(SymbolTable SA){}
	public void ExecuteMethodValue(SymbolTable SA, List<String> listof){}

	public String Evaluate(ST symTable) {return null;}
}

