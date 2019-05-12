/**
 * AST node for a number
 */ 
class PrintlistStmtAST extends StmtAST implements AST {
  ExprAST name;
  PrintlistStmtAST printlist;

  public PrintlistStmtAST(ExprAST n) {
    name = n;
    printlist = null;
  }

  public PrintlistStmtAST(ExprAST n, PrintlistStmtAST r) {
    name = n;
    printlist = r;
  }

  public String toString() {
    if(printlist == null)
    {
    	return (""+name);
    }
    else
    {
    	return (name + " , " + printlist);
    }
  }


  public String Evaluate(ST symTable) {
    if(printlist == null)
    {
      return (""+name.Evaluate(symTable));
    }
    else
    {
      return (name.Evaluate(symTable) + " " + printlist.Evaluate(symTable));
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
    TypeCheckList stmtTypeCheck = new TypeCheckList();

    TypeData declaredType = name.ReturnTypeCheck(symTable); // check that symbol exists and is boolean

    if(declaredType!=null)
    {
      if(new String("arrint").equals(declaredType.Evaluate()) || new String("arrfloat").equals(declaredType.Evaluate()) || new String("arrbool").equals(declaredType.Evaluate()) || new String("arrchar").equals(declaredType.Evaluate()) || new String("arrstring").equals(declaredType.Evaluate()) )
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: variable cannot be entire array at print statement";
        stmtTypeCheck.Combine(errorType);
      }
      else if(declaredType.typeReturn != null)
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: variable cannot be a method name at print statement";
        stmtTypeCheck.Combine(errorType);
      }
    }
    if(printlist!=null)
      stmtTypeCheck.Combine(printlist.TypeCheck(symTable));
    return stmtTypeCheck;

  }

}

