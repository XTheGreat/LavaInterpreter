
/**
 * AST node for statement lists
 */ 
class StmtListAST extends StmtAST implements AST {
  StmtAST first;
  StmtAST rest;

  public StmtListAST(StmtAST f, StmtAST r) {
    first = f;
    rest = r;
  }

  public String toString() {
    return(first+"\n"+rest);
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    //no new symbol is define here, so we can return an empty symbol table
    SymbolTable stmtST = new SymbolTable();
    // create new table data entry for program
    TypeCheckList typeChecker = new TypeCheckList();
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);
    semanticAnalyzer.Join(first.SemanticAnalysis(scope, SA));
    semanticAnalyzer.Join(rest.SemanticAnalysis(scope, SA));
    return semanticAnalyzer;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList fTypeCheck = first.TypeCheck(symTable);
    TypeCheckList rTypeCheck = rest.TypeCheck(symTable);
    
    if(fTypeCheck == null &&  rTypeCheck == null)
    {
      return null;
    }
    else
    {
      // create new TypeCheckList
      TypeCheckList stmtTypeCheck = new TypeCheckList();
      stmtTypeCheck.Combine(fTypeCheck); // results of fields
      stmtTypeCheck.Combine(rTypeCheck); // results of methods
      return stmtTypeCheck;
    }

  }


  public void ExecuteMethod(SymbolTable SA)
  {
    first.ExecuteMethod(SA);
    rest.ExecuteMethod(SA);
  }
}