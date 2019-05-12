/**
 * AST node for an integer
 */ 
class CharExprAST extends ExprAST implements AST {
  String symbol;          

  public CharExprAST(String s) {
    symbol = s;
  }


  public String toString() {
    return(symbol);
  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = new TypeData("char");
    return typeData;
  }

  public String Evaluate(ST symTable){
    return(symbol);
  }

}
