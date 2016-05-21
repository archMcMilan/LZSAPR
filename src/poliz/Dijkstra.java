package poliz;

import la.Lexema;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Aртем on 17.04.2016.
 */
public class Dijkstra {
    private ArrayList<Lexema> inputLex = new ArrayList<>();
    private ArrayList<LinkedList<Lexema>> stack = new ArrayList<>();
    private ArrayList<LinkedList<Object>> poliz = new ArrayList<>();
    private TablePriority table;
    private int counterLabel = 1;//счетчик меток
    private int maxCounterLabel=1;
    private int workCell = 1;//счетчик рабочих ячеек
    private ArrayList<Integer> cycleSign = new ArrayList<>();//Ознака цикла
    private LinkedList<Lexema> variableCycleParam = new LinkedList<>();//змінна параметра циклу


    public Dijkstra(Lexema[] inputLex) {
        boolean flag=false;
        label:
        for(int i=0;i<inputLex.length-1;i++){
            if(flag==false){
                if(inputLex[i].getCode()==1){
                    flag=true;
                }
                continue label;
            }else {
                this.inputLex.add(inputLex[i]);
            }
        }
        table = new TablePriority();

    }




    public void algorithm() {
        int index = 0;
        while (index < inputLex.size()) {
            stackTwice(index);
            firstRule(index);
            //System.out.println("cLabel "+inputLex.get(index).getLexem()+" "+counterLabel+" "+maxCounterLabel);
            index++;
           

        }
    }

    /**
     * Первое правило
     *
     * @param index индекс элемента входной строки
     */
    public void firstRule(int index) {
        if (inputLex.get(index).getCode() == 43 || inputLex.get(index).getCode() == 44) {//id, con
            poliz.get(poliz.size() - 1).add(inputLex.get(index));
        } else {
            if (inputLex.get(index).getCode() == 14 && (inputLex.get(index + 1).getCode() == 43 ||
                    inputLex.get(index + 1).getCode() == 44)) {//- перед id или con
                if (index - 1 > -1) {
                    if (inputLex.get(index - 1).getCode() != 43 && inputLex.get(index - 1).getCode() != 44
                            && inputLex.get(index - 1).getCode() != 30) {//если перед - был не id и не con
                        stack.get(stack.size() - 1).add(new Lexema(1, "@", 45, -1));
                    } else {
                        secondRule(index);
                    }
                } else {
                    stack.get(stack.size() - 1).add(new Lexema(1, "@", 45, -1));
                }
            } else {
                secondRule(index);
            }

        }
    }

