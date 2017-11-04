public class AssignStatement extends StatementNode {
    Token name;
    ExprNode expression;

    public AssignStatement(Token name, ExprNode expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return name.text + '=' + expression.toString();
    }
}
