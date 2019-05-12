/**
 * General interface for nodes in the abstract syntax tree. Contains   
 * only the method toString which is already inherited from Object,   
 * so the interface doesn't add any functionality. It only provides   
 * a common super type for all elements in the AST.                   
 */

import java. util.*;

interface AST {
	public String toString();   // already inherited from Object
	// every AST should have a typecheck function, for some ASTs, type check simply returns null
	// for others, it returns the error type
	public TypeCheckList TypeCheck(ST symTable);
	public void ExecuteMethod(SymbolTable SA); // needed to run the interpreter
	public void ExecuteMethodValue(SymbolTable SA, List<String> listof);
}

