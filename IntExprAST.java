/**
 * AST node for an integer
 */ 
class IntExprAST extends ExprAST implements AST {
  Double number;          

  public IntExprAST(String s) {
    try { number = Double.parseDouble(s); }
    catch (NumberFormatException e) { number = Double.NaN; };
  }


  public String toString() {
    return(""+String.format("%.0f",number));
  }

  public String Evaluate(ST symTable){
    return toString();
  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = new TypeData("int");
    return typeData;
  }

}
