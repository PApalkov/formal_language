import Exceptions.ParseException;
import Expression.ExprNode;
import Stack.VariableStack;
import Statement.AssignStatement;
import Statement.IfStatement;
import Statement.PrintStatement;
import Statement.StatementNode;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Body {

    public static void run(ArrayList<StatementNode> body) throws ParseException {
        run(body, new VariableStack());
    }

    public static void run(ArrayList<StatementNode> body, VariableStack stack) throws ParseException {


        for (StatementNode statement: body) {
            if (statement instanceof AssignStatement){
                AssignStatement assign = (AssignStatement)statement;

                double value = ExprNode.eval(assign.expression, stack);

                //todo описать
                String var = assign.name.text;
                VariableStack stack_of_var = VariableStack.findVariableTable(var, stack);

                if (stack_of_var == null)
                    stack.var_values.put(var, value);
                else
                    stack_of_var.var_values.put(var, value);



            } else if (statement instanceof PrintStatement){
                PrintStatement print = (PrintStatement)statement;

                double value = ExprNode.eval(print.expression, stack);


                System.out.println(value);
                //return "" + value;


            } else if (statement instanceof IfStatement){
                IfStatement ifStatement = (IfStatement)statement;
                boolean condition_result = ifStatement.condition.result(stack);

                if (condition_result == true){
                    Body.run(ifStatement.statements, new VariableStack(stack));
                }
            }


        }

        //return "";
    }
}
