import Exceptions.NotInitilizedVariable;
import Exceptions.ParseException;
import Expression.ExprNode;
import Stack.PrintStack;
import Stack.VariableStack;
import Statement.*;
import java.util.ArrayList;


public class Body {

    public static void run(ArrayList<StatementNode> body) throws ParseException, NotInitilizedVariable {
        run(body, new VariableStack());
    }

    public static void run(ArrayList<StatementNode> body, VariableStack stack) throws ParseException,
            NotInitilizedVariable {

        for (StatementNode statement: body) {
            if (statement instanceof AssignStatement){
                AssignStatement assign = (AssignStatement)statement;

                double value = ExprNode.eval(assign.expression, stack);
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
                PrintStack.printStream.add("" + value);


            } else if (statement instanceof IfStatement){
                IfStatement ifStatement = (IfStatement)statement;
                boolean condition_result = ifStatement.condition.result(stack);

                if (condition_result == true){
                    Body.run(ifStatement.if_statements, new VariableStack(stack));
                } else {
                    Body.run(ifStatement.else_statements, new VariableStack(stack));
                }

            }  else if (statement instanceof SqrtStatement) {

                SqrtStatement sqrtStatement = (SqrtStatement) statement;
                double value = Math.sqrt(ExprNode.eval(sqrtStatement.expression, stack));
                String var_name = sqrtStatement.var_to_save.text;
                stack.var_values.put(var_name, value);

            }
        }
    }
}
