
/**
 * AST node for binary expressions
 */ 
class IdExprAST extends ExprAST implements AST {
  String symbol;

  public IdExprAST(String s) {
    symbol = s;
  }

  public String toString() {
    return(symbol); 
  }

  public String Evaluate(ST symTable){
    int index = -1;
    SymbolTable SA = (SymbolTable) symTable;
    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(new String(SA.symTableData.get(counter).identifier).equals(symbol))
      {
        index = counter;
      }

    }
    return SA.symTableData.get(index).value;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList exprTypeCheck = new TypeCheckList();

    TypeData declaredType = ReturnTypeCheck(symTable); // check that symbol exists

    if (declaredType == null) //not in symbol table
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + symbol + " is not declared!";
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
    // Looks up the Symbol Table for data type of symbol
    return typeData;
  }

}


