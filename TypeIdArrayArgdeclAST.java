
/**
 * AST node for binary expressions
 */ 
class TypeIdArrayArgdeclAST extends ArgdeclAST implements AST {
  TypeAST type;
  ExprAST ident;

  public TypeIdArrayArgdeclAST(TypeAST t, ExprAST i) {
    type = t;
    ident = i;

    // include type
    typedata.PutType("arr"+type.toString());
  }

  public String toString() {
    return(type + " " + ident + "[]"); 
  }

  public ST processSymbolTable(int scope)
  {
    // create new symbol table to hold entries
    SymbolTable argdeclST = new SymbolTable();
    argdeclST.scope = scope;
    TypeData typedata = new TypeData("arr"+type.toString());
    SymbolTableData argdeclData = new SymbolTableData(ident.toString(), typedata);
    argdeclST.PutData(argdeclData);
    return argdeclST;
  }

}


