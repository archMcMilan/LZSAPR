package execute;

import la.Lexema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Aртем on 10.05.2016.
 */
public class Executor {
    private ArrayList<LinkedList<Object>> poliz;
    private HashMap<String, Integer> identificators = new HashMap<>();
    private LinkedList<Lexema> stack = new LinkedList<>();
    private LinkedList<Boolean> boolList = new LinkedList<>();

    private int constAmount = 0;

    protected static final int ID = 43;
    protected static final int CON = 44;
    protected static final int ASSIGNED = 6;//=
    protected static final int PLUS = 13;
    protected static final int MINUS = 14;
    protected static final int MULTIPLY = 15;
    protected static final int DIVISION = 16;
    protected static final int INVOLUTION = 17;
    protected static final int UNARY_MINUS = 45;
    protected static final int DOT = 28;
    protected static final int PERCENT_I = 36;
    protected static final int PERCENT_C = 37;
    protected static final int PERCENT_B = 38;
    protected static final int PRINTF = 18;
    protected static final int SCANF = 19;
    protected static final int NON_EQUAL = 7;
    protected static final int LESS = 8;
    protected static final int GREATER = 9;
    protected static final int LESS_EQUAL = 10;
    protected static final int GREATER_EQUAL = 11;
    protected static final int EQUAL = 12;
    protected static final int AND = 33;
    protected static final int NOT = 34;
    protected static final int OR = 35;

    private int i = 0;
    private int j = 0;

    private String output = "";

    private LinkedList<Integer> workCells = new LinkedList<>();//список рабочих меток
    private LinkedList<Integer> labelsPosI = new LinkedList<>();//позиции наальной метки в цикле
    private LinkedList<Integer> labelsPosJ = new LinkedList<>();//позиции наальной метки в цикле
    private LinkedList<Integer> label=new LinkedList<>();//позиции выхода из цикла

    public static final String TO = "1 = m[";
    public static final String BY = "= r[";
    public static final String WHILE = "0 == m[";
    public static final String END = "БП m[";

    public boolean exit = false;

    public Executor() {
    }

    public Executor(ArrayList<LinkedList<Object>> poliz) {
        this.poliz = poliz;
    }


