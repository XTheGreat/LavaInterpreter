/**
 * AST node for an expression.
 * 
 * The non terminal expression is the sum of multiple variants and
 * therefore modelled as an abstract class.
 * 
 */ 
import java.util.*;

abstract class ExprAST implements AST {
	
	public TypeCheckList TypeCheck(ST symTable){return null;} // for type checking

	public TypeData ReturnTypeCheck(ST symTable){return null;} //used to return specific data type in optional expression

	public void ExecuteMethod(SymbolTable SA){}

	public String Evaluate(ST symTable) {return null;}
	public void ExecuteMethodValue(SymbolTable SA, List<String> listof){}
}
