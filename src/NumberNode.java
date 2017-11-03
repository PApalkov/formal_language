public class NumberNode extends ExprNode {

    /**
     * Лексема для числа
     */
    public final Token number;


    /**
     * Конструктор для листа дерева (числа)
     */
    public NumberNode(Token number) {
        this.number = number;
    }


    public Token getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number.text;
    }
}
