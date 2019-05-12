
/**
 * AST node for binary expressions
 */ 
import java.util.*;

class IdArgsExprAST extends ExprAST implements AST {
  String symbol;
  ArgsAST arguments;

  public IdArgsExprAST(String s, ArgsAST a) {
    symbol = s;
    arguments = a;
  }

  public IdArgsExprAST(String s) {
    symbol = s;
    arguments = null;
  }

  public String toString() {
  	if(arguments == null)
  	{
  		return(symbol + " (" + ")"); 
  	}
  	else
  	{
  		return(symbol + " (" + arguments + ")"); 
  	}
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList exprTypeCheck = new TypeCheckList();

    TypeData declaredType = ReturnTypeCheck(symTable); // check that symbol exists

    if (declaredType == null) //not in symbol table
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + symbol + " is not declared!";
      exprTypeCheck.Combine(errorType);
    }

    // it should also check if what args returns
    if(arguments != null)
    {
      exprTypeCheck.Combine(arguments.TypeCheck(symTable));
      // check if the argument data types are same as the one specified in the symbol table.
      if(declaredType!=null)
      {
        if (declaredType.typeList.equals(arguments.ReturnTypeCheck(symTable).typeList))
        {} //no error, function definition and use are similar
        else
        {
          TypeData errorType = new TypeData("error");
          errorType.flag = "error: " + symbol + " function call arguments are different from declared types.";
          exprTypeCheck.Combine(errorType);
        }
      }
    }
    
    return exprTypeCheck;

  }

  public TypeData ReturnTypeCheck(ST symTable)
  {
    TypeData typeData = null;

    SymbolTable SA = (SymbolTable) symTable;

    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(new String(SA.symTableData.get(counter).identifier).equals(symbol))
      {
        typeData = SA.symTableData.get(counter).type;
      }

    } 
    // Looks up the Symbol Table for data type of symbol
    return typeData;
  }

  public String Evaluate(ST symTable)
  {
    //evaluate statement;
    int index = -1;
    int MainIndex = 0;
    // arguments
    SymbolTable SA = (SymbolTable) symTable;

    if(arguments == null) // for empty
    {
      for (int counter = 0; counter < SA.symTableData.size(); counter++) 
      {
        if(new String(SA.symTableData.get(counter).identifier).equals(symbol) && SA.symTableData.get(counter).type.typeList.size() == 0)
        {
          index = counter;
        }

        // look for main
        if(new String(SA.symTableData.get(counter).identifier).equals("main")) // main function found
        {
          // is it void? 
          if(new String(SA.symTableData.get(counter).type.Evaluate()).equals("void")) // main is void
          {
            // it doesn't have arguments?
            if(SA.symTableData.get(counter).type.typeList.size() == 0) // main has no arguments
            {
              MainIndex = counter;
            }
          }
        }
      } 
    }
    else
    {
      TypeData argsTypes = arguments.ReturnTypeCheck(SA);
      for (int counter = 0; counter < SA.symTableData.size(); counter++) 
      {
        
        if(new String(SA.symTableData.get(counter).identifier).equals(symbol) && argsTypes.typeList.equals(SA.symTableData.get(counter).type.typeList))
        {
          index = counter;
        }

        // look for main
        if(new String(SA.symTableData.get(counter).identifier).equals("main")) // main function found
        {
          // is it void? 
          if(new String(SA.symTableData.get(counter).type.Evaluate()).equals("void")) // main is void
          {
            // it doesn't have arguments?
            if(SA.symTableData.get(counter).type.typeList.size() == 0) // main has no arguments
            {
              MainIndex = counter;
            }
          }
        }

      }
    }
    

    if(index!=-1)
    {
      if(arguments == null)
      {
        SA.symTableData.get(index).node.ExecuteMethod(SA);
      }
      else
      {
        List<String> result = Arrays.asList(arguments.toString().split("\\s*,\\s*"));
        SA.symTableData.get(index).node.ExecuteMethodValue(SA, result);
      }
      
      return SA.symTableData.get(MainIndex).value;
    }

    return null;
  }

}


