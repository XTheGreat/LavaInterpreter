
/**
 * Type Data is a structure that can be 
 * singular like float, int, char, bool 
 * or a list of types that produce a type like < int, char > -> bool
 */ 

import java. util.*;

class TypeData
{
	ArrayList<String> typeList = new ArrayList<String>();
	String typeReturn;
	int size = -1;
	String flag = ""; // useful only if there's an error
	boolean isFinal = false;

	// default constructor, especially for singular types
	public TypeData()
	{
		typeReturn = null;
	}

	public TypeData(String p)
	{
		typeList.add(ConvertType(p));
		typeReturn = null;
	}
	
	// put functions for one up to four values
	public void PutType(String p){
		typeList.add(ConvertType(p));
	}

	public void PutType(String p1, String p2){
		typeList.add(ConvertType(p1));
		typeList.add(ConvertType(p2));
	}

	public void PutType(String p1, String p2, String p3){
		typeList.add(ConvertType(p1));
		typeList.add(ConvertType(p2));
		typeList.add(ConvertType(p3));
	}

	public void PutType(String p1, String p2, String p3, String p4){
		typeList.add(ConvertType(p1));
		typeList.add(ConvertType(p2));
		typeList.add(ConvertType(p3));
		typeList.add(ConvertType(p4));
	}

	// function to join lists of types
	public void PutType(TypeData t)
	{
		if(t!=null)
		{
			typeList.addAll(t.typeList);
			typeReturn = t.typeReturn;
			size = t.size;
			flag = t.flag;
		}
		
	}

	public void SetReturnType(String p){
		typeReturn = ConvertType(p);
	}

	// returns the type value as a single value or 
	// an array of types that return a type
	public String GetType() {
		if(typeReturn == null)
		{
			return(typeList.toString());
		}
		else
		{
			// show the list of types and it's return type
			return (typeList.toString() + " : " + typeReturn);
		}
	}

	// for arrays, it would be the defined type e.g. int x[3], if you evaluate the values of x they should be integers
	// for char func(intx, int y), if you evaluate func, it should be charand whatever it returns should be char
	// for int m, evaluate returns int
	public String Evaluate()
	{
		// returns the data type of the object
		if(typeReturn == null)
		{
			return(typeList.get(0)); // if it's a singular item, just return that single item
		}
		else
		{
			return (typeReturn); // if it returns a type, then it's type is what it returns
		}
	}

	public boolean CompareWith(TypeData f)
	{
		boolean val = false; 
		if(new String(f.Evaluate()).equals(Evaluate())) // same types
		{
			val = true;
		}
		return val;
	}

	public boolean CompareWith(String f)
	{
		boolean val = false; 
		if(new String(f).equals(Evaluate())) // same types
		{
			val = true;
		}
		return val;
	}


	// This function switches the given type to its valid type!
	private String ConvertType(String p)
	{
		String value;
		// check that the type is actually valid
		switch(p)
		{
			case "int" :
				value = "int";
			break;

			case "char" :
				value = "char";
			break;

			case "float" :
				value = "float";
			break;

			case "bool" :
				value = "bool";
			break;

			case "string" :
				value = "string";
			break;

			case "arrint" :
				value = "arrint";
			break;

			case "arrchar" :
				value = "arrchar";
			break;

			case "arrfloat" :
				value = "arrfloat";
			break;

			case "arrbool" :
				value = "arrbool";
			break;

			case "arrstring" :
				value = "arrstring";
			break;

			case "class" :
				value = "class";
			break;

			case "void" :
				value = "void";
			break;

			case "error" :
				value = "error";
			break;

			default : 
				value = null;
		}

		return value;

	}
}