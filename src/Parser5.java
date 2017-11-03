import java.util.List;

/**
 * Грамматический разбор грамматики
 * выражение ::= слагаемое (('+'|'-') слагаемое)*
 * слагаемое ::= множитель (('*'|'\') множитель)
 * множитель ::= (-)?ЧИСЛО | '(' выражение ')'
 * с построением дерева разбора.
 */
public class Parser5 {

    /**
     * Список лексем
     */
    private final List<Token> tokens;
    /**
     * Индекс текущей лексемы
     */
    private int index = 0;

    public Parser5(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Проверка типа текущей лексемы.
     *
     * @param type предполагаемый тип лексемы
     * @return не null, если текущая лексема предполагаемого типа (при этом текущи индекс сдвигается на 1);
     * null, если текущая лексема другого типа
     */
    private Token match(TokenType type) {
        if (index >= tokens.size())
            return null;
        Token token = tokens.get(index);
        if (token.type != type)
            return null;
        index++;
        return token;
    }

    /**
     * Сообщение об ошибке с указанием текущей позиции в тексте.
     *
     * @param message текст сообщения
     */
    private void error(String message) throws ParseException {
        // Позиция ошибки в тексте
        int errorPosition;
        if (index >= tokens.size()) {
            // Мы стоим в конце текста
            if (tokens.isEmpty()) {
                // Лексем не было вообще - текст пустой; указываем на начало текста
                errorPosition = 0;
            } else {
                // Берем координату после последней лексемы
                errorPosition = tokens.get(tokens.size() - 1).to;
            }
        } else {
            // Берем координату текущей лексемы
            Token token = tokens.get(index);
            errorPosition = token.from;
        }
        throw new ParseException(message, errorPosition);
    }

    /**
     * Метод для нетерминального символа 'слагаемое'.
     *
     * @return узел дерева, соответствующий слагаемому
     */
    private ExprNode matchSlagaemoe() throws ParseException {

        ExprNode leftNode = matchMult();
        while (true) {
            // Пока есть символ '*' или '/'...
            Token op = matchAny(TokenType.MUL, TokenType.DIV);
            if (op != null) {
                // Требуем после умножения/деления снова множитель:
                ExprNode rightNode = matchMult();
                // Из двух множителей формируем дерево с двумя поддеревьями:

                leftNode = new BinaryOpNode(leftNode, op, rightNode);
            } else {
                break;
            }
        }
        return leftNode;
    }

    /**
     * Метод для нетерминального символа 'множитель'.
     *
     * @return узел дерева, соответствующий множителю
     */
    private ExprNode matchMult() throws ParseException {

        Token minus = match(TokenType.SUB);


        Token number = match(TokenType.NUMBER);

        if (number != null) {
            //учитываем минус
            if (minus != null) {
                ExprNode rightNode = new NumberNode(number);
                return  new  BinaryOpNode(null, minus, rightNode);
            }

            // Если это просто ЧИСЛО, то возвращаем узел для числа:
            return new NumberNode(number);
        }
        if (match(TokenType.LPAR) != null) {
            // Если это открывающая скобка, то вызываем разбор выражения в скобках:
            ExprNode nested = matchExpression();
            // После него обязательно должна быть закрывающая скобка:
            if (match(TokenType.RPAR) == null) {
                error("Missing ')'");
            }

            //смтотрим, стоит ли знак минус
            if (minus != null) {
                return new  BinaryOpNode(null, minus, nested);
            }

            return nested;
        } else {
            // Иначе ошибка - других вариантов кроме числа и скобки быть не может:
            error("Number or '(' expected");
            return null;
        }
    }

    /**
     * Проверка типа текущей лексемы.
     *
     * @param types возможные типы лексем
     * @return не null, если текущая лексема одного из предполагаемых типов (при этом текущи индекс сдвигается на 1);
     * null, если текущая лексема другого типа
     */
    private Token matchAny(TokenType... types) {
        for (TokenType type : types) {
            Token matched = match(type);
            if (matched != null)
                return matched;
        }
        return null;
    }

    /**
     * Грамматический разбор выражения по грамматике
     * выражение ::= слагаемое (('+'|'-') слагаемое)*
     * слагаемое ::= ЧИСЛО | '(' выражение ')'
     *
     * @return дерево разбора выражения
     */
    public ExprNode matchExpression() throws ParseException {
        // В начале должно быть слагаемое:

        ExprNode leftNode = matchSlagaemoe();
        while (true) {
            // Пока есть символ '+' или '-'...
            Token op = matchAny(TokenType.ADD, TokenType.SUB);
            if (op != null) {
                // Требуем после плюса/минуса снова слагаемое:
                ExprNode rightNode = matchSlagaemoe();
                // Из двух слагаемых формируем дерево с двумя поддеревьями:

                leftNode = new BinaryOpNode(leftNode, op, rightNode);
            } else {
                break;
            }
        }
        return leftNode;
    }

    /**
     * Проверка грамматического разбора выражения
     */

    public static void main(String[] args) throws ParseException {
        String expression = "- 8 + 1 * 3 + -3 / 3";
        Lexer lexer = new Lexer(expression);
        List<Token> allTokens = lexer.getAllTokens();
        Parser5 parser = new Parser5(allTokens);
        ExprNode exprTreeRoot = parser.matchExpression();
        System.out.println(exprTreeRoot.toString());
        System.out.println(ExprNode.eval(exprTreeRoot));
    }
}
