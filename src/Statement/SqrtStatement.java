package Statement;

import Expression.ExprNode;
import LexerAnalysis.Token;

public class SqrtStatement extends StatementNode {
    public final ExprNode expression;
    public final Token var_to_save;

    public SqrtStatement(ExprNode expression, Token var_to_save) {
        this.expression = expression;
        this.var_to_save = var_to_save;
    }


}
