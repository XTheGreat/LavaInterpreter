/**
 * AST node for an expression.
 * 
 * The non terminal expression is the sum of multiple variants and
 * therefore modelled as an abstract class.
 * 
 */ 

import java.util.*;

abstract class TypeAST implements AST {
	public TypeCheckList TypeCheck(ST symTable){return null;} // for type checking
	public void ExecuteMethodValue(SymbolTable SA, List<String> listof){}
	public void ExecuteMethod(SymbolTable SA){}
}

