public class AssignStatement extends StatementNode {
    public final Token name;
    public final ExprNode expression;

    public AssignStatement(Token name, ExprNode expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return name.text + '=' + expression.toString();
    }
}
