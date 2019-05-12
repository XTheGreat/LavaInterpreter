
/**
 * AST node for statement lists
 */ 
class FieldListAST extends FielddeclAST implements AST {
  FielddeclAST first;
  FielddeclAST rest;

  public FieldListAST(FielddeclAST f, FielddeclAST r) {
    first = f;
    rest = r;
  }

  public String toString() {
    return(first+"\n"+rest);
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
      // create new symbol table to hold entries
      SymbolTable fieldST = new SymbolTable();
      fieldST.scope = scope;

      // typechecker
      TypeCheckList typeChecker = new TypeCheckList();
      // takes the results of symbol table and typechecking

      SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(fieldST, typeChecker);

      semanticAnalyzer.Join(first.SemanticAnalysis(fieldST.scope, SA));
      semanticAnalyzer.Join(rest.SemanticAnalysis(fieldST.scope, SA));

      return semanticAnalyzer;
  }

  public ST processSymbolTable(int scope)
  {
  	// create new symbol table to hold entries
  	SymbolTable fieldST = new SymbolTable();
    fieldST.scope = scope;
  	// Fielddecls and Methoddecls will inherit their new scope from program
  	// hence membersdecls doesn't need to enter or exit scope.
  	fieldST.PutData(first.processSymbolTable(fieldST.scope));
  	fieldST.PutData(rest.processSymbolTable(fieldST.scope));
  	return fieldST;
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    first.ExecuteMethod(SA);
    rest.ExecuteMethod(SA);
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
      TypeCheckList fieldsTypeCheck = new TypeCheckList();
      fieldsTypeCheck.Combine(fTypeCheck); // results of fields
      fieldsTypeCheck.Combine(rTypeCheck); // results of methods
      return fieldsTypeCheck;
    }

  }
    

}


