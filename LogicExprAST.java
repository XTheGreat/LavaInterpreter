
/**
 * AST node for binary expressions
 */ 
class LogicExprAST extends ExprAST implements AST {
  ExprAST firstexpr;
  ExprAST secexpr;
  ExprAST thirdexpr;

  public LogicExprAST(ExprAST f, ExprAST s, ExprAST t) {
    firstexpr = f;
    secexpr = s;
    thirdexpr = t;
  }

  public String toString() {
    return("(" + firstexpr + " ? " + secexpr + " : " + thirdexpr + ")"); 
  }

  public void ExecuteMethod(SymbolTable SA){System.out.println("I'm here");}

  public String Evaluate(ST symTable)
  {

    System.out.println("I'm here");
    return ("true");
    /*
    if(new String(firstexpr.Evaluate(symTable)).equals("true"))
    {
      return secexpr.Evaluate(symTable);
    }
    else
    {
      return thirdexpr.Evaluate(symTable);
    }
    */

  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList fTypeCheck = firstexpr.TypeCheck(symTable);
    TypeCheckList rTypeCheck = secexpr.TypeCheck(symTable);
    TypeCheckList tTypeCheck = thirdexpr.TypeCheck(symTable);
    
    // create new TypeCheckList
    TypeCheckList exprTypeCheck = new TypeCheckList();
    exprTypeCheck.Combine(fTypeCheck); // results of fields
    exprTypeCheck.Combine(rTypeCheck); 
    exprTypeCheck.Combine(tTypeCheck); 

    if(ReturnTypeCheck(symTable) == null )
    {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: " + toString() + " invalid types provided for bool expression.";
        exprTypeCheck.Combine(errorType);
    }
    
    return exprTypeCheck;
  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = new TypeData();
    String first, sec, third;
    first = firstexpr.ReturnTypeCheck(symTable).Evaluate();
    sec = secexpr.ReturnTypeCheck(symTable).Evaluate();
    third = thirdexpr.ReturnTypeCheck(symTable).Evaluate();

    if ( (new String(first).equals("bool") || new String(first).equals("int")) && new String(sec).equals(third) )
    {
      typeData = new TypeData("bool");
    }
    else if ( (new String(first).equals("bool") || new String(first).equals("int")) && new String(sec).equals("int") && new String(sec).equals("float") )
    {
      typeData = new TypeData("bool");
    }
    else if ( (new String(first).equals("bool") || new String(first).equals("int")) && new String(sec).equals("float") && new String(sec).equals("int") )
    {
      typeData = new TypeData("bool");
    }
    else
    {
      typeData = null;
    }

    return typeData;
  }

}


