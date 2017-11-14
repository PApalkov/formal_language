import Exceptions.ParseException;
import Expression.*;
import LexerAnalysis.Lexer;
import LexerAnalysis.Token;
import LexerAnalysis.TokenType;
import Statement.AssignStatement;
import Statement.IfStatement;
import Statement.PrintStatement;
import Statement.StatementNode;

import java.util.List;

/**
 * Грамматический разбор грамматики
 * программа ::= ( оператор ";" ) +
 * оператор ::= переменная '=' выражение
 * выражение ::= слагаемое (('+'|'-') слагаемое)*
 * слагаемое ::= множитель (('*'|'\') множитель)
 * множитель ::= (-)?ЧИСЛО |ПЕРЕМЕННАЯ| '(' выражение ')'
 *
 * с построением дерева разбора.
 */
public class Parser {

    /**
     * Список лексем
     */
    private final List<Token> tokens;
    /**
     * Индекс текущей лексемы
     */
    private int index = 0;

    public Parser(List<Token> tokens) {
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
        Token variable = match(TokenType.VAR);

        if (variable != null){

            //учитываем минус
            if (minus != null) {
                ExprNode rightNode = new VariableNode(variable);
                return  new  BinaryOpNode(null, minus, rightNode);
            }

            // Если это просто ЧИСЛО, то возвращаем узел для числа:
            return new VariableNode(variable);
        }
        if (number != null) {

            //учитываем минус
            if (minus != null) {
                ExprNode rightNode = new NumberNode(number);
                return  new  BinaryOpNode(null, minus, rightNode);
            }

            // Если это просто ЧИСЛО, то возвращаем узел для числа:
            return new NumberNode(number);

        } else if (match(TokenType.LPAR) != null) {
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


    public StatementNode matchAssignStatement() throws ParseException {
        Token var = match(TokenType.VAR);
        Token equal = match(TokenType.ASSIGN);

        if (var == null) {
            return null;
        }

        if (equal == null) {
            return null;
        }

        ExprNode expression = matchExpression();

        if (expression == null) {
            error("Expression missed");
            return null;
        }

        return new AssignStatement(var, expression);
    }


    public StatementNode matchPrintStatement() throws ParseException {
        Token print = match(TokenType.PRINT);

        if (print == null)
            return null;


        if (match(TokenType.LPAR) == null) error("( missing");

        ExprNode expression = matchExpression();

        if (match(TokenType.RPAR) == null) error(") missing");

        return new PrintStatement(expression);
    }

    public LogicExpression matchLogicExpression() throws ParseException{

        ExprNode left_expression = matchExpression();
        Token op = matchAny(TokenType.MORE, TokenType.LESS,
                            TokenType.NOT_EQUAL, TokenType.EQUAL);

        ExprNode right_expression = matchExpression();

        return new LogicExpression(left_expression, op, right_expression);
    }

    public StatementNode matchIfStatement() throws ParseException{
        Token if_token = match(TokenType.IF);

        if (if_token == null){
            return null;
        }

        if (match(TokenType.LPAR) == null) error("( missing");


        LogicExpression expression = matchLogicExpression();

        if (match(TokenType.RPAR) == null) error(") missing");

        IfStatement ifStatement = new IfStatement(expression);

        //начинается тело оператора
        if (match(TokenType.LSCOBE) == null) error("{ missing");

        while (match(TokenType.RSCOBE) == null) {
            StatementNode statement = matchStatement();

            if (statement == null) error("} missing");

            ifStatement.add(statement);
        }

        return ifStatement;
    }

    /**
     * тут будет происходить посик любых операторов
     * при добавлении новых операторов, будет обновляться эта часть
     */
    public StatementNode matchStatement() throws ParseException {


        StatementNode assigmentState = matchAssignStatement();
        if (assigmentState != null) {

            if (matchAny(TokenType.SEM) == null) error("; missed");
            return assigmentState;
        }

        StatementNode ifStatement = matchIfStatement();
        if (ifStatement != null)
            return ifStatement;

        StatementNode printState = matchPrintStatement();
        if (printState != null) {

            if (matchAny(TokenType.SEM) == null) error("; missed");
            return printState;
        }


        return null;
    }



    public Program matchProgram() throws ParseException {
        Program program = new Program();

        StatementNode statement = matchStatement();

        while (statement != null) {
            program.add(statement);
            statement = matchStatement();
        }

        return program;
    }


    /**
     * Проверка грамматического разбора выражения
     */

    public static void main(String[] args) throws ParseException {

        String expression = "x = 5; " +
                "y = x+1; " +
                "print(x); " +
                "print(y); " +
                "if (x != y) { " +
                    "print(y); " +
                    "z = 8; " +
                    "print(z); " +
                    "if (z > x) { " +
                        "t = 46; " +
                        "print(t); print(x); print(z);" +
                    "} " +
                "} " +
                "print(x);" +
                "print(y);";

        Lexer lexer = new Lexer(expression);
        List<Token> allTokens = lexer.getAllTokens();
        System.out.println(allTokens);
        Parser parser = new Parser(allTokens);
        Program program = parser.matchProgram();
        Body.run(program.statements);
    }

    /*
    public static String run(String expression) throws Exceptions.ParseException{
        LexerAnalysis.Lexer lexer = new LexerAnalysis.Lexer(expression);
        List<LexerAnalysis.Token> allTokens = lexer.getAllTokens();
        Parser parser = new Parser(allTokens);
        Program program = parser.matchProgram();
        return program.run();
    }
    */

}
