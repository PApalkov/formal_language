import java.util.Map;

public abstract class ExprNode {

    public static double eval(ExprNode node, Map<String, Double> var_value) throws IllegalStateException {

        if (node == null) {
            return 0;
        }

        if (node instanceof NumberNode) {

            NumberNode tmp = (NumberNode) node;
            return Double.parseDouble(tmp.number.text);

        } else if (node instanceof BinaryOpNode) {

            BinaryOpNode tmp = (BinaryOpNode) node;

            TokenType op = tmp.op.type;
            double leftValue = eval(tmp.left, var_value);
            double rightValue = eval(tmp.right, var_value);

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

            VariableNode var = (VariableNode)node;
            String var_text = var.variable.text;

            if (var_value.containsKey(var_text)) {
                return var_value.get(var_text);
            } else {
                String message = var.variable.text + " is not inicialized";
                throw new IllegalStateException(message);
            }

        } else {
            throw new IllegalStateException();
        }

    }
}
