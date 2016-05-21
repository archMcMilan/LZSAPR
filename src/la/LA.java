package la;
import javax.swing.*;
import java.util.ArrayList;

public class LA {
	private int k=0;
	private int i=0;
	private String temp="";
	private boolean flag=true;//условие выхода из программы
	private String Enter=new String();//вводимый с клавиатуры текст
	private ArrayList <String> lex=new ArrayList<String>();//массив лексем которые считались
	private int len;//длина того что ввели
	private ArrayList <Integer> lineNum=new ArrayList<Integer>();
	private ArrayList <String> TRNt=new ArrayList<String>();//переменные и зарезервированые слова вперемешку 
	private ArrayList <String> CONt=new ArrayList<String>();//цифры(возможны повроты)
	private ArrayList <String> IDN=new ArrayList<String>();//переменные исключительно 
	private ArrayList <String> CON=new ArrayList<String>();//цифры(без повторов)
	private String[] TRN={"main","int","char","bool","printf","scanf","for","to","by","while","end","if","then","true","false"};//зарезервированые слова
	public String[] TABLE={"main","{","}","int","char","bool","=","!=","<",">","<=",">=","==",
			"+","-","*","$","^","printf","scanf","for","to","by","while","end", "," , ";" ,"\"",".","(",")","if","then",
			"&&","!!","||","%i","%c","%b","true","false","[","]","IDN","CON"};//зарезервированые символы
	public char[] Separators= {'*','$','^','+','-','(',')',';',',','{','}','[',']','\"','.'};
	private Lexema[] A;
	private Ident[]Idn;
	private Const[]Con;
	
	
	public Lexema[] Osn(ArrayList<String> list){		
		for(;i<list.size();i++)
		{
			Enter=Mass(list,i);
			//Enter=list;
			len=Enter.length();
			//System.out.println(Enter.length());
			
		
		
			//Разбиваем на лексеми 
			while(k<=len-1){
				if(Enter.charAt(k)==' ')
					k++;
				State1(Enter.charAt(k));
				lex.add(temp);
				temp="";
				k++;
				if(flag==false)
				{
					//System.exit(0);
				}
			}
			k=0;
			lineNum.add(lex.size());
					
			
		}
		//класс Лексема, куда записываются данные
		A=new Lexema[lex.size()];
		
		//закидываем значения номера рядка, название лексемы и кода символа 
		int p=0;
		for(int i=0;i<lex.size();i++)
		{
			Lexema B=new Lexema(-1,"",-1,-1);
			if(i==lineNum.get(p))
			{
				p++;
			}
			B.setLine(p);
			B.setLexem(lex.get(i));
			B.setCode(InitCode(lex.get(i)));
			A[i]=B;
		}
		//функция выбора идентификаторов
		IDN=isIDN(TRNt);
		
		//Если идентификатор, то создаем класс идентификаторов
		Idn=new Ident[IDN.size()];
		for(int i=0;i<IDN.size();i++)
		{
			Ident IdnB=new Ident("",-1);
			IdnB.setIdn(IDN.get(i));
			IdnB.setNum(i);
			Idn[i]=IdnB;
		}
		CON=isCon(CONt);
		Con=new Const[CON.size()];
		for(int i=0;i<CON.size();i++)
		{
			Const ConB=new Const("-1",-1);
			ConB.setCons(CON.get(i));
			ConB.setNum(i);
			Con[i]=ConB;
		}
		
		//закидываем в класс лексем идентификаторы
		for(int i=0;i<lex.size();i++)
		{
			for(int j=0;j<Idn.length;j++)
			{
				if(A[i].getLexem().equals(Idn[j].getIdn()))
				{
					A[i].setIDNCON(Idn[j].getNum());
					A[i].setCode(43);
				}
			}
		}
		
		for(int i=0;i<lex.size();i++)
		{
			for(int j=0;j<Con.length;j++)
			{
				if(A[i].getLexem().equals(Con[j].getCons()))
				{
					A[i].setIDNCON(Con[j].getNum());
					A[i].setCode(44);
				}
			}
		}
		
		
		isInit(Idn);

		return A;
		
	}
	public Lexema[] getLexem()//достать лексему
	{
		return A;
	}
	public int getLexSize()//достать лексему
	{
		return lex.size();
	}
	public int getIDNSize()//достать лексему
	{
		return IDN.size();
	}
	public int getCONSize()//достать лексему
	{
		return CON.size();
	}
	public 	Ident[] getIdent()//достать лексему
	{
		return Idn;
	}
	public 	Const[] getConst()//достать лексему
	{
		return Con;
	}

