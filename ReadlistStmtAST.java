/**
 * AST node for a number
 */ 

// import input/output modules
import java.util.*;


class ReadlistStmtAST extends StmtAST implements AST {
  ExprAST name;
  ReadlistStmtAST readlist;

  public ReadlistStmtAST(ExprAST n) {
    name = n;
    readlist = null;
  }

  public ReadlistStmtAST(ExprAST n, ReadlistStmtAST r) {
    name = n;
    readlist = r;
  }

  public String toString() {
    if(readlist == null)
    {
    	return (""+name);
    }
    else
    {
    	return (name + " , " + readlist);
    }
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    String s = "";
    int index = -1;
    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(new String(SA.symTableData.get(counter).identifier).equals(name.toString()))
      {
        index = counter;
      }
    }

    if(index!=-1)
    {
      SA.symTableData.get(index).value = s;
    }

    if(readlist != null)
    {
      readlist.ExecuteMethod(SA);
    }
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    //no new symbol is define here, so we can return an empty symbol table
    SymbolTable stmtST = new SymbolTable();
    TypeCheckList typeChecker = TypeCheck(SA);
    
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);
    return semanticAnalyzer;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList stmtTypeCheck = new TypeCheckList();

    TypeData declaredType = name.ReturnTypeCheck(symTable); // check that symbol exists and is boolean

    if(declaredType!=null)
    {
      if(new String("arrint").equals(declaredType.Evaluate()) || new String("arrfloat").equals(declaredType.Evaluate()) || new String("arrbool").equals(declaredType.Evaluate()) || new String("arrchar").equals(declaredType.Evaluate()) || new String("arrstring").equals(declaredType.Evaluate()) )
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: variable cannot be entire array at read statement";
        stmtTypeCheck.Combine(errorType);
      }
      else if(declaredType.typeReturn != null)
      {
        TypeData errorType = new TypeData("error");
        errorType.flag = "error: variable cannot be a method name at read statement";
        stmtTypeCheck.Combine(errorType);
      }
    }
    if(readlist!=null)
      stmtTypeCheck.Combine(readlist.TypeCheck(symTable));
    return stmtTypeCheck;

  }

}

