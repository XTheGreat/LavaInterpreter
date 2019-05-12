
/**
 * This program : Symbol table Entry is a data entry of type < z, double>
 */ 

class SymbolTableData extends ST
{
	String identifier;
	TypeData type;
	String value =  null;
	AST node;
	boolean Inscope = true; // checks if this symbol is in current scope or not!

	public SymbolTableData(String i, TypeData t)
	{
		identifier = i;
		type = t;
		symType = "sym_data";
	}

	public SymbolTableData()
	{
		symType = "sym_data";
	}

	public ST getSymData()
	{
		return this;
	}

	public String dump()
	{
		if(type.size==-1)
			return ("<" + identifier + ", " + type.GetType() + "> (Value: " + value + ")");
		else
			return ("<" + identifier + ", " + type.GetType() + "> (Scope: " + scope + ", Array Size: " + type.size + ")");
	}
}