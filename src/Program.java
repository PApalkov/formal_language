import Statement.StatementNode;

import java.util.ArrayList;



public class Program {
    final public ArrayList<StatementNode> statements = new ArrayList<>();

    public void add(StatementNode statement){
        statements.add(statement);
    }

}
