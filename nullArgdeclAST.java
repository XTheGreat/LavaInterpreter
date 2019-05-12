
/**
 * AST node for statement lists
 */ 
class nullArgdeclAST extends ArgdeclAST implements AST {

  public nullArgdeclAST() {

  }

  public String toString() {
    return("");
  }

  public ST processSymbolTable(int scope)
  {
  	return null;
  }

}


