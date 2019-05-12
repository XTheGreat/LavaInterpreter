
/**
 * AST node for binary expressions
 */ 
class WhileStmtAST extends StmtAST implements AST {
  ExprAST expression;
  StmtAST statements;

  public WhileStmtAST(ExprAST e, StmtAST s) {
    expression = e;
    statements = s;
  }

  public String toString() {
    return("while " + "(" + expression + ")" + "\n" + statements); 
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    while(new String(expression.Evaluate(SA)).equals("true"))
    {
      statements.ExecuteMethod(SA);
    }

  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    //no new symbol is define here, so we can return an empty symbol table
    SymbolTable stmtST = new SymbolTable();

    TypeCheckList typeChecker = new TypeCheckList();
    typeChecker.Combine(TypeCheck(SA)); // this is where the expression type checking branches out
    typeChecker.Combine(expression.TypeCheck(SA));
    
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);
    semanticAnalyzer.Join(statements.SemanticAnalysis(scope, SA));

    return semanticAnalyzer;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList stmtTypeCheck = new TypeCheckList();

    TypeData declaredType = expression.ReturnTypeCheck(symTable); // check that symbol exists

    if(declaredType!=null)
    {
      if(new String("bool").equals(declaredType.Evaluate())){}
      else // if expressions should return bool
      {
        // COERCION!!
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: in while statement, " + expression.toString() + " must be a boolean statement at while statement";
        stmtTypeCheck.Combine(errorType);
      }
    }
    else
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: undefined variables at " + expression.toString();
      stmtTypeCheck.Combine(errorType);
    }

    return stmtTypeCheck;

  }

}


