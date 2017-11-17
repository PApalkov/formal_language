package Expression;

import Exceptions.NotInitilizedVariable;
import LexerAnalysis.Token;
import LexerAnalysis.TokenType;
import Stack.VariableStack;

public abstract class ExprNode {

    public static double eval(ExprNode node, VariableStack stack) throws NotInitilizedVariable {

        if (node == null) {
            return 0;
        }

        if (node instanceof NumberNode) {

            NumberNode tmp = (NumberNode) node;
            return Double.parseDouble(tmp.number.text);

        } else if (node instanceof BinaryOpNode) {

            BinaryOpNode tmp = (BinaryOpNode) node;

            TokenType op = tmp.op.type;
            double leftValue = eval(tmp.left, stack);
            double rightValue = eval(tmp.right, stack);

            switch (op) {
                case ADD:
                    return (leftValue + rightValue);
                case SUB:
                    return (leftValue - rightValue);
                case DIV:
                    return (leftValue / rightValue);
                case MUL:
                    return (leftValue * rightValue);
                default:
                    throw new IllegalStateException();
            }

        } else if (node instanceof VariableNode) {

            VariableNode var = (VariableNode) node;
            String var_text = var.variable.text;

            VariableStack stack_of_var = VariableStack.findVariableTable(var_text, stack);

            if (stack_of_var == null) {
                String message = "Variable " + var_text + " is not initilized";
                throw new NotInitilizedVariable(message);
            } else {
                return stack_of_var.var_values.get(var_text);
            }

        } else {
            throw new NotInitilizedVariable("Error");
        }
    }
}
