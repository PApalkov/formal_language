package Statement;

import Expression.ExprNode;

public class PrintStatement extends StatementNode {
    public final ExprNode expression;

    public PrintStatement(ExprNode expression) {
        this.expression = expression;
    }
}