	public String Mass(ArrayList<String> list,int i)//введеный рядок переводим в массив
	{
		String mas=(String)list.get(i);		
		return mas;
	}
	
	//Состояния от 1 до 8
	public void State1(char s1)
	{
		if(Character.isDigit(s1))
		{
			temp+=s1;
			if(k<len-1){
				k++;
				char s2=Enter.charAt(k);
				State2(s2);
			}
			else{
				CONt.add(temp);
			}
		}
		else
			if (Character.isLetter(s1))
			{
				temp+=s1;
				if(k<len-1){
					k++;
					char s2=Enter.charAt(k);
					State3(s2);
				}
				else{
					TRNt.add(temp);
				}
			}
			else 
				if(s1=='<'||s1=='>'||s1=='=')
				{
					temp+=s1;
					if(k<len-1){
						k++;
						char s2=Enter.charAt(k);
						State4(s2);
					}
				}
				else
					if(s1=='!')
					{
						temp+=s1;
						if(k<len-1){
							k++;
							char s2=Enter.charAt(k);
							State5(s2);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Miss '!' after '!' in line "+i);
							flag=false;
						}
					}
					else
						if(s1=='&')
						{
							temp+=s1;
							if(k<len-1){
								k++;
								char s2=Enter.charAt(k);
								State6(s2);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Miss '&' after '&' in line "+i);
								flag=false;
							}
						}
						else
							if(s1=='|')
							{
								temp+=s1;
								if(k<len-1){
									k++;
									char s2=Enter.charAt(k);
									State7(s2);
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Miss '|' after '|' in line "+i);
									flag=false;
								}
							}
							else
								if(s1=='%')
								{
									temp+=s1;
									if(k<len-1){
										k++;
										char s2=Enter.charAt(k);
										State8(s2);
									}
									else
									{
										JOptionPane.showMessageDialog(null, "After '%' expect i or b or c in line "+i);
										flag=false;
									}
									
								}
								else
									if(isSeparator(s1))
									{
										temp+=s1;
									}
									else	
										if(s1=='"')
										{
											temp+=s1;
											if(k<len-1){
												k++;
												char s2=Enter.charAt(k);
												State9(s2);
											}
										}
										else
										{
											JOptionPane.showMessageDialog(null, "Anknown symbol in line "+i);
											flag=false;
										}
			
	}
	public void State2(char s1)
	{
		if(Character.isDigit(s1))
		{
			temp+=s1;
			if(k<len-1){
				k++;
				char s2=Enter.charAt(k);
				State2(s2);
			}
			else{
				CONt.add(temp);
			}
		}
		else
		{
			CONt.add(temp);
			k--;
		}
	}
	public void State3(char s1)
	{
		if(Character.isDigit(s1))
		{
			temp+=s1;
			if(k<len-1){
				k++;
				char s2=Enter.charAt(k);
				State3(s2);
			}
			else{
				CONt.add(temp);
			}
		}
		else
			if (Character.isLetter(s1))
			{
				temp+=s1;
				if(k<len-1){
					k++;
					char s2=Enter.charAt(k);
					State3(s2);
				}
				else{
					TRNt.add(temp);
				}
			}
			else
			{
				TRNt.add(temp);
				k--;
			}
	}
	public void State4(char s1)
	{
		if(s1=='=')
		{
			temp+='=';
		}
		else
			k--;

	}
	public void State5(char s1)
	{
		if(s1=='=')
		{
			temp+=s1;
		}
		else 
			if(s1=='!')
			{
				temp+=s1;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Not right symbol after '!' in line "+i);
				flag=false;
			}
		
	}
	public void State6(char s1)
	{
		if(s1=='&')
		{
			temp+=s1;
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Not right symbol after '!' in line "+i);
			flag=false;
		}
	}
	public void State7(char s1)
	{
		if(s1=='|')
		{
			temp+=s1;
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Not right symbol after '|' in line "+i);
			flag=false;
		}
	}
	public void State8(char s1)
	{
		if(s1=='i')
		{
			temp+=s1;
		}
		else 
			if(s1=='c')
			{
				temp+=s1;
			}
			else 
				if(s1=='b')
				{
					temp+=s1;
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Expect 'i' or 'b' or 'c' in line "+i);
					flag=false;
				}
	}
	public void State9(char s1)
	{
		if(s1!='"')
		{
			temp+=s1;
			if(k<len-1){
				k++;
				char s2=Enter.charAt(k);
				State9(s2);
			}
			else{
				JOptionPane.showMessageDialog(null,"Not right symbol in line "+i);
				flag=false;
			}
		}
		else
			if(s1=='"')
			{
				temp+=s1;
				CONt.add(temp);	
			}
	}
	
	
	public boolean isSeparator(char c)//проверка на односимвольные разделители
	{
		boolean tester=false;
		for(int i=0;i<Separators.length;i++)
		{
			if(c==Separators[i])
				tester=true;
		}
		return tester;
	}
	public ArrayList <String> isIDN(ArrayList <String> allTRN)//проверка на то, является ли строка идентификатором
	{
		ArrayList <String> outputList=new ArrayList<String>();
		for(int i=0;i<allTRN.size();i++)
		{
			//System.out.println(allTRN.get(i));
			boolean tester=false;
			for(int j=0;j<TRN.length;j++)
			{
				if(TRN[j].equals(allTRN.get(i)))
				{
					tester=true;
					break;
				}
			}
			if(tester==false)
			{
				outputList.add(allTRN.get(i));
			}
		}
		ArrayList <String> idnList=new ArrayList<String>();
		
		for(int i=0;i<outputList.size();i++)
		{
			boolean tester=false;
			for(int j=0;j<i;j++)
			{
				if(outputList.get(i).equals(outputList.get(j)))
				{
					tester=true;
					break;
				}	
			}
			if(tester==false)
			{
				idnList.add(outputList.get(i));
			}
		}
		
		return idnList;
	}
	public int InitCode(String A)//как выбрать код символа

