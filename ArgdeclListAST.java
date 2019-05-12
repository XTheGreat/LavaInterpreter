
/**
 * AST node for statement lists
 */ 
class ArgdeclListAST extends ArgdeclAST implements AST {
  ArgdeclAST first;
  ArgdeclAST rest;

  public ArgdeclListAST(ArgdeclAST f, ArgdeclAST r) {
    first = f;
    rest = r;
    // join values of types
    typedata.PutType(first.typedata);
    typedata.PutType(rest.typedata);
  }

  public String toString() {
    return(first + " , "+ rest);
  }

  public ST processSymbolTable(int scope)
  {
    // create new symbol table to hold entries
    SymbolTable argdeclST = new SymbolTable();
    argdeclST.scope = scope;
    argdeclST.PutData(first.processSymbolTable(argdeclST.scope));
    argdeclST.PutData(rest.processSymbolTable(argdeclST.scope));
    return argdeclST;
  }

}


