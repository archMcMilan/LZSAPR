package mpa;
import javax.swing.JOptionPane;

public class AMB {//класс для задания правил class for rules input
	private int a;
	private int mitka;
	private int b;
	private int stack;
	private String SemNotEq;
	
	public AMB() {
		super();
	}

	public AMB(int a, int mitka, int b, int stack, String semNotEq) {
		super();
		this.a = a;
		this.mitka = mitka;
		this.b = b;
		this.stack = stack;
		SemNotEq = semNotEq;
	}
	
	
	
	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getMitka() {
		return mitka;
	}

	public void setMitka(int mitka) {
		this.mitka = mitka;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getStack() {
		return stack;
	}

	public void setStack(int stack) {
		this.stack = stack;
	}

	public String getSemNotEq() {
		return SemNotEq;
	}

	public void setSemNotEq(String semNotEq) {
		SemNotEq = semNotEq;
	}

	
}
