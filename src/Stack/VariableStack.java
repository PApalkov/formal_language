package Stack;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.HashMap;
import java.util.Map;

/**Класс для хранения значений
 * переменных на каждом уровне.
 */

public class VariableStack {
    public final Map<String, Double> var_values = new HashMap<>();
    public  VariableStack parent;

    public VariableStack(VariableStack parent) {
        this.parent = parent;
    }

    public VariableStack() {
        this.parent = null;
    }

    /**
     * @return VariableStack, в котором нахрдится искомая переменная
     * если переменная еще не использовалась раннее, @return null
     */
    public static VariableStack findVariableTable(String key, VariableStack stack){

        VariableStack tmp = stack;

        while ((tmp != null) && !(tmp.var_values.containsKey(key))) {
            tmp = tmp.parent;
        }

        if (tmp == null) return null;
        if (tmp.var_values.containsKey(key)) return tmp;

        return null;

    }
}
