package gram;
import java.util.ArrayList;

public class ParseGram {

	private String grammarInLine;
	private String[] parsedGram;
	private String[][] leftRight;
	private LexGram[] lex;
	public String[] reserveLex = { "main", "{", "}", "int", "char", "bool", "=", "!=", "<", ">", "<=", ">=", "==", "+",
			"-", "*", "$", "^", "printf", "scanf", "for", "to", "by", "while", "end", ",", ";" , "\"",".", "(", ")", "if",
			"then", "&&", "!!", "||", "%i", "%c", "%b", "true", "false","[","]", "id", "con", "lit" };
	
	private ArrayList <ParseRight> rightLex=new ArrayList<ParseRight>(); 

	public ParseGram(String grammarInLine) {//конструктор
		this.grammarInLine = grammarInLine;//запись в грамматику
		this.parsedGram = this.parse(grammarInLine);//разбил ее на правила
		leftRight = this.leftRight(parsedGram);//разбил на левую и пр части
		lexem(leftRight);//выделил списко лексем моей грамматики
		//print();
		parseRight(leftRight);//разибваю правую часть на альтернативы
		equal();//считываю все = 
		findFirst();//нахожу first
		findLast();//ласт
		for(int i=0;i<lex.length-reserveLex.length;i++)
		{
			lex[i].addFirstPlus(lex,lex[i]);
			lex[i].addLastPlus(lex,lex[i]);
		}
	}

	public String[] getParsedGram() {
		return parsedGram;
	}
	
	public LexGram[] getLex() {
		return lex;
	}

	public void setLex(LexGram[] lex) {
		this.lex = lex;
	}

	public String[] parse(String inputGram) {// ��������� �� �������
		int lines;
		int i = 0;

		lines = countLines(inputGram); // number of rules in grammar

		String[] rule = new String[lines];
		StringBuilder row = new StringBuilder();

		for (int current = 0; current < inputGram.length(); current++) {
			if (inputGram.charAt(current) == '\n') {
				String[] currRule = row.toString().split("");
				rule[i] = "";
				for (String str : currRule) {
					rule[i] += str;
				}
				row = new StringBuilder();
				i++;
				continue;
			} else {
				row.append(inputGram.charAt(current));
			}
		}

		return rule;
	}

	private int countLines(String inputGram) {// ������� ���-�� �����
		int newLine = 0;
		for (int current = 0; current < inputGram.length(); current++) {
			if (inputGram.charAt(current) == '\n') {
				newLine++;
			}
		}
		return newLine;
	}

	public String[][] leftRight(String[] rule)
	{
		String[][] leftRight = new String[rule.length][];
		String[][] leftRightTemp = null;
		String[][] temp = new String[rule.length][2];

		for (int i = 0; i < rule.length; i++) {
			temp[i] = rule[i].split("::=");
			//temp[i][1] = temp[i][1].replace("|", "/");
			String[] right = temp[i][1].split("/ ");
			leftRightTemp = new String[rule.length][right.length + 1];
			leftRightTemp[i][0] = temp[i][0];
			for (int j = 0; j < right.length; j++) {
				leftRightTemp[i][j + 1] = right[j];
			}
			leftRight[i] = leftRightTemp[i];
		}
		
		return leftRight;
	}

	public void lexem(String[][] leftRight)// �������� �������
	{
		lex = new LexGram[leftRight.length + reserveLex.length];
		for (int i = 0; i < leftRight.length; i++) {
			lex[i] = new LexGram(leftRight[i][0], i);
		}
		for (int i = leftRight.length, j = 0; i < lex.length; i++, j++) {
			lex[i] = new LexGram(reserveLex[j], i);

		}

	}
	
	 public void parseRight(String[][] leftRight)//��������� ������ �����
	 { 
		 for(int i=0;i<leftRight.length;i++)
			 for(int j=1;j<leftRight[i].length;j++)
			 {
				 
				 String[] temp = leftRight[i][j].split(" ");
				 for(int k=0;k<temp.length;k++)
				 {
					 ParseRight parseRightTemp=new ParseRight("",0,0,0);
					 parseRightTemp.setLine(i);
					 parseRightTemp.setLexema(temp[k]);
					 parseRightTemp.setAlterLineNum(j-1);
					 parseRightTemp.setPosInLine(k);
					 rightLex.add(parseRightTemp);//������ ������ � ������ �����
				 }	
			 }
		
		 for(int i=0;i<rightLex.size();i++)
		 {
			 for(int j=0;j<lex.length;j++)
			 {
				 if(rightLex.get(i).getLexema().equals(lex[j].getLex()))
				 {
					 rightLex.get(i).setLexCode(lex[j].getLexCode());
					 //System.out.println(rightLex.get(i).getLexema()+" "+rightLex.get(i).getLexCode()+" "+rightLex.get(i).getLine()+" "+rightLex.get(i).getAlterLineNum()+" "+rightLex.get(i).getPosInLine());
				 }
			 }			 
		 }
		 for(int i=0;i<rightLex.size();i++)
		 {
			 if(rightLex.get(i).getLexCode()==0)
			 {
				 rightLex.get(i).setLexCode(40);
			 }	 
		 }
		 /*for(int i=0;i<rightLex.size();i++)
		 {
			 System.out.println(rightLex.get(i).getLexema()+" "+rightLex.get(i).getLexCode()+" "+rightLex.get(i).getLine()+" "+rightLex.get(i).getAlterLineNum()+" "+rightLex.get(i).getPosInLine());	
		 }*/
	 }

