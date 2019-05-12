
/**
 * AST node for statement lists
 */ 
class OptionalexprFielddeclAST extends FielddeclAST implements AST {
  String symbol;
  ExprAST expression;
	
  public OptionalexprFielddeclAST(ExprAST e)
  {
    symbol = "=";
    expression = e;
  }

  public String toString() {
  	return(" " + symbol + " " + expression.toString());
  }

  public String Evaluate(ST symTable) {
    return(""+expression.Evaluate(symTable));
  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    return expression.ReturnTypeCheck(symTable);
  }
  
  
}


