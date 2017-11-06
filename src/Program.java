import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Program {
    final public ArrayList<StatementNode> program = new ArrayList<>();

    public void add(StatementNode statement){
        program.add(statement);
    }

    public void run(){
        Map<String, Double> var_value = new HashMap<>();

        for (StatementNode statement: program) {
            if (statement instanceof AssignStatement){
                AssignStatement assign = (AssignStatement)statement;

                double value = ExprNode.eval(assign.expression, var_value);
                var_value.put(assign.name.text, value);

            } else if (statement instanceof PrintStatement){
                PrintStatement print = (PrintStatement)statement;

                double value = ExprNode.eval(print.expression, var_value);

                //todo доделать поток вывода
                System.out.println(value);
            }
        }

    }
}
