package mpa;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import la.Lexema;

public class MPA {//таблица переходов transition table
	public String[] AMain = { "1", "2", "3", "3", "3", "4", "5","5", "6", "7", "8" };
	public String[] MitkaMain = { "main", "(", "int", "bool", "char", "id", ";",",", "{", ";", "}" };
	public String[] BMain = { "2", "3", "4", "4", "4", "5", "3","4", "п/а оп", "8", "" };
	public String[] StekMain = { "", "", "", "", "", "","", "", "↓7", "", "" };
	public String[] SemMain = { "[≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] er","[≠] er", "[≠] er", "[≠] er", "[≠] er", "",
			"[=]вихід [≠]<п/а оп> ↓7" };

	public String[] AExp = { "41", "42", "42", "42", "43", "43", "43", "43", "43", "44" };
	public String[] MitkaExp = { "-", "id", "con", "(", "+", "-", "*", "/", "^", ")" };
	public String[] BExp = { "42", "43", "43", "<п/а> вираз", "42", "42", "42", "42", "42", "43" };
	public String[] StekExp = { "", "", "", "↓44", "", "", "", "", "", "" };
	public String[] SemExp = { "[≠] 42", "[≠] er", "[≠] er", "[≠] er", "[≠] вихід", "[≠] вихід", "[≠] вихід",
			"[≠] вихід", "[≠] вихід", "[≠] er" };

	public String[] ABool = { "51", "51", "52", "52", "52", "52", "52", "52", "53", "53", "54" };
	public String[] MitkaBool = { "!!", "[", "==", "!=", "<", ">", "<=", ">=", "||", "&&", "]" };
	public String[] BBool = { "51", "<п/а> вираз", "<п/а> вираз", "<п/а> вираз", "<п/а> вираз", "<п/а> вираз",
			"<п/а> вираз", "<п/а> вираз", "51", "51", "53" };
	public String[] StekBool = { "", "↓54", "↓53", "↓53", "↓53", "↓53", "↓53", "↓53", "", "", "" };
	public String[] SemBool = { "[≠]<п/а> вираз ↓52", "[≠]<п/а> вираз ↓52", "[≠] er", "[≠] er", "[≠] er", "[≠] er",
			"[≠] er", "[≠] er", "[≠] вихід", "[≠] вихід", "[≠] er" };

	public String[] AOp = { "21", "21", "21", "21", "21", "22", "23", "24", "25", "25", "25", "26", "27", "27", "27",
			"28", "29", "30", "31", "32", "33", "34", "35", "36", "36", "37", "37", "37", "37", "37", "37", "38", "39",
			"20","19","40" };
	public String[] MitkaOp = { "id", "scanf", "printf", "for", "if", "=", "lit", "(", "%i", "&b", "%c", ".", "lit",
			"id", "con", "id", "=", "to", "by", "while", "(", ")", "end", "true", "false", "==", "!=", "<", ">", "<=",
			">=", "then", ";", "","end",")" };
	public String[] BOp = { "22", "24", "24", "28", "36", "23", "", "25", "26", "26", "26", "27", "", "", "", "29",
			"<п/а> вираз", "<п/а> вираз", "<п/а> вираз", "33", "<п/а>лог вираз", "<п/а> оп", "", "38", "38",
			"<п/а> вираз", "<п/а> вираз", "<п/а> вираз", "<п/а> вираз", "<п/а> вираз", "<п/а> вираз", "21", "19", "","","" };
	public String[] StekOp = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "↓30", "↓31", "↓32", "",
			"↓34", "↓35", "", "", "", "↓38", "↓38", "↓38", "↓38", "↓38", "↓38", "", "", "","","" };
	public String[] SemOp = { "[≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] er",
			"[≠]<п/а> вираз ↓20, [=] вихід", "[≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] er", "[=] вихід [≠] er",
			"[=] вихід [≠] er", "[=] вихід [≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] er",
			"[≠] er", "[=] вихід [≠] <п/а> оп ↓39", "[≠] <п/а> вираз ↓37", "[≠] <п/а> вираз ↓37", "[≠] er", "[≠] er",
			"[≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] er", "[≠] вихід","[=] вихід [≠]","<п/а> оп ↓39","[=] вихід [≠] er" };

	private Lexema[] Lex;
	private int len;
	private int i;
	private boolean flag;
	private ArrayList<Integer> stack = new ArrayList<Integer>();
	private boolean exitFlag = false;
	private String[] T;
	private String[] Tstack;

	public MPA(Lexema[] lex) {
		super();
		Lex = lex;
		len = Lex.length;
		i = 0;
		flag = false;
		T = new String[Lex.length];
		Tstack = new String[Lex.length];
		for(int k=0;k<Lex.length;k++)
			Tstack[k]="";
	}

	public int getLen() {
		return len;
	}

	public String[] getT() {
		return T;
	}

	public String[] getTstack() {
		return Tstack;
	}

