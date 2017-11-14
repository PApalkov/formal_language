package Expression;


import Exceptions.ParseException;
import LexerAnalysis.Token;
import Stack.VariableStack;
import Statement.StatementNode;


public class LogicExpression extends StatementNode {
    public final ExprNode left;
    public final Token operation;
    public final ExprNode right;

    public LogicExpression(ExprNode left, Token operation, ExprNode right) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public boolean result(VariableStack stack) throws ParseException{

        double left_result = ExprNode.eval(left, stack);
        double right_result = ExprNode.eval(right, stack);

        switch (operation.type){
            case MORE: return (left_result > right_result);
            case LESS: return (left_result < right_result);
            case NOT_EQUAL: return (left_result != right_result);
            case EQUAL: return (left_result == right_result);
            default: throw new ParseException("Wrong logic operation type", 0);
        }
    }
}
