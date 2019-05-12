
/**
 * AST node for statement lists
 */ 
class MethodListAST extends MethoddeclAST implements AST {
  MethoddeclAST first;
  MethoddeclAST rest;

  public MethodListAST(MethoddeclAST f, MethoddeclAST r) {
    first = f;
    rest = r;

  }

  public String toString() {
    return(first+"\n"+rest);
  }

 public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA) 
  {
      // create new symbol table to hold entries
      SymbolTable methodST = new SymbolTable();
      methodST.scope = scope;
      // typechecker
      TypeCheckList typeChecker = new TypeCheckList();
      // takes the results of symbol table and typechecking
      SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(methodST, typeChecker);

      semanticAnalyzer.Join(first.SemanticAnalysis(methodST.scope, SA)); // whatever scope is passed in
      semanticAnalyzer.Join(rest.SemanticAnalysis(methodST.scope, SA)); // pass in added variables in SA
      
      return semanticAnalyzer;
  }


  public ST processSymbolTable(int scope)
  {
  	// create new symbol table to hold entries
  	SymbolTable methodST = new SymbolTable();
    methodST.scope = scope;
  	methodST.PutData(first.processSymbolTable(methodST.scope));
  	methodST.PutData(rest.processSymbolTable(methodST.scope));
  	return methodST;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList fTypeCheck = first.TypeCheck(symTable);
    TypeCheckList rTypeCheck = rest.TypeCheck(symTable);
    
    if(fTypeCheck == null &&  rTypeCheck == null)
    {
      return null;
    }
    else
    {
      // create new TypeCheckList
      TypeCheckList methodsTypeCheck = new TypeCheckList();
      methodsTypeCheck.Combine(fTypeCheck); // results of fields
      methodsTypeCheck.Combine(rTypeCheck); // results of methods
      return methodsTypeCheck;
    }

  }

  public void ExecuteMethod(SymbolTable SA)
  {
    // only starts execution from main
    TypeMethoddeclAST method = (TypeMethoddeclAST) first;
    // void main()
    if(new String(method.id).equals("main") && new String(method.type.toString()).equals("void") && new String(method.argdeclarations.toString()).equals(""))
    {
      first.ExecuteMethod(SA);
    }
    else
    {
      SA.PutData(first.processSymbolTable(0));
      rest.ExecuteMethod(SA);
    }
  }

}


