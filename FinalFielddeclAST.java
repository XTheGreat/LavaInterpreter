
/**
 * AST node for statement lists
 */ 
class FinalFielddeclAST extends FielddeclAST implements AST {
  String symbol;
	
  public FinalFielddeclAST()
  {
    symbol = "final";
  }

  public String toString() {
  	return(symbol);
  }
  	
}


