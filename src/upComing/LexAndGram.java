package upComing;

import java.util.ArrayList;

import gram.LexGram;

//класс в котором записаны
public class LexAndGram {
	LexGram lex;//list of lexems that gram contains
	public ArrayList<Integer> less;//list of indexes lexems that are less 
	public ArrayList<Integer> more;
	public ArrayList<Integer> equal;
	

	public LexAndGram(LexGram lex, String[] data) {
		less=new ArrayList<>();
		more=new ArrayList<>();
		equal=new ArrayList<>();
		this.lex = lex;
		initLME(data);
		//show();
	}

	public LexGram getLex() {
		return lex;
	}

	public void initLME(String[] data) {
		for (int i = 1; i < data.length; i++) {
			if(!data[i].equals("")){
				if (data[i].equals("<")) {
					less.add(i-1);
				} else {
					if (data[i].equals("=")) {
						equal.add(i-1);
					} else {
						if (data[i].equals(">")) {
							more.add(i-1);
						}
					}
				}
			}
			
		}
	}

	public void show() {
		System.out.println(lex.getLex() + " " + lex.getLexCode() + " ");
		System.out.print("less:");
		for (int i = 0; i < less.size(); i++)
			System.out.print(less.get(i)+ " ");
		System.out.println();
		System.out.print("more:");
		for (int i = 0; i < more.size(); i++)
			System.out.print(more.get(i)+ " ");
		System.out.println();
		System.out.print("equal:");
		for (int i = 0; i < equal.size(); i++)
			System.out.print(equal.get(i)+ " ");
		System.out.println();
		System.out.println();
		
	}

}
