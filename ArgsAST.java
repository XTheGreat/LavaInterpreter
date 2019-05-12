/**
 * AST node for an expression.
 * 
 * The non terminal expression is the sum of multiple variants and
 * therefore modelled as an abstract class.
 * 
 */ 
import java.util.*;

abstract class ArgsAST implements AST {
	public TypeCheckList TypeCheck(ST symTable){return null;} // for type checking
	public TypeData ReturnTypeCheck(ST symTable){return null;} // for type checking\

	public void ExecuteMethod(SymbolTable SA){}
	public void ExecuteMethodValue(SymbolTable SA, List<String> listof){}
}

