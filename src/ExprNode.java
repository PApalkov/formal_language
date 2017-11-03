public abstract class ExprNode {

    public static double eval(ExprNode node) throws IllegalStateException{

        if (node == null){
            return 0;
        }

        if (node instanceof NumberNode){

            NumberNode tmp = (NumberNode)node;
            return Double.parseDouble(tmp.number.text);

        } else if (node instanceof BinaryOpNode) {

            BinaryOpNode tmp = (BinaryOpNode)node;

            TokenType op = tmp.op.type;
            double leftValue = eval(tmp.left);
            double rightValue = eval(tmp.right);

            switch (op){
                case ADD: return (leftValue + rightValue);
                case SUB: return (leftValue - rightValue);
                case DIV: return (leftValue / rightValue);
                case MUL: return (leftValue * rightValue);
                default: throw new IllegalStateException();
            }

        } else {
            throw new IllegalStateException();
        }
    }
}
