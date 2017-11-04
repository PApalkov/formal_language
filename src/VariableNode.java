public class VariableNode extends ExprNode {


    /**
     * Лексема для переменной
     */
    public final Token variable;


    /**
     * Конструктор для листа дерева (числа)
     */
    public VariableNode(Token variable) {
        this.variable = variable;
    }


    public Token getVar() {
        return variable;
    }

    @Override
    public String toString() {
        return variable.text;
    }


}