	public void print() {
		for (int i = 0; i < lex.length; i++) {
			System.out.println(lex[i].getLex()+" "+lex[i].getLexCode());

		}
	}
	
	public void equal()//���� ����������
	{
		for(int i=0;i<rightLex.size()-1;i++)
		{
			if(rightLex.get(i).getLine()==rightLex.get(i+1).getLine())
			{
				if(rightLex.get(i).getAlterLineNum()==rightLex.get(i+1).getAlterLineNum())
				{
					for(int j=0;j<lex.length;j++)
					{
						if(lex[j].getLexCode()==rightLex.get(i).getLexCode())
						{
							lex[j].addEqual(rightLex.get(i+1));
							//System.out.print(lex[j].getLex()+" "+rightLex.get(i+1).getLexema()+" ");
						}
					}
					//System.out.println();
					//Equal temp=new Equal(rightLex.get(i),rightLex.get(i+1));
					//equals.add(temp);
				}
			}
		}
		/*for(int i=0;i<equals.size();i++)
		{
			System.out.println(equals.get(i).getLexFirst().getLexema()+" "+equals.get(i).getLexSecond().getLexema());	
		}*/
	}
	
	
	
	public void findFirst()//���� First
	{
		for(int i=0;i<rightLex.size();i++)
		{
			if(rightLex.get(i).getPosInLine()==0)//��������� �� ��, ��� �� ��� ��� ������ ��-��� � ������������
			{
				for(int j=0;j<lex.length;j++)
				{
					if(lex[j].getLexCode()==rightLex.get(i).getLine())//���� �������, ������� ���� ����� � ��� �� ����
					{
						if(lex[j].getFirsts().size()==0)//���� ������ first ��� ������
						{
							lex[j].addFirst(rightLex.get(i));
							//System.out.println(i+" "+j+" "+rightLex.get(i).getLexema()+" "+rightLex.get(i).getLexCode()+" "+lex[j].getLex());	
						}
						else
							if(lex[j].getFirsts().get(lex[j].getFirsts().size()-1).getLexCode()!=rightLex.get(i).getLexCode())
								//���� �� ������, �� ��� �� �� ���������� ��� ���� ���� � ����
							{
								lex[j].addFirst(rightLex.get(i));
								//System.out.println(i+" "+j+" "+rightLex.get(i).getLexema()+" "+rightLex.get(i).getLexCode()+" "+lex[j].getLex());	
							}
					}
					
				}
				
			}
		}
	}
	public void findLast()//���� Last
	{
		for(int i=0;i<rightLex.size()-1;i++)
		{
			if(rightLex.get(i).getLine()<rightLex.get(i+1).getLine() || rightLex.get(i).getAlterLineNum()<rightLex.get(i+1).getAlterLineNum())
				//�������� �� ��, ��� ��� ������� ��������� � ������������
			{
				for(int j=0;j<lex.length;j++)
				{
					if(lex[j].getLexCode()==rightLex.get(i).getLine())//���� �������, ������� ���� ����� � ��� �� ����
					{
						if(lex[j].getLasts().size()==0)//���� ������ last ��� ������
						{
							lex[j].addLast(rightLex.get(i));
							//System.out.println(i+" "+j+" "+rightLex.get(i).getLexema()+" "+rightLex.get(i).getLexCode()+" "+lex[j].getLex());	
						}
						else
							if(lex[j].getLasts().get(lex[j].getLasts().size()-1).getLexCode()!=rightLex.get(i).getLexCode())
								//���� �� ������, �� ��� �� �� ���������� ��� ���� ���� � ����
							{
								lex[j].addLast(rightLex.get(i));
								//System.out.println(i+" "+j+" "+rightLex.get(i).getLexema()+" "+rightLex.get(i).getLexCode()+" "+lex[j].getLex());	
							}
					}
					
				}
				
			}
		}
	}
}
