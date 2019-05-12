
/**
 * AST node for statement lists
 */ 
class FielddeclsStmtAST extends StmtAST implements AST {
  FielddeclAST fielddecls;
  StmtAST statement;
  MethoddeclAST optionalsemi;
	
  public FielddeclsStmtAST(FielddeclAST f, StmtAST s, MethoddeclAST o)
  {
    fielddecls = f;
    statement = s;
    optionalsemi = o;
  }

  public String toString() {
  	return("{" + "\n" + fielddecls + "\n" + statement + "}" + optionalsemi );
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    SymbolTable stmtST = new SymbolTable();
    stmtST.PutData(SA);
    stmtST.PutData(fielddecls.processSymbolTable(0));
    fielddecls.ExecuteMethod(stmtST);
    statement.ExecuteMethod(stmtST);


    int index = SA.symTableData.size();

    if(stmtST.symTableData.size() > index)
    {
      SymbolTableData functionData = new  SymbolTableData();
      functionData = stmtST.symTableData.get(index);
      for (int counter = 0; counter < SA.symTableData.size(); counter++)
      {
        if(SA.symTableData.get(counter).type.typeReturn == functionData.type.typeReturn && functionData.type.typeList.equals(SA.symTableData.get(counter).type.typeList) && new String(functionData.identifier).equals(SA.symTableData.get(counter).identifier))
        {
          SA.symTableData.set(counter, functionData);
        }
      }
    }
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    //no new symbol is define here, so we can return an empty symbol table
    SymbolTable stmtST = new SymbolTable();
    stmtST.scope = scope;
    SA.scope = scope;

    // create new table data entry for program
    TypeCheckList typeChecker = new TypeCheckList();

    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(stmtST, typeChecker);

    SA.PutData(stmtST);
    semanticAnalyzer.Join(fielddecls.SemanticAnalysis(stmtST.scope, SA));
    SA.PutData(fielddecls.processSymbolTable(stmtST.scope));
    semanticAnalyzer.Join(statement.SemanticAnalysis(stmtST.scope, SA));

    return semanticAnalyzer;
  }

  public ST processSymbolTable(int scope)
  {
    // create new symbol table to hold entries
    SymbolTable fieldST = new SymbolTable();
    fieldST.scope = scope;
    //enter new scope

    // Fielddecls and Methoddecls will inherit their new scope from program
    // hence membersdecls doesn't need to enter or exit scope.
    fieldST.PutData(fielddecls.processSymbolTable(fieldST.scope));
    fieldST.PutData(statement.processSymbolTable(fieldST.scope));

    //exit scope

    return fieldST;

  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList fTypeCheck = fielddecls.TypeCheck(symTable);
    TypeCheckList sTypeCheck = statement.TypeCheck(symTable);
    
    if(fTypeCheck == null &&  sTypeCheck == null)
    {
      return null;
    }
    else
    {
      // create new TypeCheckList
      TypeCheckList stmtTypeCheck = new TypeCheckList();
      stmtTypeCheck.Combine(fTypeCheck); // results of fields
      stmtTypeCheck.Combine(sTypeCheck); // results of methods
      return stmtTypeCheck;
    }

  }
  	
}


