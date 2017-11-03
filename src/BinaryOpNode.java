public class BinaryOpNode extends ExprNode {

    /**
     * Левое поддерево (заполняется при isNumber = false)
     */
    public final ExprNode left;
    /**
     * Лексема для символа операции (заполняется при isNumber = false)
     */
    public final Token op;
    /**
     * Правое поддерево (заполняется при isNumber = false)
     */
    public final ExprNode right;

    /**
     * Конструктор для узла дерева с поддеревьями
     * @param left левое поддерево
     * @param op операция в узле
     * @param right правое поддерево
     */
    public BinaryOpNode(ExprNode left, Token op, ExprNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    /**
     * Конструктор для листа дерева (числа)
     */

    public ExprNode getLeft() {
        return left;
    }

    public Token getOp() {
        return op;
    }

    public ExprNode getRight() {
        return right;
    }

    @Override
    public String toString() {
        if (left == null) {
            return "(" + op.text + right.toString() + ")";
        }

        return "(" + left.toString() + op.text + right.toString() + ")";
    }
}