	{
		int code=-1;
		for(int i=0;i<TABLE.length;i++)
		{
			if(A.equals(TABLE[i]))
				code=i;
		}
		return code;
	}
	public ArrayList <String> isCon(ArrayList <String> allCon)//проверка на то, является ли строка идентификатором
	{
		ArrayList <String> outputList=new ArrayList<String>();
		
		for(int i=0;i<allCon.size();i++)
		{
			//System.out.println(allCon.get(i));
			boolean tester=false;
			for(int j=0;j<i;j++)
			{
				if(allCon.get(i).equals(allCon.get(j)))
				{
					tester=true;
					break;
				}	
			}
			if(tester==false)
			{
				outputList.add(allCon.get(i));
			}
		}
		
		return outputList;
	}
	public void isInit(Ident[] A1) //инициализирована ли переменная
	{ 
		for(int i=0;i<lex.size();i++) 
		{ 
			for(int j=0;j<A1.length;j++) 
			{ 
				if(lex.get(i).equals(A1[j].getIdn())) 
				{ 
					if(A1[j].isInit()==false)
						for(int k=i;k>=0;k--) 
						{ 
							if(lex.get(k).equals("int")||lex.get(k).equals("char")||lex.get(k).equals("bool")) 
							{ 
								boolean tester=true;
								for(int k1=k;k1<=i;k1++) 
								{ 
									if(lex.get(k1).equals(";")) 
									{
										tester=false;
										JOptionPane.showMessageDialog(null, "Error:"+"'"+A1[j].getIdn()+"'"+"isn't init");
										break;
									}								
								} 
								if(tester==true)
								{
									A1[j].setInit(true);
								break;
								} 
							}
						}
					else
						for(int k=i;k>=0;k--) 
						{ 
							if(lex.get(k).equals(";"))
							{
								for(int k1=k;k1<=i;k1++) 
								{ 
									if(lex.get(k1).equals("int")||lex.get(k1).equals("char")||lex.get(k1).equals("bool")) 
									{
										JOptionPane.showMessageDialog(null, "Error:"+"'"+A1[j].getIdn()+"'"+"is duplicate");
									}								
								}
								break;
							}
						} 
				} 	
			} 
		} 
	}
	
	
}





