
/**
 * AST node for statement lists
 */ 
class TypeArrayFielddeclAST extends FielddeclAST implements AST {
  TypeAST type;
  String id;
  ExprAST integer;
	
  public TypeArrayFielddeclAST(TypeAST t, String i, ExprAST x)
  {
    type = t;
    id = i;
    integer = x;
  }

  public String toString() {
  	return(type + " " + id + " [" + integer + "];");
  }

  // excited for my first type checking! Yipee
  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
      // create new symbol table to hold entries
      ST sym  = processSymbolTable(scope); // doing this since it's not leading to any new scope
      SymbolTable fieldST = new SymbolTable();
      fieldST.PutData(sym);
      // typechecker
      SA.PutData(fieldST);
      TypeCheckList typeChecker = TypeCheck(SA); // checks that this is an int or it returns an error
      SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(fieldST, typeChecker);
      return semanticAnalyzer;
  }

  public ST processSymbolTable(int scope)
  {
    // create new symbol table to hold entries
    SymbolTable fieldST = new SymbolTable();
    fieldST.scope = scope;
    TypeData typedata = new TypeData("arr"+type.toString());
    typedata.size = Integer.parseInt(integer.toString());
    SymbolTableData fieldData = new SymbolTableData(id.toString(), typedata);
    fieldST.PutData(fieldData);
    return fieldST;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList fieldTypeCheck = new TypeCheckList();
    // compare type value with optional expression value
    TypeData declaredType = new TypeData(type.toString());
    TypeData intlit = integer.ReturnTypeCheck(symTable); // will be null if no value is assigned, else it returns the type of the expr;


    if ( new String("int").equals(intlit.Evaluate()) ) // array number is integer
    {
      fieldTypeCheck = null; // no error
    }
    else // error, array number must be an integer
    {
      TypeData errorType = new TypeData("error");
      errorType.flag = "error: " + toString() + " Array size cannot be of type " + intlit.Evaluate();
      fieldTypeCheck.Combine(errorType);
    }

    return fieldTypeCheck;

  }
}


