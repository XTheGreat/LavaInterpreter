/**
 * AST node for an integer
 */ 
class BinopExprAST extends ExprAST implements AST {
  String symbol;          

  public BinopExprAST(String s) {
    symbol = s;
  }


  public String toString() {
    return(symbol);
  }

}