    /**
     * метод идет по полизу и проводит действия
     */
    public void execute() {
        int cycleStart = 0;

        for (i = 0; i < poliz.size(); i++) {//проход по внешенму циклу поиза
            //проход по внутреннему
            for (j = 0; j < poliz.get(i).size(); j++) {
                if (poliz.get(i).get(j) instanceof Lexema) {
                    int code = ((Lexema) poliz.get(i).get(j)).getCode();
                    /*id*/
                    if (code == ID) {
                        identificators.putIfAbsent(((Lexema) poliz.get(i).get(j)).getLexem(), 0);//кладем в список идентификаторов
                        stack.add(((Lexema) poliz.get(i).get(j)));//заносим в стек
                        /*константа*/
                    } else if (code == CON) {
                        stack.add(((Lexema) poliz.get(i).get(j)));
                        //если операция
                    } else if (code == ASSIGNED || (code >= PLUS && code <= INVOLUTION)) {
                        if (code == ASSIGNED) {//присвоение
                            if (stack.getLast().getCode() == CON) {
                                int value = Integer.parseInt(stack.pollLast().getLexem());
                                cycleStart = value;

                                String key = stack.pollLast().getLexem();
                                identificators.put(key, value);
                            } else {
                                Lexema temp = stack.pollLast();//вытаскиваем из стека последнюю лексему
                                identificators.put(stack.pollLast().getLexem(), Integer.parseInt(temp.getLexem()));
                            }
                        } else {
                            int rightValue = idOrCon();//разбиваем на правую
                            int leftValue = idOrCon();// и левую часть операции
                            if (code == PLUS) {
                                stack.add(new Lexema(0, (leftValue + rightValue) + "", CON, 0));
                            } else if (code == MINUS) {
                                stack.add(new Lexema(0, (leftValue - rightValue) + "", CON, 0));
                            } else if (code == MULTIPLY) {
                                stack.add(new Lexema(0, (leftValue * rightValue) + "", CON, 0));
                            } else if (code == DIVISION) {
                                stack.add(new Lexema(0, (leftValue / rightValue) + "", CON, 0));
                            } else if (code == INVOLUTION) {
                                stack.add(new Lexema(0, (int) (Math.pow(leftValue, rightValue)) + "", CON, 0));
                            }
                        }
                    } else if (code == UNARY_MINUS) {//если унарный минус
                        int rightValue = Integer.parseInt(stack.pollLast().getLexem());
                        stack.add(new Lexema(0, (-rightValue) + "", CON, 0));
                    } else if (code == DOT) {//если встретили точку
                        j++;//идем на следующий элемент по ПОЛИЗ
                        int codeNext = ((Lexema) poliz.get(i).get(j)).getCode();//код следующего символа
                        if (codeNext == PERCENT_I || codeNext == PERCENT_B || codeNext == PERCENT_C) {//если это %i,%b,%c
                            j++;
                            int codeScanOrPrint = ((Lexema) poliz.get(i).get(j)).getCode();//считали код следующей лексемы после %
                            if (codeScanOrPrint == SCANF) {//если это scanf
                                scanMessage();
                            } else if (codeScanOrPrint == PRINTF) {
                                printMessage(idOrCon());
                            }
                        }
                    } else if (code >= NON_EQUAL && code <= EQUAL) {
                        if (code == NON_EQUAL) {//присвоение
                            if (!logicOperator(NON_EQUAL)) {
                                if (!findUpl()) {
                                    upl();
                                }
                            }
                        } else if (code == LESS) {
                            if (!logicOperator(LESS)) {
                                if (!findUpl()) {
                                    upl();
                                }
                            }
                        } else if (code == GREATER) {
                            if (!logicOperator(GREATER)) {
                                if (!findUpl()) {
                                    upl();
                                }
                            }
                        } else if (code == LESS_EQUAL) {
                            if (!logicOperator(LESS_EQUAL)) {
                                if (!findUpl()) {
                                    upl();
                                }
                            }
                        } else if (code == GREATER_EQUAL) {
                            if (!logicOperator(GREATER_EQUAL)) {
                                if (!findUpl()) {
                                    upl();
                                }
                            }
                        } else if (code == EQUAL) {
                            if (!logicOperator(EQUAL)) {
                                if (!findUpl()) {
                                    upl();
                   }
                            }
                        }
                    } else if (code >= AND && code <= OR) {
                        boolean rightValue = boolList.pollLast();//разбиваем на правую
                        boolean leftValue = boolList.pollLast();// и левую часть операции
                        if (code == AND) {
                            if (leftValue && rightValue) {
                                boolList.add(true);
                            } else {
                                boolList.add(false);
                                upl();
                            }
                        } else if (code == NOT) {
                            if (!rightValue) {
                                boolList.add(leftValue);
                                boolList.add(true);
                            } else {
                                boolList.add(leftValue);
                                boolList.add(false);
                                upl();
                            }
                        } else if (code == OR) {
                            if (leftValue || rightValue) {
                                boolList.add(true);
                            } else {
                                boolList.add(false);
                                upl();
                            }
                        }
                    }
                } else if (poliz.get(i).get(j) instanceof String) {//цикл
                    if (((String) poliz.get(i).get(j)).contains(TO)) {//to
                        //System.out.println("Add workCell="+workCells.size());
                        workCells.add(1);
                        workCells.add(0);
                        workCells.add(0);
                        labelsPosI.add(i);
                        labelsPosJ.add(j);

                    } else if (((String) poliz.get(i).get(j)).contains(BY) && !((String) poliz.get(i).get(j)).contains(TO)
                            && !((String) poliz.get(i).get(j)).contains(WHILE)) {//by
                        //System.out.println("Size="+workCells.size()+" "+stack.getLast().getLexem());
                        workCells.set(workCells.size() - 1, Integer.parseInt(stack.pollLast().getLexem()));
                    } else if (((String) poliz.get(i).get(j)).contains(BY) && ((String) poliz.get(i).get(j)).contains(WHILE)) {//while
                        workCells.set(workCells.size() - 2, Integer.parseInt(stack.pollLast().getLexem()));
                        String polizLexem[] = ((String) poliz.get(i).get(j)).split(" ");
                        int idPos = 7;
                        int idPosExit = 24;
                        label.add(Character.getNumericValue(polizLexem[idPosExit].charAt(2)));
                        int temp = identificators.get(polizLexem[idPos]);
                        if (workCells.get(workCells.size() - 3) == 0) {
                            identificators.put(polizLexem[idPos], temp + workCells.get(workCells.size() - 2));
                        }
                        workCells.set(workCells.size() - 3, 0);
                        if ((identificators.get(polizLexem[idPos]) - workCells.get(workCells.size() - 1)) * workCells.get(workCells.size() - 2) <= 0) {
                            j++;
                        } else {
                            //System.out.println("Out1:"+i+" "+j+" "+label+" "+labelsPosI+" "+labelsPosJ);
                            workCells.pollLast();
                            workCells.pollLast();
                            workCells.pollLast();
                            findLabelInPoliz(label.getLast());
                            //System.out.println("out2:"+i+" "+j+" "+label+" "+labelsPosI+" "+labelsPosJ);
                            label.pollLast();
                            labelsPosI.pollLast();
                            labelsPosJ.pollLast();
                        }
                    } else if (((String) poliz.get(i).get(j)).contains(END)) {//}end
                        //System.out.println("End:"+i+" "+j+" "+label+" "+labelsPosI+" "+labelsPosJ);
                        i = labelsPosI.getLast();
                        j = labelsPosJ.getLast();
                        label.pollLast();
                    }
                }
                if (i == poliz.size()) {
                    break;
                }
            }

        }
        printMessage("Process finished");
        while (stack.size() > 0) {
            System.out.println(stack.pollLast().getLexem());
        }
    }

