
/**
 * AST node for binary expressions
 */ 
class IdArgsEmptyStmtAST extends StmtAST implements AST {
  String symbol;

  public IdArgsEmptyStmtAST(String s) {
    symbol = s;
  }

  public String toString() {
    return(symbol + " (" + ")"); 
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    //no new symbol is define here, so we can return an empty symbol table
    SymbolTable stmtST = new SymbolTable();

    // create new table data entry for program
    ST symbolTable = new SymbolTable();
    symbolTable = SA;

    TypeCheckList typeChecker = TypeCheck(symbolTable);
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);

    return semanticAnalyzer;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList stmtTypeCheck = new TypeCheckList();

    TypeData declaredType = ReturnTypeCheck(symTable); // check that symbol exists

    if (declaredType == null) //not in symbol table
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + symbol + " is not declared!";
      stmtTypeCheck.Combine(errorType);
    }

    return stmtTypeCheck;

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


