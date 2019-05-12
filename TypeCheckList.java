// TypeCheckList will be an array of type datas that will have a flag for error or not
// and a description

import java. util.*;


class TypeCheckList
{
	ArrayList<TypeData> typeList = new ArrayList<TypeData>(); // will only store types if an error is found!
	// if this type is an error type, flag is set to true and description is attached to this type


	public TypeCheckList()
	{
		// nothing here!
	}

	public String dump()
	{
		String typeTableList = "";
		String flag;

		//get each symboltable data in arraylist and print their dump.
		for (int counter = 0; counter < typeList.size(); counter++) 
		{
			flag = typeList.get(counter).Evaluate();
			if(new String("error").equals(flag))
			{
				typeTableList = typeTableList + typeList.get(counter).flag + "\n";
			}
		} 
		return typeTableList;
	}

	public void Combine(TypeCheckList t)
	{
		if (t != null)
			typeList.addAll(t.typeList);
	}

	public void Combine(TypeData t)
	{
		if (t != null)
			typeList.add(t);
	}
}