
/**
 * AST node for statement lists
 */ 
class OptionalsemiMethoddeclAST extends MethoddeclAST implements AST {
  String symbol;
	
  public OptionalsemiMethoddeclAST()
  {
    symbol = ";";
  }

  public String toString() {
  	return(symbol);
  }
  	
}


