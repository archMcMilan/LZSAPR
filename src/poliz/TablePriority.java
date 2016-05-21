package poliz;

import la.Lexema;

import java.util.ArrayList;

/**
 * Created by Aртем on 17.04.2016.
 */
public class TablePriority {
    ArrayList<Operation> priority = new ArrayList<>();

    public TablePriority() {
        generateTable();
    }

    /**
     * создаем таблицу приоритетов
     */
    private void generateTable() {
        ArrayList<String> temp = new ArrayList<>();

        temp.add("(");
        temp.add("[");

        temp.add("if");

        temp.add("for");
        temp.add("{");

        temp.add("printf");
        temp.add("scanf");
        priority.add(new Operation(temp));
        temp.clear();

        temp.add(")");
        temp.add("]");

        temp.add("then");

        temp.add("to");
        temp.add("by");
        temp.add("while");
        temp.add("}");
        temp.add("end");

        temp.add("%i");
        temp.add("%b");
        temp.add("%c");
        priority.add(new Operation(temp));
        temp.clear();

        temp.add("=");
        temp.add(".");
        priority.add(new Operation(temp));
        temp.clear();

        priority.add(new Operation("||"));

        priority.add(new Operation("&&"));
        priority.add(new Operation("!!"));

        temp.add("<");
        temp.add(">");
        temp.add("<=");
        temp.add(">=");
        temp.add("==");
        temp.add("!=");
        priority.add(new Operation(temp));
        temp.clear();

        temp.add("+");
        temp.add("-");
        priority.add(new Operation(temp));
        temp.clear();

        temp.add("*");
        temp.add("$");
        temp.add("@");
        priority.add(new Operation(temp));
        temp.clear();

        priority.add(new Operation("^"));
        priority.add(new Operation(";"));
    }

    /**
     * находим приоритет операции
     *
     * @param lex элемент класса Lexema, в котором содержаться код лексемы, ее название
     * @return int приоритет
     */
    public int getPriority(Lexema lex) {
        int iter=0;
        for(Operation el:priority){
            if(el.contains(lex.getLexem())){
                return iter;
            }
            iter++;
        }
        return -1;
    }

}

class Operation {
    ArrayList<String> operators;
    int priority;

    public Operation(String operator) {
        operators = new ArrayList<>();
        operators.add(operator);
        this.priority++;
    }

    public Operation(ArrayList<String> operators) {
        this.operators = new ArrayList<>();
        for (String temp:operators){
            this.operators.add(temp);
        }

        this.priority++;
    }

    /**
     * проверяем, есть ли такой оператор с данным приоритетом
     * @param lex искомая лексема
     * @return boolean, в случае, если содержит, в противном случае false
     */
    public boolean contains(String lex){
        if(operators.contains(lex)){
            return true;
        }
        return false;
    }


}