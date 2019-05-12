 /* General interface for nodes in symbol table.             
 */
abstract class ST {
	int scope;
	int scopelevel = 0; // will be used to track the highest scope value for program
	String symType; // this is used to check whether this is an entry or a symbol table that points to entries.
	boolean exception = false; // if TRUE, there are errors in the symbol table.

	// enters a new scope
	public void enterScope(int s)
	{
		scope = scope + 1;
	}
	// exits current scope
	public void exitScope(int s)
	{
		scope = scope - 1;
	}

	// enters a new scope and checks the inscope
	public void ActivateScope()
	{
		// does nothing here!
	}
	// exits current scope and unchecks the inscope
	public void DeactivateScope()
	{
		// does nothing here!
	}
	// exits current scope and unchecks the inscope
	public void DeactivateScope(int val)
	{
		// does nothing here!
	}

	abstract public <ST> ST getSymData();

	// to dump output
	abstract public String dump();

	public String getExceptions()
	{ return null; }
}