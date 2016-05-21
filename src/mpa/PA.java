package mpa;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import la.Lexema;
//подавтомат 
public class PA {
	private Lexema[] Lex;
	private ArrayList <AMB> avt=new ArrayList<AMB>();
	private ArrayList <Integer> stack=new ArrayList<Integer>();
	private int k=0;
	public ArrayList <Integer> OutA=new ArrayList<Integer>();
	public ArrayList <String> OutMitka=new ArrayList<String>();
	public ArrayList <Integer> OutB=new ArrayList<Integer>();
	public ArrayList <String> OutStack=new ArrayList<String>();
		
	public ArrayList<AMB> getAvt() {
		return avt;
	}
	
	public Lexema[] getLex() {
		return Lex;
	}

	public ArrayList<Integer> getStack() {
		return stack;
	}


	public int getK() {
		return k;
	}
//правила переходов
	public PA(Lexema[] lex) {
		Lex=lex;
		
		avt.add(new AMB(1,0,2,0,"Error"));
		avt.add(new AMB(2,29,3,0,"Error"));
		avt.add(new AMB(3,3,4,0,"Error"));
		avt.add(new AMB(3,4,4,0,"Error"));
		avt.add(new AMB(3,5,4,0,"Error"));
		avt.add(new AMB(3,30,6,0,"Error"));
		avt.add(new AMB(4,43,5,0,"Error"));
		avt.add(new AMB(5,26,3,0,"Error"));
		avt.add(new AMB(5,25,4,0,"Error"));
		avt.add(new AMB(6,1,21,7,"Error"));
		avt.add(new AMB(7,26,8,0,"Error"));
		avt.add(new AMB(8,2,0,0,"21 7"));
		
		avt.add(new AMB(21,43,22,0,"Error"));
		avt.add(new AMB(21,18,24,0,"Error"));
		avt.add(new AMB(21,19,24,0,"Error"));
		avt.add(new AMB(21,20,28,0,"Error"));
		avt.add(new AMB(21,31,36,0,"Error"));
		avt.add(new AMB(22,6,23,0,"Error"));
		avt.add(new AMB(23,44,0,0,"41 20"));
		//avt.add(new AMB(24,29,25,0,"Error"));
		avt.add(new AMB(24,36,26,0,"Error"));
		avt.add(new AMB(24,37,26,0,"Error"));
		avt.add(new AMB(24,38,26,0,"Error"));
		avt.add(new AMB(26,28,27,0,"Error"));
		avt.add(new AMB(27,43,0,0,"Error"));
		avt.add(new AMB(27,44,0,0,"Error"));
		avt.add(new AMB(28,43,29,0,"Error"));
		avt.add(new AMB(29,6,41,30,"Error"));
		avt.add(new AMB(30,21,41,31,"Error"));
		avt.add(new AMB(31,22,41,32,"Error"));
		avt.add(new AMB(32,23,51,34,"Error"));
		//avt.add(new AMB(33,29,51,34,"Error"));
		avt.add(new AMB(34,1,21,35,"Error"));
		avt.add(new AMB(35,26,19,0,"Error"));
		avt.add(new AMB(36,39,38,0,"51 38"));
		avt.add(new AMB(36,40,38,0,"51 38"));
		//avt.add(new AMB(37,7,41,38,"Error"));
		//avt.add(new AMB(37,8,41,38,"Error"));
		//avt.add(new AMB(37,9,41,38,"Error"));
		//avt.add(new AMB(37,10,41,38,"Error"));
		//avt.add(new AMB(37,11,41,38,"Error"));
		//avt.add(new AMB(37,12,41,38,"Error"));
		avt.add(new AMB(38,32,21,0,"Error"));
		avt.add(new AMB(39,26,19,0,"Error"));
		avt.add(new AMB(20,-1,0,0,"Exit"));
		avt.add(new AMB(19,2,40,0,"21 39"));
		avt.add(new AMB(40,24,0,0,"Error"));
		
		avt.add(new AMB(41,14,42,0,"42"));
		avt.add(new AMB(42,43,43,0,"Error"));
		avt.add(new AMB(42,44,43,0,"Error"));
		avt.add(new AMB(42,29,41,44,"Error"));
		avt.add(new AMB(43,13,42,0,"Exit"));
		avt.add(new AMB(43,14,42,0,"Exit"));
		avt.add(new AMB(43,15,42,0,"Exit"));
		avt.add(new AMB(43,16,42,0,"Exit"));
		avt.add(new AMB(43,17,42,0,"Exit"));
		
		avt.add(new AMB(51,34,51,0,"41 52"));
		avt.add(new AMB(51,41,41,54,"41 52"));
		avt.add(new AMB(52,7,41,53,"Error"));
		avt.add(new AMB(52,8,41,53,"Error"));
		avt.add(new AMB(52,9,41,53,"Error"));
		avt.add(new AMB(52,10,41,53,"Error"));
		avt.add(new AMB(52,11,41,53,"Error"));
		avt.add(new AMB(52,12,41,53,"Error"));
		avt.add(new AMB(53,35,51,0,"Exit"));
		avt.add(new AMB(53,33,51,0,"Exit"));
		avt.add(new AMB(54,42,53,0,"Error"));
		//MPA();
	}


	public int  Move(int a, int Lex) {
		int B=-1;
		boolean flag=false;
		int tempI=0;
		for(int i=0;i<avt.size();i++)
		{
			if(a==avt.get(i).getA())
			{
				if(Lex==avt.get(i).getMitka())
				{
					k++;
					B=avt.get(i).getB();
					flag=true;
					if(avt.get(i).getStack()!=0)
						stack.add(avt.get(i).getStack());
					break;
				}
				tempI=i;
			}
		}
		if(flag==false)
		{
			int temp[];
			temp=parseSemNotEq(avt.get(tempI).getSemNotEq(),avt.get(tempI).getA());
			B=temp[0];
			if(temp[1]!=-1)
				stack.add(temp[1]);
				
		}
		if(B==0)
		{
			B=stack.get(stack.size()-1);
			//System.out.println("  "+stack.size()+" "+B);
			stack.remove(stack.size()-1);
		}
		if(B==-1)
		{
			JOptionPane.showMessageDialog(null, "Error in state " + avt.get(tempI).getA());
			//System.exit(0);
		}
		return B;
	}
	
	public int[] parseSemNotEq(String Sem, int state)
	{
		int ret[]=new int[2];
		if(Sem.equals("Error"))
		{
			JOptionPane.showMessageDialog(null, "Error in state " + state+" before "+ Lex[k].getLexem());
			System.exit(0);
		}
		else
			if(Sem.equals("Exit"))
			{
				ret[0]=stack.get(stack.size()-1);
				stack.remove(stack.size()-1);
				ret[1]=-1;
			}
			else
				if(Sem.length()==2)
				{
					ret[0]=Integer.parseInt(Sem);
					ret[1]=-1;
				}
				else
				{
					String[] temp=Sem.split(" ");
					ret[0]=Integer.parseInt(temp[0]);
					ret[1]=Integer.parseInt(temp[1]);
				}
		return ret;
	}

	

}
