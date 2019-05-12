import java.io.*;

public class Main {

  public static void main(String [] args) throws Exception {
    Reader reader = null;
    
    if (args.length == 1) {
      File input = new File(args[0]);
      if (!input.canRead()) {
        System.out.println("Error: could not read ["+input+"]");
      }
      reader = new FileReader(input);
    }
    else {  
     reader = new InputStreamReader(System.in);
    }

    Yylex scanner = new Yylex(reader);   // create scanner ; lexical analysis

    parser parser = new parser(scanner); // create parser ; syntax analysis
    ProgramAST program = null;

    try { 
      program = (ProgramAST) parser.parse().value;  // parse program
    }    
    catch (Exception e) { 
      e.printStackTrace(); 
    }

    // Indent output of program
    String output = program.toString();
    PTFormatter ptformatter = new PTFormatter(output);
    //System.out.print(ptformatter);

    // semantic analysis process
    SymbolTable table = new SymbolTable();
    SemanticAnalyzer semanticAnalyzer = program.SemanticAnalysis(0, table); // passing in empty tablw



    //System.out.print("\r\nSYMBOL TABLE ...\r\n");
    //System.out.println(semanticAnalyzer.symbolTable.dump());
    System.out.print(semanticAnalyzer.symbolTable.getExceptions());
    //System.out.print("TYPE CHECKING ...\r\n");
    System.out.println(semanticAnalyzer.typeChecker.dump());


    //Interpreter will proceed if there are no errors in semantic analysis
    if(semanticAnalyzer.HasError())
    {
      System.out.println ("Resolve conflicts in program!\r\n");
    }
    else
    {
      if(semanticAnalyzer.symbolTable.HasMainFunc())
      {
        SymbolTable sT = new SymbolTable();
        program.ExecuteMethod(sT);
      }
      else
      {
        System.out.println("Main not found! Terminating the code...\r\n");
      }
    }
  }

  public static void error(String s) { 
    System.out.println(s); 
  }
}
