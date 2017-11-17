package Statement;

import Expression.LogicExpression;
import java.util.ArrayList;

public class IfStatement extends StatementNode{

    public final LogicExpression condition;
    public final ArrayList<StatementNode> if_statements = new ArrayList<>();
    public final ArrayList<StatementNode> else_statements = new ArrayList<>();


    public IfStatement(LogicExpression condition) {
        this.condition = condition;
    }

    public void add_if(StatementNode statement){
        if_statements.add(statement);
    }

    public void add_else(StatementNode statement){
        else_statements.add(statement);
    }
}