//основная функция
	public void Parse() {
		State1();
		if (i != len - 1) {
			JOptionPane.showMessageDialog(null, "Symbols after }");
			flag = true;
		}
		if (flag == true) {
			exitFlag = false;
		}
		//Print();
		// return exitFlag;

	}

	// п/а головний автомат
	//состояния
	public void State1() {
		AddState(i, "1");
		if (i < len && Lex[i].getCode() == 0) {
			i++;
			State2();
		} else {
			JOptionPane.showMessageDialog(null, "Expect main on the start");
			flag = true;
		}
	}

	public void State2() {
		String t = AddState(i, "2");
		//System.out.println(t);
		if (i < len && Lex[i].getCode() == 28) {
			i++;
			State3();
		} else {
			JOptionPane.showMessageDialog(null, "Expect ( after the main");
			flag = true;
		}
	}

	public void State3() {
		AddState(i, "3");
		if (i < len && (Lex[i].getCode() == 3 || Lex[i].getCode() == 4 || Lex[i].getCode() == 5)) {

			i++;
			State4();
		} else if (Lex[i].getCode() == 29) {

			i++;
			State6();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State4() {
		AddState(i, "4");
		if (i < len && Lex[i].getCode() == 40) {
			i++;
			State5();
		} else {
			JOptionPane.showMessageDialog(null, "Error with id in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State5() {
		AddState(i, "5");
		if (i < len && Lex[i].getCode() == 25) {

			i++;
			State4();
		} else if (i < len && Lex[i].getCode() == 26) {

			i++;
			State3();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State6() {
		AddState(i, "6");//метод для подсчета состояния
		if (i < len && Lex[i].getCode() == 1) {
			stack.add(7);//стек добавляем
			AddStack(i, stack);//заносим
			i++;
			State21();// п/а оп
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State7() {
		AddState(i, "7");
		if (i < len && Lex[i].getCode() == 26) {
			AddStack(i, stack);
			i++;
			State8();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State8() {
		AddState(i, "8");
		if (i < len && Lex[i].getCode() == 2) {
			AddStack(i, stack);
			exitFlag = true;
			JOptionPane.showMessageDialog(null, "Successfully");
		} else {
			stack.add(7);
			State21();// п/а оп
		}
	}

	// п/а оп
	public void State20() {
		AddState(i, "20");
		WhereGo();
	}

	public void State21() {
		AddState(i, "21");
		if (i < len && Lex[i].getCode() == 40) {
			AddStack(i, stack);
			i++;
			State22();
		} else if (i < len && (Lex[i].getCode() == 18 || Lex[i].getCode() == 19)) {
			AddStack(i, stack);
			i++;
			State24();
		} else if (i < len && Lex[i].getCode() == 20) {
			AddStack(i, stack);
			i++;
			State28();
		} else if (i < len && Lex[i].getCode() == 30) {
			AddStack(i, stack);
			i++;
			State36();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State22() {
		AddState(i, "22");
		//System.out.println();
		if (i < len && Lex[i].getCode() == 6) {
			AddStack(i, stack);
			i++;
			State23();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State23() {
		AddState(i, "23");
		if (i < len && Lex[i].getCode() == 41) {
			AddStack(i, stack);
			i++;
			WhereGo();
		} else {
			stack.add(20);
			AddStack(i, stack);
			State41();// п/а вираз
		}
	}

	public void State24() {
		AddState(i, "24");
		if (i < len && Lex[i].getCode() == 28) {
			AddStack(i, stack);
			i++;
			State25();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State25() {
		AddState(i, "25");
		if (i < len && (Lex[i].getCode() == 35 || Lex[i].getCode() == 36 || Lex[i].getCode() == 37)) {
			AddStack(i, stack);
			i++;
			State26();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State26() {
		AddState(i, "26");
		if (i < len && Lex[i].getCode() == 25) {
			AddStack(i, stack);
			i++;
			State27();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State27() {
		AddState(i, "27");
		if (i < len && (Lex[i].getCode() == 40 || Lex[i].getCode() == 41)) {
			AddStack(i, stack);
			i++;
			State40();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State40() {
		AddState(i, "40");
		if (i < len && Lex[i].getCode() == 29) {
			AddStack(i, stack);
			i++;
			WhereGo();
		}
	}

	public void State28() {
		AddState(i, "28");
		if (i < len && Lex[i].getCode() == 40) {
			AddStack(i, stack);
			i++;
			State29();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State29() {
		AddState(i, "29");
		if (i < len && Lex[i].getCode() == 6) {
			stack.add(30);
			AddStack(i, stack);

			i++;
			State41();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State30() {
		AddState(i, "30");
		if (i < len && Lex[i].getCode() == 21) {
			stack.add(31);
			AddStack(i, stack);

			i++;
			State41();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State31() {
		AddState(i, "31");
		if (i < len && Lex[i].getCode() == 22) {
			stack.add(32);
			AddStack(i, stack);

			i++;
			State41();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State32() {
		AddState(i, "32");
		if (i < len && Lex[i].getCode() == 23) {
			AddStack(i, stack);
			i++;
			State33();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State33() {
		AddState(i, "33");
		if (i < len && Lex[i].getCode() == 28) {
			stack.add(34);
			AddStack(i, stack);

			i++;
			State51();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State34() {
		AddState(i, "34");
		if (i < len && Lex[i].getCode() == 29) {
			stack.add(35);
			AddStack(i, stack);

			i++;
			State21();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State35() {
		AddState(i, "35");
		if (i < len && Lex[i].getCode() == 26) {
			AddStack(i, stack);
			i++;
			State19();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State19() {
		AddState(i, "19");
		if (i < len && Lex[i].getCode() == 24) {
			AddStack(i, stack);
			i++;
			WhereGo();
		} else {
			stack.add(39);
			AddStack(i, stack);
			State21();
		}
	}

	public void State39() {
		AddState(i, "39");
		if (i < len && Lex[i].getCode() == 26) {
			AddStack(i, stack);
			i++;
			State19();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State36() {
		AddState(i, "36");
		if (i < len && (Lex[i].getCode() == 38 || Lex[i].getCode() == 39)) {
			AddStack(i, stack);
			i++;
			State38();
		} else {
			stack.add(37);
			AddStack(i, stack);
			State41();
		}
	}

	public void State37() {
		AddState(i, "37");
		if (i < len && (Lex[i].getCode() == 7 || Lex[i].getCode() == 8 || Lex[i].getCode() == 9
				|| Lex[i].getCode() == 10 || Lex[i].getCode() == 11 || Lex[i].getCode() == 12)) {
			stack.add(38);
			AddStack(i, stack);

			i++;
			State41();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State38() {
		AddState(i, "38");
		if (i < len && Lex[i].getCode() == 31) {
			AddStack(i, stack);
			i++;
			State21();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	// п/а вираз
	public void State41() {
		AddState(i, "41");
		if (i < len && Lex[i].getCode() == 14) {
			AddStack(i, stack);
			i++;
			State42();
		} else {
			AddStack(i, stack);
			State42();
		}
	}

	public void State42() {
		AddState(i, "42");
		if (i < len && (Lex[i].getCode() == 40 || Lex[i].getCode() == 41)) {
			AddStack(i, stack);
			i++;
			State43();
		} else if (i < len && Lex[i].getCode() == 28) {
			stack.add(44);
			AddStack(i, stack);
			i++;
			State41();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State43() {
		AddState(i, "43");
		if (i < len && (Lex[i].getCode() == 13 || Lex[i].getCode() == 14 || Lex[i].getCode() == 15
				|| Lex[i].getCode() == 16 || Lex[i].getCode() == 17)) {
			AddStack(i, stack);
			i++;
			State42();
		} else {
			WhereGo();
		}
	}

	public void State44() {
		AddState(i, "44");
		if (i < len && Lex[i].getCode() == 29) {
			AddStack(i, stack);
			i++;
			State43();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	// п/а лог вираз
	public void State51() {
		AddState(i, "51");
		if (i < len && Lex[i].getCode() == 33) {
			AddStack(i, stack);
			i++;
			State51();
		} else if (i < len && Lex[i].getCode() == 28) {
			stack.add(54);
			AddStack(i, stack);
			i++;
			State41();
		} else {
			stack.add(52);
			AddStack(i, stack);
			State41();
		}
	}

	public void State52() {
		AddState(i, "52");
		if (i < len && (Lex[i].getCode() == 7 || Lex[i].getCode() == 8 || Lex[i].getCode() == 9
				|| Lex[i].getCode() == 10 || Lex[i].getCode() == 11 || Lex[i].getCode() == 12)) {
			stack.add(53);
			AddStack(i, stack);
			i++;
			State41();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}

	public void State53() {
		AddState(i, "53");
		if (i < len && (Lex[i].getCode() == 34 || Lex[i].getCode() == 32)) {
			AddStack(i, stack);
			i++;
			State51();
		} else {
			WhereGo();
		}
	}

	public void State54() {
		AddState(i, "54");
		if (i < len && Lex[i].getCode() == 7) {
			AddStack(i, stack);
			i++;
			State53();
		} else {
			JOptionPane.showMessageDialog(null, "Error in line " + Lex[i].getLine());
			flag = true;
		}
	}
//после выхода, если стек и если да, то куда идти
	public void WhereGo() {
		int last = stack.size() - 1;
		int a = 0;
		if (last == -1) {
			State7();
		} else
			a = stack.get(last);

		if (a == 7) {
			stack.remove(last);
			State7();
		} else if (a == 20) {
			stack.remove(last);
			State20();
		} else if (a == 30) {
			stack.remove(last);
			State30();
		} else if (a == 31) {
			stack.remove(last);
			State31();
		} else if (a == 32) {
			stack.remove(last);
			State32();
		} else if (a == 34) {
			stack.remove(last);
			State34();
		} else if (a == 35) {
			stack.remove(last);
			State35();
		} else if (a == 39) {
			stack.remove(last);
			State39();
		} else if (a == 37) {
			stack.remove(last);
			State37();
		} else if (a == 38) {
			stack.remove(last);
			State38();
		} else if (a == 44) {
			stack.remove(last);
			State44();
		} else if (a == 52) {
			stack.remove(last);
			State52();
		} else if (a == 53) {
			stack.remove(last);
			State53();
		}
	}

//добавить состояние
	public String AddState(int i, String st) {
		if (T[i] == null) {
			T[i] = st;
		} else {
			T[i] += " ";
			T[i] += st;
		}
		return T[i];
	}
//добавляем стек
	public String AddStack(int i, ArrayList<Integer> stack) {
		for (int p = 0; p < stack.size(); p++) {
			Tstack[i] += Integer.toString(stack.get(p));
			Tstack[i] += "  ";
		}
		return Tstack[i];
	}
}
