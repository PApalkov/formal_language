package LexerAnalysis;

/**
 * Тип лексемы
 */
public  enum TokenType {
    /**
     * Пробелы
     */
    SPACES,

    /**
     * Переменная
     */
    VAR,

    /**
     * Целое число
     */
    NUMBER,
    /**
     * Символ '+'
     */
    ADD,
    /**
     * Символ '-'
     */
    SUB,
    /**
     * Символ '*'
     */
    MUL,
    /**
     * Символ '/'
     */
    DIV,
    /**
     * Символ '('
     */
    LPAR,
    /**
     * Символ ')'
     */
    RPAR,

    /**
    * Символ '='
    */
    ASSIGN,

    /**
     * 'print'
     */
    PRINT,

    /**
     * Символ ';'
     * от англ. semicolon
     */
    SEM,

    /**
     * Символ '>'
     */
    MORE,

    /**
     * Символ '<'
     */
    LESS,

    /**
     * Символ '>='
     */
    MORE_EQUAL,

    /**
     * Символ '<='
     */
    LESS_EQUAL,

    /**
     * Символ '!='
     */
    NOT_EQUAL,

    /**
     * Символ '=='
     */
    EQUAL,

    /**
     * Символ 'true'
     */
    TRUE,

    /**
     * Символ 'false'
     */
    FALSE,

    /**
     * Символ 'if'
     */
    IF,

    /**
     * Символ 'while'
     */
    WHILE,

    /**
     * Символ '{'
     */
    LSCOBE,

    /**
     * Символ '}'
     */
    RSCOBE,




}