    /**
     * Второе правило
     *
     * @param index индекс элемента входной строки
     */
    public void secondRule(int index) {
        if (stack.get(stack.size() - 1).size() != 0) {//проверка что бы стек не был пуст
            if (inputLex.get(index).getCode() != 29 && inputLex.get(index).getCode() != 30 &&// '(' и ')'
                    inputLex.get(index).getCode() != 41 && inputLex.get(index).getCode() != 42 &&// '[' и ']'
                    inputLex.get(index).getCode() != 31 && inputLex.get(index).getCode() != 32 &&    // if и then
                    (!(inputLex.get(index).getCode() >= 18 && inputLex.get(index).getCode() <= 24)) &&//printf & scanf, for,to,by,while,end
                    inputLex.get(index).getCode() != 1 && inputLex.get(index).getCode() != 2) {// { & }

                if (inputLex.get(index).getCode() == 6 && inputLex.get(index-2).getCode()==20) {
                    //для случая цикла: если встретилось присвоение и ОЦ=1, то последнюю запись из полиза записываем вЗПЦ и выставляем ОЦ=0;
                	if (cycleSign.get(cycleSign.size() - 1) == 1) {
                        variableCycleParam.set(variableCycleParam.size() - 1, (Lexema) poliz.get(poliz.size() - 2).getLast());
                        cycleSign.set(cycleSign.size() - 1, 0);

                    }
                }
                if (table.getPriority(stack.get(stack.size() - 1).getLast()) >= table.getPriority(inputLex.get(index))) {//если приоритет больше
                    poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());

                    secondRule(index);
                } else {
                    if(inputLex.get(index).getCode()==26){
                        fourthRule(index);
                    }else {
                        stack.get(stack.size() - 1).add(inputLex.get(index));
                    }
                }
            } else {
                if (inputLex.get(index).getCode() == 29 || inputLex.get(index).getCode() == 41 ||//если открывающаяся скобка '('или '['
                        inputLex.get(index).getCode() == 31) {// или 'if'
                    stack.get(stack.size() - 1).add(inputLex.get(index));
                    
                }
                if (inputLex.get(index).getCode() == 30) {//если закрывающаяся скобка ')'
                    while (stack.get(stack.size() - 1).getLast().getCode() != 29) {//выталкиваем из стека в полиз все до (
                        poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());

                    }
                    stack.get(stack.size() - 1).pollLast();//удаляем ( из стека
                }
                if (inputLex.get(index).getCode() == 42) {//если  ']'
                    while (stack.get(stack.size() - 1).getLast().getCode() != 41) {//выталкиваем из стека в полиз все до [
                        poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());

                    }
                    stack.get(stack.size() - 1).pollLast();//удаляем [ из стека
                }
                if (inputLex.get(index).getCode() == 32) {//если then
                    while (stack.get(stack.size() - 1).getLast().getCode() != 31) {//выталкиваем из стека в полиз все до if
                        poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());
                    }
                    stack.get(stack.size() - 1).getLast().addLabel(counterLabel);//добавляем метку на на if
                    stack.get(stack.size() - 1).getLast().addPos(index);
                    poliz.get(poliz.size() - 1).add("m[" + counterLabel + "] УПЛ");//добавляем запись в полиз m*номер метки* УПЛ                 
                    counterLabel++;
                    if(maxCounterLabel<counterLabel){
                    	maxCounterLabel=counterLabel;
                    }
                   
                }
                if (inputLex.get(index).getCode() == 18 || inputLex.get(index).getCode() == 19) {//если prinf или scanf
                    stack.get(stack.size() - 1).add(inputLex.get(index));
                }

                if (inputLex.get(index).getCode() == 20) {//если for
                    initFor(index);
                }
                if (inputLex.get(index).getCode() == 21) {//если to
                    while (stack.get(stack.size() - 1).getLast().getCode() != 20) {//выталкиваем из стека в полиз все до for
                        poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());
                    }
                    poliz.get(poliz.size() - 1).add("r[" + workCell + "] "+1 +" "+ ((new Lexema(1, "=", 6, -1)).getLexem()) + " m[" + (counterLabel-3) + "]" +
                            ": r[" + (workCell + 2) + "]");//добавляем запись в полиз r*номер раб ячейки*= m*номер метки*: r*номер раб ячейки+2*
                }
                if (inputLex.get(index).getCode() == 22) {//если by
                    while (stack.get(stack.size() - 1).getLast().getCode() != 20) {//выталкиваем из стека в полиз все до for
                        poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());
                    }
                    poliz.get(poliz.size() - 1).add((new Lexema(1, "=", 6, -1)).getLexem() + " r[" + (workCell + 1) + "]");
                    //добавляем запись в полиз = r*номер раб ячейки+1*
                }
                if (inputLex.get(index).getCode() == 23) {//если while
                    while (stack.get(stack.size() - 1).getLast().getCode() != 20) {//выталкиваем из стека в полиз все до for
                        poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());
                    }
                    poliz.get(poliz.size() - 1).add(" = r[" + workCell + "] " + 0 + " "+((new Lexema(1, "==", 12, -1)).getLexem()) + " m[" + (counterLabel - 1) + "] УПЛ " +
                            variableCycleParam.getLast().getLexem() + " " + variableCycleParam.getLast().getLexem() + " r[" + (workCell + 1) + "] " +
                            ((new Lexema(1, "+", 13, -1)).getLexem()) + " "+ ((new Lexema(1, "=", 6, -1)).getLexem()) + " m[" +
                            (counterLabel - 1) + "]: r[" + (workCell) + "]" +" "+ 0 +" "+ ((new Lexema(1, "=", 6, -1)).getLexem()) +" "+
                            variableCycleParam.getLast().getLexem() + " r[" + (workCell + 2) + "]" +" "+ ((new Lexema(1, "-", 14, -1)).getLexem()) + " r[" + (workCell + 1) + "] " +
                            ((new Lexema(1, "*", 15, -1).getLexem()) +  " "+0 +  " "+((new Lexema(1, "<=", 10, -1)).getLexem()) + " "+ " m[" + (counterLabel - 2) + "] УПЛ"));
                    //=r[j]0==m[i+2] УПЛ ЗПЦ ЗПЦ r[j+1] += m[i+2]: rj0= ЗПЦ r[j+2]-r[j+1]*0<=m[i+3] УПЛ

                }
                if (inputLex.get(index).getCode() == 1) {//если {
                    while (stack.get(stack.size() - 1).getLast().getCode() != 20) {//выталкиваем из стека в полиз все до for
                        poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());
                    }
                    stack.get(stack.size() - 1).add(inputLex.get(index));
                    poliz.get(poliz.size() - 1).add("m[" + (counterLabel - 2) + "] УПЛ");//m[i+1] УПЛ
                }
                if (inputLex.get(index).getCode() == 2) {//если }
                    while (stack.get(stack.size() - 1).getLast().getCode() != 1) {//выталкиваем из стека в полиз все до {
                        poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());
                    }
                    stack.get(stack.size() - 1).pollLast();//удаляем { из стека
                }
                if (inputLex.get(index).getCode() == 24) {//если end
                    while (stack.get(stack.size() - 1).getLast().getCode() != 20) {//выталкиваем из стека в полиз все до for
                        poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());
                    }
                    workCell+=3;
                    stack.get(stack.size() - 1).pollLast();//удаляем for из стека
                    //System.out.println(counterLabel);
                    poliz.get(poliz.size() - 1).add("m[" + (counterLabel-3) + "] БП m[" + (counterLabel -2) + "]: ");//m[i+1] УПЛ
                    //System.out.println("label="+counterLabel+" "+maxCounterLabel);
                    if(maxCounterLabel<counterLabel){
                    	maxCounterLabel=counterLabel;
                    }
                    counterLabel-=3;
                }
            }
        } else {
            thirdRule(index);
        }

    }


    /**
     * Третье правило
     *
     * @param index индекс элемента входной строки
     */
    public void thirdRule(int index) {
        if (stack.get(stack.size() - 1).size() == 0) {
            if (inputLex.get(index).getCode() == 20) {//если for
                initFor(index);
            }else if(inputLex.get(index).getCode() == 26){
            	fourthRule(index);
            }else{
            	stack.get(stack.size() - 1).add(inputLex.get(index));
            }
            
            
        } else {
            fourthRule(index);
        }
    }

    /**
     * Четвертое правило
     *
     * @param index индекс элемента входной строки
     */
    public void fourthRule(int index) {
        if (inputLex.get(index).getCode() == 26) {//;
        	//System.out.println("counterLabel "+counterLabel+" "+maxCounterLabel);
        	if(inputLex.get(index-1).getCode() == 24 && inputLex.get(index-1)!=null){//end
        		//System.out.println("in "+counterLabel+" next"+inputLex.get(index+1).getCode());
        		if(index+1<inputLex.size() && inputLex.get(index+1).getCode() != 2){
        			//System.out.println("in2 "+counterLabel);
            		counterLabel=maxCounterLabel;
        		}
        		
        	}
            while (stack.get(stack.size() - 1).size() > 0) {//выталкиваем все из стека в полиз
                if(stack.get(stack.size() - 1).getLast().getCode()==26 || stack.get(stack.size() - 1).getLast().getCode()==1){//если дошли до ; или {
                    return;
                }
                poliz.get(poliz.size() - 1).add(stack.get(stack.size() - 1).pollLast());
            }
        }
    }

    /**
     * Заносим стек в следующий стек
     *
     * @param index
     */
    public void stackTwice(int index) {
        //выделяем память под коллекции
        stack.add(new LinkedList<>());
        poliz.add(new LinkedList<>());
        if (index != 0) {
            for (int i = 0; i < stack.get(index - 1).size(); i++) {
                stack.get(index).add(stack.get(index - 1).get(i));
            }
        }
        if(index!=0){
            variableCycleParam.add(variableCycleParam.getLast());
            cycleSign.add(cycleSign.get(cycleSign.size()-1));
        }else{
            variableCycleParam.add(new Lexema());
            cycleSign.add(1);
        }
    }

    /**
     * Длинна входной строки
     *
     * @return inputLex.size();
     */
    public int getSize() {
        return inputLex.size();
    }

    /**
     * Возвращает входную лексему в виде списка Лексем
     *
     * @return List
     */
    public ArrayList<Lexema> getInputLex() {
        return inputLex;
    }

    /**
     * Возвращает стек
     *
     * @return List
     */
    public ArrayList<LinkedList<Lexema>> getStack() {
        return stack;
    }

    /**
     * Возвращает полиз
     *
     * @return List
     */
    public ArrayList<LinkedList<Object>> getPoliz() {
        return poliz;
    }

    public ArrayList<Integer> getCycleSign() {
        return cycleSign;
    }

    public LinkedList<Lexema> getVariableCycleParam() {
        return variableCycleParam;
    }

    private void initFor(int index){
        stack.get(stack.size() - 1).add(inputLex.get(index));
        for (int i = 1; i < 4; i++) {//добавляем метки на for
            stack.get(stack.size() - 1).getLast().addLabel(counterLabel);
            counterLabel++;

        }
        stack.get(stack.size() - 1).getLast().addPos(index);
        cycleSign.set(cycleSign.size() - 1, 1);
    }
}
