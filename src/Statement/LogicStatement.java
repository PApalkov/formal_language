package Statement;

import Expression.ExprNode;
import LexerAnalysis.Token;
import com.sun.javafx.tools.packager.PackagerException;

import java.util.Map;

public class LogicStatement extends StatementNode {
    public final ExprNode left;
    public final ExprNode right;
    public final Token operation;

    public LogicStatement(ExprNode left, ExprNode right, Token operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public boolean result(Map<String, Double> var_value) throws PackagerException{

        double left_result = ExprNode.eval(left, var_value);
        double right_result = ExprNode.eval(right, var_value);

        switch (operation.type){
            case MORE: return (left_result > right_result);
            case LESS: return (left_result < right_result);
            case MORE_EQUAL: return (left_result >= right_result);
            case LESS_EQUAL: return (left_result <= right_result);
            case NOT_EQUAL: return (left_result != right_result);
            case EQUAL: return (left_result == right_result);
            default: throw new PackagerException("wrong logic operation type");
        }
    }
}
