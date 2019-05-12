
/**
 * This program takes the output of the parser and indents it
 */ 
import java.io.*;

class PTFormatter{
	String codeblock;


	public PTFormatter(String c)
	{
		codeblock = c;
	}

	public String toString()
	{
		String s = "";
		String indent = "";
		Integer depth = 0;
		
		try {
			// Write program to temporary file
		FileWriter fw=new FileWriter("temp.txt");
		fw.write(codeblock);
		fw.close();
		// Read in program from temporary file line by line
		// and indent appropriately!
		
		FileReader fileReader = new FileReader("temp.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		String trailingspace;
		while ((line = bufferedReader.readLine()) != null) {
			trailingspace = stringForIndent(depth)+"^t";
			if(new String("{").equals(line))
			{
				depth = depth+1;
			}
			if(new String("}").equals(line) || new String("};").equals(line) )
			{
				depth = depth-1;
				s = s+"^t";
			}
			s = s + line + "\n" + stringForIndent(depth);
			s = s.replace(trailingspace, stringForIndent(depth));
		}
		fileReader.close();


		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return s;
	}

	int INDENTMULTIPLIER = 2;
	String stringForIndent(int indent) {
		String s = "";
		for (int i = 0; i < (INDENTMULTIPLIER * indent); i++)
			s = s + "  ";
		return s;
	}
}