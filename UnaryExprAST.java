
/**
 * AST node for binary expressions
 */ 
class UnaryExprAST extends ExprAST implements AST {
  ExprAST argument;
  String operator;

  public UnaryExprAST(String op, ExprAST arg) {
    operator = op;
    argument = arg;
  }

  public String toString() {
    return("("+operator+" "+argument+")"); 
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList stmtTypeCheck = new TypeCheckList();

    TypeData declaredType = ReturnTypeCheck(symTable); // check that symbol exists and is boolean
    stmtTypeCheck.Combine(declaredType);
    if (declaredType == null) //not in symbol table
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + argument.toString() + " is not declared!";
      stmtTypeCheck.Combine(errorType);
    }
    if(new String(declaredType.Evaluate()).equals("error"))
    {
      declaredType.flag = "error: " + declaredType.Evaluate() + " is an invalid type for unary expression " + toString();
      stmtTypeCheck.Combine(declaredType);
    }
    return stmtTypeCheck;

  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = argument.ReturnTypeCheck(symTable);
    if(typeData != null)
    {
      if(new String(operator).equals("+") || new String(operator).equals("-"))
      {
        if(new String(typeData.Evaluate()).equals("float") || new String(typeData.Evaluate()).equals("int"))
        {
          // it will return type
        }
      }
      else if(new String(operator).equals("~"))
      {
        if(new String(typeData.Evaluate()).equals("bool") || new String(typeData.Evaluate()).equals("int"))
        {
          typeData = new TypeData("bool");
        }
      }
    }
    return typeData;
  }

  public String Evaluate(ST symTable){
    return (operator+argument.toString());
  }

}


