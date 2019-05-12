
/**
 * AST node for binary expressions
 */ 
class BinaryExprAST extends ExprAST implements AST {
  ExprAST leftarg, rightarg;
  String operator;

  public BinaryExprAST(ExprAST left, String binop, ExprAST right) {
    leftarg = left;
    operator = binop;
    rightarg = right;
  }

  public String toString() {
    return("("+leftarg+" "+operator+" "+rightarg+")"); 
  }

  public String Evaluate(ST symTable){

    // * | / | + | - | < | > | <= | >= | == | <> | \|\| | && 

    TypeData dataType = ReturnTypeCheck(symTable);
    String value = "";

    if(dataType != null)
    {
      if( new String(dataType.Evaluate()).equals("int"))
      {
        int result = 0;
        // perform operation based 
        switch(operator)
        {
          case "+" :
            result = Integer.parseInt(leftarg.Evaluate(symTable)) + Integer.parseInt(rightarg.Evaluate(symTable));
            value = Integer.toString(result) ;
          break;

          case "-" :
            result = Integer.parseInt(leftarg.Evaluate(symTable)) - Integer.parseInt(rightarg.Evaluate(symTable));
            value = Integer.toString(result) ;
          break;

          case "*" :
            result = Integer.parseInt(leftarg.Evaluate(symTable)) * Integer.parseInt(rightarg.Evaluate(symTable));
            value = Integer.toString(result) ;
          break;

          case "/" :
            result = Integer.parseInt(leftarg.Evaluate(symTable)) / Integer.parseInt(rightarg.Evaluate(symTable));
            value = Integer.toString(result) ;
          break;

          default : 
            value = null;
        }

        
        return value;
      }

      if( new String(dataType.Evaluate()).equals("float"))
      {
        double result = 0;
        // perform operation based 
        switch(operator)
        {
          case "+" :
            result = Double.parseDouble(leftarg.Evaluate(symTable)) + Double.parseDouble(rightarg.Evaluate(symTable));
            value = String.format("%f",result) ;
          break;

          case "-" :
            result = Double.parseDouble(leftarg.Evaluate(symTable)) - Double.parseDouble(rightarg.Evaluate(symTable));
            value = String.format("%f",result) ;
          break;

          case "*" :
            result = Double.parseDouble(leftarg.Evaluate(symTable)) * Double.parseDouble(rightarg.Evaluate(symTable));
            value = String.format("%f",result) ;
          break;

          case "/" :
            result = Double.parseDouble(leftarg.Evaluate(symTable)) / Double.parseDouble(rightarg.Evaluate(symTable));
            value = String.format("%f",result) ;
          break;

          default : 
            value = null;
        }
      }

      if( new String(dataType.Evaluate()).equals("string"))
      {
        String s1 = leftarg.Evaluate(symTable);
        String s2 = rightarg.Evaluate(symTable);
        // perform operation based 
        switch(operator)
        {
          case "+" :
            value = s1.concat(s2).replace("\"","");
          break;

          case "-" :
            s2 = s2.replace("\"","");
            value = s1.replace("\"","").replace(s2, "");
          break;

          default : 
            value = null;
        }

      }

      if( new String(dataType.Evaluate()).equals("bool"))
      {
        String s1 = leftarg.Evaluate(symTable);
        String s2 = rightarg.Evaluate(symTable);
        // perform operation based 
        switch(operator)
        {
          case "<" :
            if(Double.parseDouble(s1) < Double.parseDouble(s2))
              value = "true";
            else
              value = "false";
          break;

          case ">" :
            if(Double.parseDouble(s1) > Double.parseDouble(s2))
              value = "true";
            else
              value = "false";
          break;

          case "<=" :
            if(Double.parseDouble(s1) <= Double.parseDouble(s2))
              value = "true";
            else
              value = "false";
          break;

          case ">=" :
            if(Double.parseDouble(s1) >= Double.parseDouble(s2))
              value = "true";
            else
              value = "false";
          break;

          case "==" :
            if(new String(s1).equals(s2))
              value = "true";
            else
              value = "false";
          break;

          case "<>" :
            if(new String(s1).equals(s2))
              value = "false";
            else
              value = "true";
          break;

          case "&&" :
            if(new String(s1).equals("true") && new String(s2).equals("true"))
              value = "true";
            else
              value = "false";
          break;

          case "||" :
            if(new String(s1).equals("true") || new String(s2).equals("true"))
              value = "true";
            else
              value = "false";
          break;

          default : 
            value = null;
        }

      }
    }

    return value;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList fTypeCheck = leftarg.TypeCheck(symTable);
    TypeCheckList rTypeCheck = rightarg.TypeCheck(symTable);
    TypeCheckList exprTypeCheck = new TypeCheckList();

    if(ReturnTypeCheck(symTable) == null)
    {
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: undefined variable at " + toString();
      exprTypeCheck.Combine(errorType);
    }
    
    if(fTypeCheck == null &&  rTypeCheck == null)
    {
      return null;
    }
    else
    {
      // create new TypeCheckList
      
      exprTypeCheck.Combine(fTypeCheck); // results of fields
      exprTypeCheck.Combine(rTypeCheck); // results of methods
      
      return exprTypeCheck;
    }
  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = null;
    TypeData ldata = leftarg.ReturnTypeCheck(symTable);
    TypeData rdata = rightarg.ReturnTypeCheck(symTable);
    if(ldata!=null && rdata!=null)
    {
      // should check for each binary operator
      // +, * etc are for string
      // < <= > are for bools

      if ((new String(ldata.Evaluate()).equals("int") || new String(ldata.Evaluate()).equals("float")) && (new String(rdata.Evaluate()).equals("int") || new String(rdata.Evaluate()).equals("float"))) 
      {
        if(new String(operator).equals("<") || new String(operator).equals("<=") || new String(operator).equals(">") || new String(operator).equals(">=") || new String(operator).equals("<>") || new String(operator).equals("==") || new String(operator).equals("||") || new String(operator).equals("&&"))
        {
          typeData = new TypeData("bool");
        }
        else if (new String(operator).equals("+") || new String(operator).equals("-") || new String(operator).equals("*") || new String(operator).equals("/"))
        {
          if (new String(ldata.Evaluate()).equals("float") || new String(rdata.Evaluate()).equals("float"))
          {
            typeData = new TypeData("float");
          }
          else
          {
            typeData = new TypeData("int");
          }

        }
      }

      else if (new String(ldata.Evaluate()).equals(rdata.Evaluate()))
      { // < | > | <= | >= | == | <>
        if(new String(operator).equals("<") || new String(operator).equals("<=") || new String(operator).equals(">") || new String(operator).equals(">=") || new String(operator).equals("<>") || new String(operator).equals("==") || new String(operator).equals("||") || new String(operator).equals("&&"))
        {
          typeData = new TypeData("bool");
        }
        else if(new String(operator).equals("+") || new String(operator).equals("-"))
        {
          if (new String(ldata.Evaluate()).equals("string"))
          {
            typeData = new TypeData("string");
          }
        }
      }

      else if ((new String(ldata.Evaluate()).equals("int") || new String(ldata.Evaluate()).equals("bool")) && (new String(rdata.Evaluate()).equals("int") || new String(rdata.Evaluate()).equals("bool"))) 
      {
        if(new String(operator).equals("<") || new String(operator).equals("<=") || new String(operator).equals(">") || new String(operator).equals(">=") || new String(operator).equals("<>") || new String(operator).equals("==")|| new String(operator).equals("||") || new String(operator).equals("&&"))
        {
          typeData = new TypeData("bool");
        }
      }

    }
    
    return typeData;
  }

}


