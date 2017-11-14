package Statement;

import Expression.LogicExpression;
import java.util.ArrayList;

public class IfStatement extends StatementNode{

    public final LogicExpression condition;
    public final ArrayList<StatementNode> statements = new ArrayList<>();


    public IfStatement(LogicExpression condition) {
        this.condition = condition;
    }

    public void add(StatementNode statement){
        statements.add(statement);
    }
}
