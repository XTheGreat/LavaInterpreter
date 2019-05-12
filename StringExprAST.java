/**
 * AST node for an integer
 */ 
class StringExprAST extends ExprAST implements AST {
  String symbol;          

  public StringExprAST(String s) {
    symbol = s;
  }


  public String toString() {
    return(symbol);
  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = new TypeData("string");
    return typeData;
  }

  public String Evaluate(ST symTable){
    String s = toString();
    return s.replace("\"", "");
  }

}
