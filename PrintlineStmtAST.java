
/**
 * AST node for binary expressions
 */ 
class PrintlineStmtAST extends StmtAST implements AST {

  public PrintlineStmtAST() {
  }

  public String toString() {
    return("printline " + "(" + ");"); 
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    //evaluate statement;
    System.out.println("");
  }

}


