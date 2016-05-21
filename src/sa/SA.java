package sa;
import javax.swing.JOptionPane;

import la.Lexema;

public class SA {
	private Lexema[] Lex;
	private boolean flag=false;
	private int i = 0;
	private int len;

	public SA(Lexema[] lex) {
		super();
		Lex = lex;
		len = Lex.length;
		flag=false;
	}

	public Lexema[] getLex() {
		return Lex;
	}

	public void setLex(Lexema[] lex) {
		Lex = lex;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public boolean Analyzer() {
		
		if (Lex[i].getCode() == 0)// main
		{
			i++;
			if (i<len&&Lex[i].getCode() == 29) // (
			{
				i++;
				if (i<len&&DeclarationList()) //список оголошень
				{
					if (i<len&&Lex[i].getCode() == 30) // )
					{
						i++;
						if (i<len&&Lex[i].getCode() == 1) // {
						{
							i++;
							if (i<len&&OperatorList()) //список операторів
							{
										if (i<len&&Lex[i].getCode() == 2) //}
										{
											flag=true;
										}
										else
											JOptionPane.showMessageDialog(null, "Expect } after"+Lex[i-1].getLexem()+" in line "+ Lex[i-1].getLine());
								
							}
							else
								JOptionPane.showMessageDialog(null, "Error in line"+Lex[i-1].getLine()+" after "+Lex[i-1].getLexem());
						}
						else
							JOptionPane.showMessageDialog(null, "Expect { after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine());
					}
					else
						JOptionPane.showMessageDialog(null, "Expect ) after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine());
				}
				else
					JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
			}
			else
				JOptionPane.showMessageDialog(null,  "Expect ( after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine() );
		}
		else
			JOptionPane.showMessageDialog(null,  "Expect ) after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine());
		
		if(flag==true)
		{
			JOptionPane.showMessageDialog(null, "Successfully");
		}
		
		return flag;
	}

	public boolean DeclarationList()// список оголошень
	{
		boolean tester = false;
		boolean testerTemp = false;
		boolean flagExit = false;
		if (i<len&&Declaration()) // оголошення
		{
			if(i<len&&Lex[i].getCode()==26)
			{
				tester=true;
				i++;
			}
			else
				JOptionPane.showMessageDialog(null,  "Expect ; after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine() );
		}
		else
			JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
		while(i<len&&(Lex[i].getCode()!=30)&&flagExit==false)//)
		{
			testerTemp=false;
			i++;
			if(i<len&&Declaration())//оголошення
			{
				if(i<len&&Lex[i].getCode()==26)
				{
					tester=true;
					i++;
					testerTemp=true;
				}
			}
			else
			{
				flagExit=true;
			}
		}		
		return tester;
	}

	public boolean Declaration()// оголошення
	{
		boolean tester = false;
		if (i < len&&Type()) {
				i++;
				if (i < len&&VariableList())
					tester = true;
		} 
		return tester;
	}

	public boolean Type()// тип
	{
		boolean tester = false;
		if (Lex[i].getCode() == 3 || Lex[i].getCode() == 4 || Lex[i].getCode() == 5) // int||char||bool
		{
			tester = true;
		}
		else
			JOptionPane.showMessageDialog(null,  "Expect type after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine() );
		
		return tester;
	}

	public boolean VariableList()// список змінних
	{
		boolean tester = true;
		boolean testerTemp = false;
		boolean flagExit = false;
		if (i<len&&Lex[i].getCode() == 43) // IDN
		{
			tester=true;
			i++;
		}
		while(i<len&&Lex[i].getCode()==25&&flagExit==false)//,
		{
			testerTemp=false;
			i++;
			if(i<len&&Lex[i].getCode() == 43)//IDN
			{
				testerTemp=true;
				i++;
			}
			else
			{
				flagExit=true;
				JOptionPane.showMessageDialog(null, ",зайва в списку змінних Error in line"+Lex[i].getLine()+" "+i+" "+Lex[i].getLexem() );
			}
		}
		return tester; 
	}
	
	public boolean OperatorList()// список операторів
	{
		boolean tester = false;
		boolean testerTemp = false;
		boolean flagExit = false;
		if(i<len&&Operator())
		{
			if(i<len&&Lex[i].getCode()==26)
			{
				tester=true;
				i++;
			}
			else
				JOptionPane.showMessageDialog(null, "; після оператора відсутня Error in line"+Lex[i].getLine()+" "+i+" "+Lex[i].getLexem() );
		}
		while(i<len&&(Lex[i].getCode()!=2&&Lex[i].getCode()!=24)&&flagExit==false)//} 
		{
			testerTemp=false;
			if(i<len&&Operator())//оператор
			{
				if(i<len&&Lex[i].getCode()==26)
				{
					tester=true;
					i++;
					testerTemp=true;
				}
				else
				{
					flagExit=true;
					JOptionPane.showMessageDialog(null, "Не має ; in line"+Lex[i-1].getLine()+" "+i+" "+Lex[i-1].getLexem() );
				}
			}
			else
			{
				flagExit=true;
				JOptionPane.showMessageDialog(null, "має бути оператор змінних Error in line"+Lex[i-1].getLine()+" "+i+" "+Lex[i-1].getLexem() );
			}
		}
		return tester;
	}

	public boolean Operator()// оператор
	{
		boolean tester = false;
		if (i<len&&Assignment()||Input()||Output()||Cycle()||Сondition())
			tester = true;
		else
			JOptionPane.showMessageDialog(null, "не оператор Error in line"+Lex[i].getLine()+" "+i+" "+Lex[i].getLexem() );
		return tester;
	}

	public boolean Assignment()// присвоєння
	{
		boolean tester = false;
		if (i < len&&Lex[i].getCode() == 43) // IDN
		{
			i++;
			if (i < len&&Lex[i].getCode() == 6) // =
			{
				i++;
				if (i < len&&(Expression()))//вираз
				{
					tester = true;
				}
				else
					JOptionPane.showMessageDialog(null, "Має бути або лв, або вираз, або con Error in line"+Lex[i].getLine()+" "+i+" "+Lex[i].getLexem() );
			} 
		}
		return tester;
	}

	public boolean Input()// введеня з клавіатури
	{
		boolean tester = false;
		if (i<len&&Lex[i].getCode() == 19) // scanf
		{
			i++;
			//if (i<len&&Lex[i].getCode() == 28) // (
			//{
				//i++;
				if (i<len&&Ind()) // інд
				{
					if (i<len&&Lex[i].getCode() == 28) // .
					{
						i++;
						if (i<len&&(Lex[i].getCode() == 43||Lex[i].getCode() == 44))
						{
							i++;
							/*if (i<len&&Lex[i].getCode() == 30)//)
							{
								tester = true;
								i++;
							}
							else
								JOptionPane.showMessageDialog(null,  "Expect ) after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine() );
						*/}
						else
							JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
						
					}
					else
						JOptionPane.showMessageDialog(null,  "Expect , after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine() );
				}
				else
					JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
			//}
			//else
				//JOptionPane.showMessageDialog(null,  "Expect( after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine() );
		}
		return tester;
	}

	public boolean Output()// виведення на екран
	{
		boolean tester = false;
		if (i<len&&Lex[i].getCode() == 18) // printf
		{
			i++;
			/*if (i<len&&Lex[i].getCode() == 28) // (
			{
				i++;*/
				if (i<len&&Ind()) // інд
				{
					if (i<len&&Lex[i].getCode() == 28) // .
					{
						i++;
						if (i<len&&(Lex[i].getCode() == 43||Lex[i].getCode() == 44))
						{
							i++;
							/*if (i<len&&Lex[i].getCode() == 30)//)
							{
								tester = true;
								i++;
							}
							else
								JOptionPane.showMessageDialog(null,  "Expect ) after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine() );
						*/}
						else
							JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
					}
					else
						JOptionPane.showMessageDialog(null,  "Expect , after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine() );
				}
				else
					JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
			/*}
			else
				JOptionPane.showMessageDialog(null,  "Expect( after"+Lex[i-1].getLexem()+" in line "+ Lex[i].getLine() );
		*/}
		return tester;
	}

	public boolean Ind()// інд
	{
		boolean tester = false;
		if (i < len&&(Lex[i].getCode() == 36 || Lex[i].getCode() == 37 || Lex[i].getCode() == 38)) // %i,%c,%b
		{
			tester = true;
			i++;
		}
		else
			JOptionPane.showMessageDialog(null, "не інд Error in line"+Lex[i].getLine()+" "+i+" "+Lex[i].getLexem() );
		return tester;
	}

	public boolean Cycle()// цикл
	{
		boolean tester = false;
		if (i<len&&Lex[i].getCode() == 20) // for
		{
			i++;
			if (i<len&&Lex[i].getCode() == 43) // id
			{
				i++;
				if (i<len&&Lex[i].getCode() == 6) // =
				{
					i++;
					if (i<len&&Expression()) // вираз
					{
						if (i<len&&Lex[i].getCode() == 21) // to
						{
							i++;
							if (i<len&&Expression()) // вираз
							{

								if (i<len&&Lex[i].getCode() == 22) // by
								{
									i++;
									if (i<len&&Expression()) // вираз
									{
										if (i<len&&Lex[i].getCode() == 23) // while
										{
											i++;
											/*if (i<len&&Lex[i].getCode() ==40) // [
											{
												
												i++;*/
												if (i<len&&BoolExpression()) // лог вираз
												{
													/*if (i<len&&Lex[i].getCode() == 42) // ]
													{
														i++;*/
														if (i<len&&OperatorList()) // список операторів
														{	
															if (i<len&&Lex[i].getCode() == 24) // end
															{
																tester = true;
																i++;
															}
															else
																JOptionPane.showMessageDialog(null, "end у циклі відсутній Error in line"+Lex[i-1].getLine()+" "+Lex[i-1].getLexem() );
														}
														else
															JOptionPane.showMessageDialog(null, "Error in line"+Lex[i-1].getLine()+" after "+Lex[i-1].getLexem());
													/*}
													else
														JOptionPane.showMessageDialog(null, "Expect ) in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
												*/}
												else
													JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
											/*}
											else
												JOptionPane.showMessageDialog(null, "Expect ) in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
										*/}
										else
											JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
									}
									else
										JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
								
								}
								else
									JOptionPane.showMessageDialog(null, "Expect by in line"+Lex[i].getLine()+" after "+Lex[i-1].getLexem());
							}
							else
								JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
						}
						else
							JOptionPane.showMessageDialog(null, "Expect to in line"+Lex[i].getLine()+" after "+Lex[i-1].getLexem());
					}
					else
						JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
				}
				else
					JOptionPane.showMessageDialog(null, "Expect = in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
			}
			else
				JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
		}
		return tester;
	}

	public boolean Expression()// вираз
	{
		boolean tester = false;
		boolean testerTemp = true;
		boolean flagExit = false;
		if (i<len&&Lex[i].getCode() == 14)//-
		{
			i++;
			if(i<len&&Term())//терм
			{
				tester=true;
			}
		}
		else
			if(i<len&&Term())//терм
			{
				tester=true;
			}
		while(i<len&&(Lex[i].getCode()==13||Lex[i].getCode()==14)&&flagExit==false)//+||-
		{
			testerTemp=false;
			i++;
			if(i<len&&Term())//терм
			{
				testerTemp=true;
			}
			else
			{
				flagExit=true;
				//JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
			}
		}
		return tester;
	}

	public boolean Term()// терм
	{
		boolean tester = false;
		boolean testerTemp = true;
		boolean flagExit = false;
		if(i<len&&Mnoj())//множ
		{
			tester=true;
		}
		while(i<len&&(Lex[i].getCode()==15||Lex[i].getCode()==16)&&flagExit==false)//*||/
		{
			testerTemp=false;
			i++;
			if(i<len&&Mnoj())//множ
			{
				testerTemp=true;
			}
			else
			{
				flagExit=true;

			}
		}
		if(testerTemp==false&&tester==true)
			i--;
		return tester; 
	}

	public boolean Mnoj()// множ
	{
		boolean tester = false;
		boolean testerTemp = true;
		boolean flagExit = false;
		if(i<len&&Perv())//перв
		{
			tester=true;
		}
		while(i<len&&Lex[i].getCode()==17&&flagExit==false)//^
		{
			testerTemp=false;
			i++;
			if(i<len&&Perv())//перв
			{
				testerTemp=true;
			}
			else
			{
				flagExit=true;
				//JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
			}
		}
		if(testerTemp==false&&tester==true)
			i--;
		return tester;
	}

	public boolean Perv()// перв
	{
		boolean tester = false;
		if (i<len&&(Lex[i].getCode() == 43 || Lex[i].getCode() == 44))//IDN,CON
		{
			tester = true;
			i++;
		}
		else
			if(i<len&&Lex[i].getCode() == 29)//(
			{
				i++;
				if(i<len&&Expression())//вираз
				{
					if(i<len&&Lex[i].getCode() == 30)//)
					{
						tester=true;
						i++;
					}
					else
						JOptionPane.showMessageDialog(null, "Expect ) in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
				}
				else 
					JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
			}
			else
				JOptionPane.showMessageDialog(null, "Expect ( in line"+Lex[i].getLine()+" after "+Lex[i-1].getLexem());
		return tester;
	}

	public boolean BoolExpression()// логічний вираз
	{
		boolean tester = false;
		boolean testerTemp = false;
		boolean flagExit = false;

		if(i<len&&LT())//лт
		{
			tester=true;
			while(i<len&&Lex[i].getCode()==35&&flagExit==false)//||
			{
				testerTemp = false;
				i++;
				if(i<len&&LT())//лт
				{
					testerTemp=true;
				}
				else
				{
					flagExit=true;
					JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
				}
			}
		}
		
		return tester;
	}

	public boolean LT()// лт
	{
		boolean tester = false;
		boolean testerTemp = false;
		boolean flagExit = false;
		if(i<len&&LM())//лм
		{
			tester=true;
			while(i<len&&Lex[i].getCode()==33&&flagExit==false)//&&
			{
				testerTemp=false;
				i++;
				if(i<len&&LM())//лм
				{
					testerTemp=true;
				}
				else
				{
					flagExit=true;
					JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
				}
			}
		}
		else
			JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" "+i+" "+Lex[i].getLexem() );

		return tester;
	}

	public boolean LM()// лм
	{
		boolean tester = false;
		if (Lex[i].getCode() == 34) // !!
		{
			if (i < len&&Attitude()) {
				tester = true;
			}
			else

				if (i< Lex.length && Lex[i].getCode() == 29) {//(
					i++;
					if(BoolExpression())
					{
						if(i< Lex.length && Lex[i].getCode() == 30)//)
						{
							i++;
							tester=true;
						}
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
		}
		else
			if (Attitude()) // відн
			{
				tester = true;
			}
			else

				if (i< Lex.length && Lex[i].getCode() == 29) {//(
					i++;
					if(BoolExpression())
					{
						if(i< Lex.length && Lex[i].getCode() == 30)//)
						{
							i++;
							tester=true;
						}
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
		return tester;
	}

	public boolean Attitude()// відношення
	{
		boolean tester = true;
		if (Lex[i].getCode() == 39 || Lex[i].getCode() == 40) // true,false
		{
			tester = true;
			i++;
		}
		
		else 
			if (Expression()) {
				if (i < len&&Connotation()) {
						if (Expression())
							tester = true;
				}
		} 
			else
				JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
		return tester;
	}

	public boolean Connotation()// знак відношення
	{
		boolean tester = true;
		if (Lex[i].getCode() >= 7 && Lex[i].getCode() <= 12) // !=,>,<,<=,>=,==
		{
			tester = true;
			i++;
		}
		else
			JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
		return tester;
	}

	public boolean Сondition()// умова
	{
		boolean tester = true;
		if (i<len&&Lex[i].getCode() == 31) // if
		{
				i++;
				if (i<len&&Attitude()) {
						if (i<len&&Lex[i].getCode() == 32) // then
						{
							i++;
							if (Operator())
								tester = true;
							else
								JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
							
						}
						else
							JOptionPane.showMessageDialog(null, "Expect then in line"+Lex[i].getLine()+" after "+Lex[i-1].getLexem());
				}
				else
					JOptionPane.showMessageDialog(null, "Error in line"+Lex[i].getLine()+" after "+Lex[i].getLexem());
		}
		return tester;
	}

}
