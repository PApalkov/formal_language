import Exceptions.ParseException;
import Expression.ExprNode;
import Statement.AssignStatement;
import Statement.PrintStatement;
import Statement.StatementNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Program {
    final public ArrayList<StatementNode> program = new ArrayList<>();

    public void add(StatementNode statement){
        program.add(statement);
    }

    //спросить как переделать поток вывода в jTextArea
    //public String run() throws Exceptions.ParseException {
    public void run() throws ParseException {

        Map<String, Double> var_value = new HashMap<>();

        for (StatementNode statement: program) {
            if (statement instanceof AssignStatement){
                AssignStatement assign = (AssignStatement)statement;

                double value = ExprNode.eval(assign.expression, var_value);
                var_value.put(assign.name.text, value);


            } else if (statement instanceof PrintStatement){
                PrintStatement print = (PrintStatement)statement;

                double value = ExprNode.eval(print.expression, var_value);


                System.out.println(value);
                //return "" + value;
            }
        }

        //return "";
    }
}