    public int idOrCon() {
        if (stack.getLast().getCode() == ID) {//если справа id
            String key = stack.pollLast().getLexem();
            return identificators.get(key);//то достаем ее значение
        } else {//если константа
            return Integer.parseInt(stack.pollLast().getLexem());//достаем ее значение
        }
    }

    public void printMessage(String message) {
        System.out.println("Output:" + message);
        output += message + "\n";
    }

    public void printMessage(int message) {
        System.out.println("Output:" + message);
        output += message + "\n";
    }

    public void scanMessage() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input:");
        if (sc.hasNextInt()) {
            int value = sc.nextInt();
            identificators.put(stack.pollLast().getLexem(), value);
        } else {
            printMessage("Illegal input");
        }
    }

    /**
     * переход по upl
     */
    public void upl() {
        j++;
        String transit = (String) poliz.get(i).get(j);
        int label = Character.getNumericValue(transit.charAt(2));
        findLabel(label);
    }

    /**
     * ищем метку
     */
    private void findLabel(int label) {
        boolean exitFlag = false;
        while (i < poliz.size()) {
            while (j < poliz.get(i).size()) {
                if (poliz.get(i).get(j) instanceof Lexema) {
                    if (((Lexema) poliz.get(i).get(j)).getLabel().size() != 0) {
                        if (((Lexema) poliz.get(i).get(j)).containsLabel(label)) {
                            j++;
                            exitFlag = true;
                            break;
                        } else {
                            j++;
                        }
                    } else {
                        j++;
                    }
                } else {
                    j++;
                }
            }
            if (exitFlag) {
                break;
            }

            i++;
            j = 0;
        }
    }

    private void findLabelInPoliz(int label) {
        boolean exitFlag = false;
        while (i < poliz.size()) {
            while (j < poliz.get(i).size()) {
                if (poliz.get(i).get(j) instanceof String) {
                        if (((String) poliz.get(i).get(j)).contains("m["+label+"]:")) {
                            j++;
                            exitFlag = true;
                            break;
                        } else {
                            j++;
                        }
                } else {
                    j++;
                }
            }
            if (exitFlag) {
                break;
            }
            i++;
            j = 0;
        }
    }


    public boolean findUpl() {
        if (j + 1 < poliz.get(i).size()) {
            if (poliz.get(i).get(j + 1) instanceof Lexema) {
                if (((Lexema) poliz.get(i).get(j + 1)).getCode() == ID || ((Lexema) poliz.get(i).get(j + 1)).getCode() == CON
                        || ((Lexema) poliz.get(i).get(j + 1)).getCode() == AND || ((Lexema) poliz.get(i).get(j + 1)).getCode() == OR) {
                    return true;
                }
            }

        } else if (i + 1 < poliz.size()) {
            if (poliz.get(i + 1).get(j) instanceof Lexema) {
                if (((Lexema) poliz.get(i + 1).get(j)).getCode() == ID || ((Lexema) poliz.get(i + 1).get(j)).getCode() == CON
                        || ((Lexema) poliz.get(i + 1).get(j)).getCode() == AND || ((Lexema) poliz.get(i + 1).get(j)).getCode() == OR) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean logicOperator(int sample) {
        int rightValue = idOrCon();//разбиваем на правую
        int leftValue = idOrCon();// и левую часть операции
        if (sample == NON_EQUAL) {//присвоение
            if (leftValue != rightValue) {
                boolList.add(true);
                return true;
            }
        } else if (sample == LESS) {
            if (leftValue < rightValue) {
                boolList.add(true);
                return true;
            }
        } else if (sample == GREATER) {
            if (leftValue > rightValue) {
                boolList.add(true);
                return true;
            }
        } else if (sample == LESS_EQUAL) {
            if (leftValue <= rightValue) {
                boolList.add(true);
                return true;
            }
        } else if (sample == GREATER_EQUAL) {
            if (leftValue >= rightValue) {
                boolList.add(true);
                return true;
            }
        } else if (sample == EQUAL) {
            if (leftValue == rightValue) {
                boolList.add(true);
                return true;
            }
        }
        boolList.add(false);
        return false;
    }

    public String output() {
        return output;
    }
}

