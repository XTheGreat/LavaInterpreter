
/**
 * AST node for statement lists
 */ 
class ReturnExprStmtAST extends StmtAST implements AST {
  String symbol;
  ExprAST expression;
	
  public ReturnExprStmtAST(ExprAST e)
  {
    symbol = "return";
    expression = e;
  }

  public String toString() {
  	return(symbol  + " " + expression + ";");
  }

  public void ExecuteMethod(SymbolTable SA)
  {

    // get last symboldata with type return not null
    int index = -1;
    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(SA.symTableData.get(counter).type.typeReturn  != null)
      {
        index = counter;
      }

    } 

    if(index!=-1)
    {
      SA.symTableData.get(index).value = expression.Evaluate(SA);
    }

  }

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
    
    TypeData retType = expression.ReturnTypeCheck(symTable);
    TypeData callFuncType = ReturnTypeCheck(symTable); // typecheck here will compare the previous scope
    typeChecker.Combine(expression.TypeCheck(symTable)); // if this expression itself has an error
    
    if(retType != null && callFuncType!= null)
    {
      if (new String(callFuncType.Evaluate()).equals(retType.Evaluate())){}
      /*else
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: " + callFuncType.Evaluate() + " vcb type function cannot return a " + retType.Evaluate() + " value at " + toString();
        typeChecker.Combine(errorType);
      }*/
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

    int index = -1;


    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(SA.symTableData.get(counter).scope  == funcscope)
      {
        typeData = SA.symTableData.get(counter).type;
        index = counter;
      }

    } 

    return typeData;
  }
  	
}


