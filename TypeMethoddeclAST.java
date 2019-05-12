
/**
 * AST node for statement lists
 */ 
import java.util.*;

class TypeMethoddeclAST extends MethoddeclAST implements AST {
  TypeAST type;
  String id;
  ArgdeclAST argdeclarations;
  FielddeclAST fielddeclarations;
  StmtAST statement;
  MethoddeclAST optionalsemi;
	
  public TypeMethoddeclAST(TypeAST t, String i, ArgdeclAST a, FielddeclAST f, StmtAST s, MethoddeclAST o)
  {
    type = t;
    id = i;
    argdeclarations = a;
    fielddeclarations = f;
    statement = s;
    optionalsemi = o;
  }

  public String toString() {
    return(type + " " + id + " " + "(" + argdeclarations + ")" + "\n" + "{" + fielddeclarations + "\n" + statement + "}" + optionalsemi );
  }

 public SemanticAnalyzer SemanticAnalysis(int scope, SymbolTable SA)
  {
    // create new symbol table to hold entries
      SymbolTable methodST = new SymbolTable();
      methodST.scope = scope;
      
      
      // create new table data entry for program
      TypeData typedata = new TypeData();
      typedata.SetReturnType(type.toString()); // set return type
      typedata.typeList = argdeclarations.typedata.typeList; //set input type to types of arg declarations
      SymbolTableData methodData = new SymbolTableData(id.toString(), typedata);

      methodST.PutData(methodData);

      SA.scope = methodST.scope;
      SA.PutData(methodST); //method data



      TypeCheckList typeChecker = new TypeCheckList();
      SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(methodST, typeChecker);

      
      methodST.enterScope(scope);
      methodST.ActivateScope(); // not really necessary if you think about it!
      
      //enter new scope for argdecls, fielddecls and statements
      methodST.PutData(argdeclarations.processSymbolTable(methodST.scope));
      
      semanticAnalyzer.typeChecker.Combine(argdeclarations.TypeCheck(SA)); // typecheck errors from args
      SA.PutData(argdeclarations.processSymbolTable(methodST.scope));

      SymbolTable fieldSA = new SymbolTable();
      fieldSA.PutData(SA);

      semanticAnalyzer.Join(fielddeclarations.SemanticAnalysis(methodST.scope, fieldSA)); // hide other scopes
      semanticAnalyzer.Join(statement.SemanticAnalysis(methodST.scope, fieldSA)); // type checking will occur here for declared fields and args in SA
      // exit scope of argdecls, fielddecls and statements
      semanticAnalyzer.DeactivateScope(methodST.scope);
      methodST.exitScope(scope);
      return semanticAnalyzer;
  }

  public ST processSymbolTable(int scope)
  {
    // create new symbol table to hold entries
    SymbolTable methodST = new SymbolTable();
    methodST.scope = scope;
    
    // create new table data entry for program
    TypeData typedata = new TypeData();
    typedata.SetReturnType(type.toString()); // set return type
    typedata.typeList = argdeclarations.typedata.typeList; //set input type to types of arg declarations
    SymbolTableData methodData = new SymbolTableData(id.toString(), typedata);
    methodData.node = this;
    // insert in symbol table the current.
    methodST.PutData(methodData);

    
    methodST.enterScope(scope);
    //enter new scope for argdecls, fielddecls and statements
    methodST.PutData(argdeclarations.processSymbolTable(methodST.scope));

    // create new symbol table for fielddeclarations and statements
    methodST.PutData(fielddeclarations.processSymbolTable(methodST.scope));
    methodST.PutData(statement.processSymbolTable(methodST.scope));

    // exit scope of argdecls, fielddecls and statements
    methodST.exitScope(scope);
    return methodST;
  }

  public TypeCheckList TypeCheck(ST symTable)
  {
    TypeCheckList fTypeCheck = fielddeclarations.TypeCheck(symTable);
    TypeCheckList sTypeCheck = statement.TypeCheck(symTable);
    
    if(fTypeCheck == null &&  sTypeCheck == null)
    {
      return null;
    }
    else
    {
      // create new TypeCheckList
      TypeCheckList methodsTypeCheck = new TypeCheckList();
      methodsTypeCheck.Combine(fTypeCheck); // results of fields
      methodsTypeCheck.Combine(sTypeCheck); // results of methods
      return methodsTypeCheck;
    }
  }

  public void ExecuteMethod(SymbolTable SA)
  {
    //it only adds to this if it is a main function
    // checks if function is already in symbol table, if not add it.
    if(InSymTable(SA)==false)
    {
      SymbolTable methodST = (SymbolTable) processSymbolTable(0);
      SA.PutData(methodST);
    }
    SA.PutData(argdeclarations.processSymbolTable(0));
    fielddeclarations.ExecuteMethod(SA);
    statement.ExecuteMethod(SA);
  }

  public void ExecuteMethodValue(SymbolTable SA, List<String> listof)
  {

    //arguments passing

    SymbolTable symTable = (SymbolTable) argdeclarations.processSymbolTable(0);
    for(int counter = 0; counter < symTable.symTableData.size(); counter++)
    {
      SymbolTableData symtData = symTable.symTableData.get(counter);
      symtData.value = listof.get(counter);
      symTable.symTableData.set(counter, symtData);
    }

    SA.PutData(symTable);
    fielddeclarations.ExecuteMethod(SA);
    statement.ExecuteMethod(SA);
  }

  public boolean InSymTable(SymbolTable SA)
  {
    TypeData typeof = new TypeData();
    typeof.typeList = argdeclarations.typedata.typeList;
    for(int counter = 0; counter < SA.symTableData.size(); counter++){
      // for no arguemnts
      if(new String(id).equals(SA.symTableData.get(counter).identifier) && new String(type.toString()).equals(SA.symTableData.get(counter).type.Evaluate()) && typeof.typeList.equals(SA.symTableData.get(counter).type.typeList))
      {
        return true;
      }
      // for arguments
      else if(new String(id).equals(SA.symTableData.get(counter).identifier) && new String(type.toString()).equals(SA.symTableData.get(counter).type.Evaluate()) && typeof.typeList.equals(SA.symTableData.get(counter).type.typeList))
      {
        return true;
      }
    }
    return false;
  }
  	
}


