
/**
 * AST node for statement lists
 */ 
class ReturnStmtAST extends StmtAST implements AST {
  String symbol;
  
  public ReturnStmtAST()
  {
    symbol = "return";
  }

  public String toString() {
    return(symbol  + ";");
  }

  // void functions don't need to return anything

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    //no new symbol is define here, so we can return an empty symbol table
    SymbolTable stmtST = new SymbolTable();
    TypeCheckList typeChecker = TypeCheck(SA);
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);
    return semanticAnalyzer;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList typeChecker = new TypeCheckList(); // type checking by passing symbol table values

    TypeData callFuncType = ReturnTypeCheck(symTable); // typecheck here will compare the previous scope
    
    if (callFuncType != null)
    {
      if (new String(callFuncType.Evaluate()).equals("void")){}
      else
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: 'return;' is a void type function statement.";
        typeChecker.Combine(errorType);
      }
    }

    return typeChecker;

  }

  // looks up the type of function
  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = null;

    SymbolTable SA = (SymbolTable) symTable;
    SA.GetScopeLevel();
    int funcscope = SA.scopelevel - 1; // last higher scope!


    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(SA.symTableData.get(counter).scope  == funcscope)
      {
        typeData = SA.symTableData.get(counter).type;
      }

    } 
    // Looks up the Symbol Table for data type of symbol
    return typeData;
  }
    
}
