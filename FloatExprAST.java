/**
 * AST node for a number
 */ 
class FloatExprAST extends ExprAST implements AST {
  Double number;

  public FloatExprAST(String s) {
    try { number = Double.parseDouble(s); }
    catch (NumberFormatException e) { number = Double.NaN; };
  }

  public String toString() {
    return(""+String.format("%f",number)); 
  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = new TypeData("float");
    return typeData;
  }

  public String Evaluate(ST symTable){
    return toString();
  }

}

