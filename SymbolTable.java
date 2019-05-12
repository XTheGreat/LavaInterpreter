
/**
 * Symbol table  is a list or collection of  symboltabledata entries
 */ 

import java.util.*;

class SymbolTable extends ST
{
	ArrayList<SymbolTableData> symTableData = new ArrayList<SymbolTableData>(); // an entry in the symbol table	// can have as child another symbol table


	//create new symbol table and set it's scope
	public SymbolTable()
	{
		symType = "sym_table";
	}

	// put in data entry that is singular or points to another symbol table!
	public void PutData(ST s)
	{
		if(s != null)
		{
			//s.scope = scope; //set the entries scope to table's scope level

			if(s.symType == "sym_table")
			{
				symTableData.addAll(s.getSymData());
			}
			else
			{
				s.scope = scope;
				symTableData.add( (SymbolTableData) s);
			}
			
		}
		
	}

	public ArrayList<SymbolTableData> getSymData()
	{
		return symTableData;
	}

	public void GetScopeLevel()
	{
		int symbolscope;
		scopelevel = 0;
		for (int counter = 0; counter < symTableData.size(); counter++) 
		{
			symbolscope = symTableData.get(counter).scope; // local scope value
			if(symbolscope > scopelevel)
			{
				scopelevel = symbolscope;
			}
		} 
	}

	public String dump()
	{
		String symTableList = "";

		//get each symboltable data in arraylist and print their dump.
		for (int counter = 0; counter < symTableData.size(); counter++) 
		{
			symTableList = symTableList + symTableData.get(counter).dump() + "\n";
		} 
		return symTableList;
	}

	// enters a new scope and checks the inscope
	public void ActivateScope()
	{
		//get each symboltable data in arraylist and set them in scope
		for (int counter = 0; counter < symTableData.size(); counter++) 
		{
			symTableData.get(counter).Inscope = true;
		}
	}
	
	// exits current scope and unchecks the inscope
	public void DeactivateScope()
	{
		//get each symboltable data in arraylist and set them in scope
		for (int counter = 0; counter < symTableData.size(); counter++) 
		{
			symTableData.get(counter).Inscope = false;
		}
	}

	// deactivates a specific scope
	public void DeactivateScope(int val)
	{
		//get each symboltable data in arraylist and set them in scope
		for (int counter = 0; counter < symTableData.size(); counter++) 
		{
			if (symTableData.get(counter).scope == val)
				symTableData.get(counter).Inscope = false;
		}
	}

	public String getExceptions()
	{
		String exceptionError;

		GetScopeLevel();

		if (scopelevel == 0)
		{
			exceptionError = null; // at class level
		}
		else
		{
			exceptionError = "";
			int localscope = 0;
			int symbolscope;

			String storedvariables = ""; // using a string to track variable names as it's easy to search
			String currentvar, methoddef;

			// iterate through the list to check for multiple declarations
			// in the same scope
			while (localscope <= scopelevel)
			{
				// check for exception in current scope before checking the next scope
				for (int counter = 0; counter < symTableData.size(); counter++) 
				{
					symbolscope = symTableData.get(counter).scope; // local scope value
					if(symbolscope == localscope)
					{
						currentvar = symTableData.get(counter).identifier + "*";

						if(storedvariables.contains(currentvar)) // check if variable is already in the table
						{
							// check if it's a function and it's been overwritten
							if(symTableData.get(counter).type.typeReturn != null ) // it's a function
							{
								// define what function looks like
								methoddef = currentvar + "~~~"; // methoddef specifies that this is a function i.e x*~~~
								//check if a function x already exits
								if(storedvariables.contains(methoddef)) 
								{
									// since defintion exists 
									// check if it's same definition. Note: same definition means it returns  the same type e.g. int x()
									methoddef = currentvar + "~~~" + symTableData.get(counter).type.typeReturn + "*"; // override will have same return type
									// check if it's an override
									if(storedvariables.contains(methoddef)) {
										methoddef = currentvar + "~~~" + symTableData.get(counter).type.typeReturn + "*" + symTableData.get(counter).type.typeList + "*";
										if(storedvariables.contains(methoddef)) // Error! method is being redeclared!
										{
											exception = true;
											exceptionError = exceptionError + "error: '" + symTableData.get(counter).identifier + "' is a multiply declared method in scope (" + localscope +").\r\n";
										}
										else // method is being overwritten
										{
											storedvariables = storedvariables + methoddef;
										}
									}
									//else this is an exception, function is redefined to return as another type e.g. char x()
									else
									{
										exception = true;
										exceptionError = exceptionError + "error: '" + symTableData.get(counter).identifier + "', ambiguous method declaration of different return type in scope (" + localscope +").\r\n";

									}

								}
								else
								{
									//add new function x definition with it's args type and return type
									// add type
									methoddef = currentvar + "~~~" + symTableData.get(counter).type.typeReturn + "*" + symTableData.get(counter).type.typeList + "*";
									storedvariables = storedvariables + methoddef;

								}
							}
							// it's a variable
							// variable is being redeclared
							else
							{
								exception = true;
								exceptionError = exceptionError + "error: '" + symTableData.get(counter).identifier + "' is multiply declared in scope (" + localscope +").\r\n";
							}
						}
						else
						{
							// if it's a function
							if(symTableData.get(counter).type.typeReturn != null ) // it's a function
							{
								methoddef = currentvar + "~~~" + symTableData.get(counter).type.typeReturn + "*" + symTableData.get(counter).type.typeList + "*";
								storedvariables = storedvariables + methoddef;
							}
							else
							{
								// saved as type + variable name
							storedvariables = storedvariables + currentvar;
						}
						}
					}

					else if(symbolscope < localscope && symbolscope != 0)
					{
						storedvariables = ""; // this scope has ended!
					}
					else{}
				
				} 

				// reset flag and tracked variables
				storedvariables = "";

				localscope += 1; // move to next scope
			}

			if (exceptionError != "")
			{
				exceptionError = "\r\nSymbol Table Exceptions:\r\n" + exceptionError;
			}
		}
		return exceptionError;
	}

	public boolean HasMainFunc() 
	{
		boolean found = false;
		// checks to find a main function in the table
		for (int counter = 0; counter < symTableData.size(); counter++) 
		{
			if(new String(symTableData.get(counter).identifier).equals("main")) // main function found
			{
				// is it void? 
				if(new String(symTableData.get(counter).type.Evaluate()).equals("void")) // main is void
				{
					// it doesn't have arguments?
					if(symTableData.get(counter).type.typeList.size() == 0) // main has no arguments
					{
						found = true;
					}
				}
			}
		} 
		return found;
	}
}