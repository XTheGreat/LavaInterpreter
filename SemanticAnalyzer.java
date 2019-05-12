
/**
 * This program points to results of symbol table and type checking!
 */ 

class SemanticAnalyzer
{
	SymbolTable symbolTable;
	TypeCheckList typeChecker;
	public SemanticAnalyzer()
	{
		symbolTable = null;
		typeChecker = null;
	}

	public SemanticAnalyzer(SymbolTable s, TypeCheckList t)
	{
		symbolTable = s;
		typeChecker = t;
	}

	public void Join(SemanticAnalyzer s)
	{
		if (s != null)
		{
			symbolTable.symTableData.addAll(s.symbolTable.symTableData);
			typeChecker.Combine(s.typeChecker);
		}
	}

	public void DeactivateScope(int scope)
	{
		symbolTable.DeactivateScope(scope);
	}

	public boolean HasError() // returns true if there are errors in symbol table or type checking
	{
		boolean found = false;
		if(symbolTable.exception || typeChecker.typeList.size() > 0)
		{
			found = true;
		}
		return found;
	}
}