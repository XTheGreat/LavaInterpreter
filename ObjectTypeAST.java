/**
 * AST node for a number
 */ 
class ObjectTypeAST extends TypeAST implements AST {
  String type;

  public ObjectTypeAST(String t) {
    type = t;
  }

  public String toString() {
    return(type); 
  }

}

