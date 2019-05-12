
/**
 * AST node for binary expressions
 */ 
class TypeIdArgdeclAST extends ArgdeclAST implements AST {
  TypeAST type;
  ExprAST ident;

  public TypeIdArgdeclAST(TypeAST t, ExprAST i) {
    type = t;
    ident = i;

    // include type
    typedata.PutType(type.toString());
  }

  public String toString() {
    return(type + " " + ident); 
  }

  public ST processSymbolTable(int scope)
  {
    // create new symbol table to hold entries
    SymbolTable argdeclST = new SymbolTable();
    argdeclST.scope = scope;
    TypeData typedata = new TypeData(type.toString());
    SymbolTableData argdeclData = new SymbolTableData(ident.toString(), typedata);
    argdeclST.PutData(argdeclData);
    return argdeclST;
  }

}


