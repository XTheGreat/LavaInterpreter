/**
 * AST node for an integer
 */ 
class BoolExprAST extends ExprAST implements AST {
  String symbol;          

  public BoolExprAST(String s) {
    symbol = s;
  }


  public String toString() {
    return(symbol);
  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = new TypeData("bool");
    return typeData;
  }

  public String Evaluate(ST symTable){
    return(symbol);
  }

}
