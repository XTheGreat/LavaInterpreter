
/**
 * AST node for statement lists
 */ 
class NameOpStmtAST extends StmtAST implements AST {
  ExprAST symbol;
  String operator;
	
  public NameOpStmtAST(ExprAST s, String o)
  {
    symbol = s;
    operator = o;
  }

  public String toString() {
  	return("(" + symbol  + " " + operator + operator + ")");
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    TypeData dataType = ReturnTypeCheck(SA);
    String s1 = dataType.Evaluate();
    int index = -1;
    if(new String(s1).equals("int") || new String(s1).equals("float"))
    {
      for (int counter = 0; counter < SA.symTableData.size(); counter++) 
      {
        if(new String(SA.symTableData.get(counter).identifier).equals(symbol.toString()))
        {
          index = counter;
        }

      }

      if(index!=-1)
      {
        // update value 
        switch(operator)
        {
          case "+" :
            if(new String(s1).equals("float"))
            {
              Double result = Double.parseDouble(SA.symTableData.get(index).value);
              result = result + 1;
              SA.symTableData.get(index).value = String.format("%f",result) ;
            }
            else
            {
              //integer
              int result = Integer.parseInt(SA.symTableData.get(index).value);
              result = result + 1;
              SA.symTableData.get(index).value = Integer.toString(result) ;
            }
            break;
          case "-" :
            if(new String(s1).equals("float"))
            {
              Double result = Double.parseDouble(SA.symTableData.get(index).value);
              result = result - 1;
              SA.symTableData.get(index).value = String.format("%f",result) ;
            }
            else
            {
              //integer
              int result = Integer.parseInt(SA.symTableData.get(index).value);
              result = result - 1;
              SA.symTableData.get(index).value = Integer.toString(result) ;
            }
            break;
          default : 
            break;
        }
      }
      
    }
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    //no new symbol is define here, so we can return an empty symbol table
    SymbolTable stmtST = new SymbolTable();

    // create new table data entry for program
    ST symbolTable = new SymbolTable();
    symbolTable = SA;

    TypeCheckList typeChecker = new TypeCheckList();
    typeChecker.Combine(TypeCheck(SA)); // this is where the expression type checking branches out
    typeChecker.Combine(symbol.TypeCheck(SA));
    
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);
    return semanticAnalyzer;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList stmtTypeCheck = new TypeCheckList();

    TypeData declaredType = ReturnTypeCheck(symTable); // check that symbol exists and is boolean

    if (declaredType == null) //not in symbol table
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + symbol + " is not declared!";
      stmtTypeCheck.Combine(errorType);
    }

    else if(new String("int").equals(declaredType.Evaluate()) || new String("float").equals(declaredType.Evaluate()) ) {}
    else // must be an integer
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + symbol.toString() + " is of type "+ declaredType.Evaluate() + ", " + operator + operator + " can only be applied to int/float values.";
      stmtTypeCheck.Combine(errorType);
    }
    
    return stmtTypeCheck;

  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    return symbol.ReturnTypeCheck(symTable);
  }
  	
}


