
/**
 * AST node for binary expressions
 */ 
class IdArrayExprAST extends ExprAST implements AST {
  String symbol;
  ExprAST expression;

  public IdArrayExprAST(String s, ExprAST e) {
    symbol = s;
    expression = e;
  }

  public String toString() {
    return(symbol + "[" + expression + "]"); 
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList exprTypeCheck = new TypeCheckList();

    TypeData declaredType = ReturnTypeCheck(symTable); // check that symbol exists
    TypeData exprType = expression.ReturnTypeCheck(symTable);

    if (declaredType == null) //not in symbol table
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + symbol + " is not declared!";
      exprTypeCheck.Combine(errorType);
    }

    if(new String(exprType.Evaluate()).equals("int"))
    {
      int exprsize = Integer.parseInt(expression.toString());
      if(exprsize < 0 || exprsize >= declaredType.size)
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: array index out of bounds.";
        exprTypeCheck.Combine(errorType);
      }
    }//valid
    else
    {
    	TypeData errorType = new TypeData("error");
    	errorType.flag = "error: int type expected for array index.";
    	exprTypeCheck.Combine(errorType);
    }

    return exprTypeCheck;

  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = null;

    SymbolTable SA = (SymbolTable) symTable;

    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(new String(SA.symTableData.get(counter).identifier).equals(symbol))
      {
        typeData = SA.symTableData.get(counter).type;
      }

    }
    if(typeData != null)
    {
      switch(typeData.Evaluate())
      {
        case "arrint" :
          typeData = new TypeData("int");
        break;

        case "arrchar" :
          typeData = new TypeData("char");
        break;

        case "arrfloat" :
          typeData = new TypeData("float");
        break;

        case "arrbool" :
          typeData = new TypeData("bool");
        break;

        case "arrstring" :
          typeData = new TypeData("string");
        break;

        default : 
          break;
      }
    }
    return typeData;
  }
}


