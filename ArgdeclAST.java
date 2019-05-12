/**
 * AST node for an expression.
 * 
 * The non terminal expression is the sum of multiple variants and
 * therefore modelled as an abstract class.
 * 
 */ 

import java.util.*;


abstract class ArgdeclAST implements AST {
	abstract public ST processSymbolTable(int scope);
	TypeData typedata = new TypeData();

	public TypeCheckList TypeCheck(ST symTable){return null;} // for type checking

	public void ExecuteMethod(SymbolTable SA){}
	public void ExecuteMethodValue(SymbolTable SA, List<String> listof){}
}

