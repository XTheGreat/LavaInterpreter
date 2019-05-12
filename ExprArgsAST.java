
/**
 * AST node for binary expressions
 */ 
class ExprArgsAST extends ArgsAST implements AST {
  ExprAST expression;
  ArgsAST arguments;

  public ExprArgsAST(ExprAST e, ArgsAST a) {
    expression = e;
    arguments = a;
  }

  public ExprArgsAST(ExprAST e) {
    expression = e;
    arguments = null;
  }

  public String toString() {
  	if(arguments == null)
  	{
  		return(""+expression); 
  	}
    else
    {
    	return(expression + " , " + arguments); 
    }
  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData argstypeData = new TypeData();
    if(expression.ReturnTypeCheck(symTable)!=null)
      argstypeData.PutType(expression.ReturnTypeCheck(symTable));
    
    if(arguments!=null)
      argstypeData.PutType(arguments.ReturnTypeCheck(symTable));
    
    return argstypeData;
  }

}


