
/**
 * AST node for statement lists
 */ 
class ProgramListAST extends ProgramAST implements AST {
  ExprAST ident;
  MemberdeclAST members;


  public ProgramListAST(ExprAST i, MemberdeclAST m) {
    ident = i;
    members = m;
  }

  public String toString() {
    return("class " + ident + "\n" + "{" + members + "\n" + "}\n");
  }

 public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    // create new symbol table to hold entries
    SymbolTable programST = new SymbolTable();    // create new table data entry for program
    TypeData typedata = new TypeData("class");
    SymbolTableData programData = new SymbolTableData(ident.toString(), typedata);
    // insert in symbol table the current.
    programST.PutData(programData);
    // typechecker
    TypeCheckList typeChecker = new TypeCheckList();

    // takes the results of symbol table and typechecking
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(programST, typeChecker);

    programST.enterScope(scope); // increment integer
    programST.ActivateScope(); // makes sure that this scope is currently active

    SA.scope = programST.scope;
    // add current data to symbol table parse in
    SA.PutData(programST);
    semanticAnalyzer.Join(members.SemanticAnalysis(programST.scope, SA));
    programST.DeactivateScope(); // makes sure that this scope is inactive
    programST.exitScope(scope); // decrement integer
    return semanticAnalyzer;
  }

  public ST processSymbolTable(int scope)
  {
  	//ideally scope should start from zero and be increased to one on first enter scope
  	// create new symbol table to hold entries
  	SymbolTable programST = new SymbolTable();
    // create new table data entry for program
    TypeData typedata = new TypeData("class");
    SymbolTableData programData = new SymbolTableData(ident.toString(), typedata);
    // insert in symbol table the current.
    programST.PutData(programData);

  	//enter new scope
  	programST.enterScope(scope);
  	// member declarations that points to another symbol table
  	programST.PutData(members.processSymbolTable(programST.scope));

  	//exit current scope
  	programST.exitScope(scope);

  	return programST;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    // typecheck members
    return members.TypeCheck(symTable);
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    members.ExecuteMethod(SA);
  }

}


