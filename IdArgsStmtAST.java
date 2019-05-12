
/**
 * AST node for binary expressions
 */ 

import java.util.*;

class IdArgsStmtAST extends StmtAST implements AST {
  String symbol;
  ArgsAST arguments;

  public IdArgsStmtAST(String s, ArgsAST a) {
    symbol = s;
    arguments = a;
  }

  public IdArgsStmtAST(String s) {
    symbol = s;
    arguments = null;
  }

  public String toString() {
  	if(arguments == null)
  	{
  		return(symbol + " (" + ");"); 
  	}
    else
    {
    	return(symbol + " (" + arguments + ");"); 
    }
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    //no new symbol is define here, so we can return an empty symbol table
    SymbolTable stmtST = new SymbolTable();

    // create new table data entry for program
    ST symbolTable = new SymbolTable();
    symbolTable = SA;

    TypeCheckList typeChecker = TypeCheck(symbolTable);
    if (arguments!=null)
      typeChecker.Combine(arguments.TypeCheck(SA));
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);

    return semanticAnalyzer;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList stmtTypeCheck = new TypeCheckList();

    TypeData declaredType = new TypeData();
    declaredType.PutType(ReturnTypeCheck(symTable)); // check that symbol exists

    if (declaredType == null) //not in symbol table
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + symbol + " is not declared!";
      stmtTypeCheck.Combine(errorType);
    }

    if(arguments!= null)
    {
      // it should also check if what args returns
      TypeData argsTypes = arguments.ReturnTypeCheck(symTable); // returns an array of the data types in the arguments
      // check if the argument data types are same as the one specified in the symbol table.

      //make ints coercible to floats
      for(int i = 0; i < declaredType.typeList.size(); i++)
      {
        if(new String("int").equals(declaredType.typeList.get(i)))
        {
          declaredType.typeList.set(i, "float");
        }
      }
      for(int i = 0; i < argsTypes.typeList.size(); i++)
      {
        if(new String("int").equals(argsTypes.typeList.get(i)))
        {
          argsTypes.typeList.set(i, "float");
        }
      }


      if (declaredType.typeList.equals(argsTypes.typeList))
        {} //no error, function definition and use are similar
      else
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: " + symbol + " function call arguments are different from declared types.";
        stmtTypeCheck.Combine(errorType);
      }
    }

    return stmtTypeCheck;

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


  public void ExecuteMethod(SymbolTable SA)
  {
    //evaluate statement;
    int index = -1;
    int MainIndex = 0;
    // arguments

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
      
    }
  }

  


}


