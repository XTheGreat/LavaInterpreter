
/**
 * AST node for binary expressions
 */ 

import java.util.*;

class NameStmtAST extends StmtAST implements AST {
  ExprAST symbol;
  ExprAST expression;

  public NameStmtAST(ExprAST s, ExprAST e) {
  	symbol = s;
    expression = e;
    
  }

  public String toString() {
    return(symbol + " = " + expression + ";"); 
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    int index = -1;
    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(new String(SA.symTableData.get(counter).identifier).equals(symbol.toString()))
      {
        // remember to split expression to remove () e.g. for foo() or foo(a,b) , it should be looking for just foo
        index = counter;
      }
    }

    if(index!=-1)
    {
      SA.symTableData.get(index).value = expression.Evaluate(SA);
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
    typeChecker.Combine(expression.TypeCheck(SA));
    
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);
    return semanticAnalyzer;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList stmtTypeCheck = new TypeCheckList();
    stmtTypeCheck.Combine(symbol.TypeCheck(symTable));
    stmtTypeCheck.Combine(expression.TypeCheck(symTable));

    TypeData declaredType = symbol.ReturnTypeCheck(symTable);
    TypeData equalType = expression.ReturnTypeCheck(symTable);

    if(declaredType!=null)
    {
      if(declaredType.isFinal == true)
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: " + symbol.toString() + " is declared as final. It cannot be reassigned a value.";
        stmtTypeCheck.Combine(errorType);
      }

    }

    if (declaredType == null) //not in symbol table
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + symbol + " in " + toString() + " is not declared!";
      stmtTypeCheck.Combine(errorType);
    }
    else if (equalType == null) //not in symbol table
    {
      // COERCION!!
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + expression.toString() + " in " + toString() + " is not declared!";
      stmtTypeCheck.Combine(errorType);
    }
    else if (new String(declaredType.Evaluate()).equals(equalType.Evaluate()))
    {
      // storing value in symbol table
      WriteEntry(symTable);
    }
    else 
    {
      
      if(new String(equalType.Evaluate()).equals("int") || new String(equalType.Evaluate()).equals("float") || new String(declaredType.Evaluate()).equals("int") || new String(declaredType.Evaluate()).equals("float"))
      {
        // storing value in symbol table
        WriteEntry(symTable);
      }
      else
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: " + toString() + " " + equalType.Evaluate() + " cannot be equal to " + declaredType.Evaluate();
        stmtTypeCheck.Combine(errorType);
      }
    }

    return stmtTypeCheck;
  }

  public void WriteEntry (ST symTable)
  {
    int index = -1;
    SymbolTable SA = (SymbolTable) symTable;
    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(new String(SA.symTableData.get(counter).identifier).equals(symbol.toString()))
      {
        index = counter;
      }
    }
    if(index != -1)
      SA.symTableData.get(index).value =  expression.toString();
  }

}


