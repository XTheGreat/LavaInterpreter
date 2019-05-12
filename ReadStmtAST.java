
/**
 * AST node for binary expressions
 */
class ReadStmtAST extends StmtAST implements AST {
  ReadlistStmtAST statement;

  public ReadStmtAST(ReadlistStmtAST s) {
  	statement = s;
  }

  public String toString() {
    return("read " + "(" + statement + ");"); 
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    //no new symbol is define here, so we can return an empty symbol table
    SymbolTable stmtST = new SymbolTable();
    TypeCheckList typeChecker = new TypeCheckList();
    typeChecker.Combine(TypeCheck(SA));
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);
    semanticAnalyzer.Join(statement.SemanticAnalysis(scope, SA));
    return semanticAnalyzer;
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    //evaluate statement;
    statement.ExecuteMethod(SA); // reads in to symbol from command line
  }

  
}


