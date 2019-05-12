
/**
 * AST node for statement lists
 */ 
class TypeFielddeclAST extends FielddeclAST implements AST {
  TypeAST type;
  String id;
  FielddeclAST optionalexpression;
	
  public TypeFielddeclAST(TypeAST t, String i, FielddeclAST o)
  {
    type = t;
    id = i;
    optionalexpression = o;
  }

  public String toString() {
    return(type + " " + id + optionalexpression.toString() + ";" );
  }

  public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
    {
        // create new symbol table to hold entries
        ST sym  = processSymbolTable(scope); // returns type  id
        SymbolTable fieldST = (SymbolTable) sym;


        SA.scope = scope;
        SA.PutData(fieldST);

        TypeCheckList typeChecker = TypeCheck(SA); // type id = expr is an error

        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(fieldST, typeChecker);
        return semanticAnalyzer;
    }

  public ST processSymbolTable(int scope)
  {
    // create new symbol table to hold entries
    SymbolTable fieldST = new SymbolTable();
    fieldST.scope = scope;
    TypeData typedata = new TypeData(type.toString());
    SymbolTableData fieldData = new SymbolTableData(id.toString(), typedata);
    fieldST.PutData(fieldData);
    return fieldST;
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    int index = -1;
    for (int counter = 0; counter < SA.symTableData.size(); counter++) 
    {
      if(new String(SA.symTableData.get(counter).identifier).equals(id))
      {
        index = counter;
      }

    } 

    if(index!=-1)
      SA.symTableData.get(index).value = optionalexpression.Evaluate(SA);
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList fieldTypeCheck = new TypeCheckList();

    // compare type value with optional expression value
    TypeData declaredType = new TypeData(type.toString());
    TypeData optionalExpr = optionalexpression.ReturnTypeCheck(symTable); // will be null if no value is assigned, else it returns the type of the expr;

    if (optionalExpr == null ) // no value is assigned e.g. int x;
    {
      fieldTypeCheck = null;
    }
    else // value is assigned e.g. int x = 5;
    { 
      // compare the return type of declaredType with optionalExpr
      if (declaredType.CompareWith(optionalExpr) == true) // if it's the same type
      {
        fieldTypeCheck = null; // no error
      }
      else
      {
        if(new String(optionalExpr.Evaluate()).equals("int") || new String(optionalExpr.Evaluate()).equals("float") || new String(declaredType.Evaluate()).equals("int") || new String(declaredType.Evaluate()).equals("float")){}
        else
        {
          TypeData errorType = new TypeData("error");
          errorType.flag = "error: " + toString() + " " + optionalExpr.Evaluate() + " cannot be equal to " + declaredType.Evaluate();
          fieldTypeCheck.Combine(errorType);
        }
        
        
      }
      
    }

    return fieldTypeCheck;
  }
  	
}


