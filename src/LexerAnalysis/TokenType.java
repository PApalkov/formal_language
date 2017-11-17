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
     * Символ ','
     */
    COMMA,

    /**
     * Ключевое слово 'print'
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
     * Символ '!='
     */
    NOT_EQUAL,

    /**
     * Символ '=='
     */
    EQUAL,

    /**
     * Ключевое слово 'if'
     */
    IF,

    /**
     * Ключевое слово 'else'
     */
    ELSE,

    /**
     * Ключевое слово 'while'
     */
    WHILE,

    /**
     * Ключевое слово 'sqrt'
     */
    SQRT,

    /**
     * Символ '{'
     */
    LSCOBE,

    /**
     * Символ '}'
     */
    RSCOBE,




}
