
/**
 * AST node for statement lists
 */ 
class MemberdeclListAST extends MemberdeclAST implements AST {
  FielddeclAST fields;
  MethoddeclAST methods;

  public MemberdeclListAST(FielddeclAST f, MethoddeclAST m) {
    fields = f;
    methods = m;
  }

  public String toString() {
  	return(fields+"\n"+methods);
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    // create new symbol table to hold entries
    SymbolTable memberST = new SymbolTable();
    memberST.scope = scope;
    // typechecker
    TypeCheckList typeChecker = new TypeCheckList();
    SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(memberST, typeChecker);
    SA.scope = memberST.scope;
    SymbolTable kST = new SymbolTable();
    semanticAnalyzer.Join(fields.SemanticAnalysis(memberST.scope, SA));
    semanticAnalyzer.Join(methods.SemanticAnalysis(memberST.scope, SA));
    return semanticAnalyzer;
  }

  public ST processSymbolTable(int scope)
  {
  	// create new symbol table to hold entries
  	SymbolTable memberST = new SymbolTable();
    memberST.scope = scope;
  	// Fielddecls and Methoddecls will inherit their new scope from program
  	// hence membersdecls doesn't need to enter or exit scope.
  	memberST.PutData(fields.processSymbolTable(memberST.scope));
  	memberST.PutData(methods.processSymbolTable(memberST.scope));

  	return memberST;

  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList fTypeCheck = fields.TypeCheck(symTable);
    TypeCheckList mTypeCheck = methods.TypeCheck(symTable);
    
    if(fTypeCheck == null &&  mTypeCheck == null)
    {
      return null;
    }
    else
    {
      // create new TypeCheckList for members
      TypeCheckList membersTypeCheck = new TypeCheckList();
      membersTypeCheck.Combine(fTypeCheck); // results of fields
      membersTypeCheck.Combine(mTypeCheck); // results of methods
      return membersTypeCheck;
    }

  } 

  public void ExecuteMethod(SymbolTable SA)
  {
    SymbolTable fieldsST = (SymbolTable) fields.processSymbolTable(0);
    SA.PutData(fieldsST);

    fields.ExecuteMethod(SA);
    methods.ExecuteMethod(SA);
  }

}