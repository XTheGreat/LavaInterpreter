
/**
 * AST node for binary expressions
 */ 
class TypeExprAST extends ExprAST implements AST {
  String symbol;
  ExprAST expression;

  public TypeExprAST(String s, ExprAST e) {
    symbol = s;
    expression = e;
  }

  public String toString() {
    return("(" + symbol +")" +  " " + expression); 
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
  	TypeCheckList exprTypeCheck = new TypeCheckList();
  	if (ReturnTypeCheck(symTable).GetType() == null)
  	{
  		// error invalid typecasting!
  		TypeData errorType = new TypeData("error");
  		errorType.flag = "error: " + toString() + " must be bool type.";
  		exprTypeCheck.Combine(errorType);
  	}
  	exprTypeCheck.Combine(expression.TypeCheck(symTable));
  	return exprTypeCheck;
  }

  // typecasting
  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = new TypeData(symbol); // it converts it some data type or null by default
    return typeData;
  }

  public String Evaluate(ST symTable){
    return expression.Evaluate(symTable);
  }

}


