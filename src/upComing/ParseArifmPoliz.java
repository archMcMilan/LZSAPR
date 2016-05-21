package upComing;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import gram.ParseGram;
import la.Lexema;
import upComing.LexAndGram;

public class ParseArifmPoliz {
	/*private LexAndGram[] gramLex;
	private Lexema[] output;*/
	private LinkedList<LexAndGram> stack;
	public ArrayList<String> attitude=new ArrayList<>();
	private LinkedList<Lexema> inputLex;
	
	public ArrayList<String> stackPrint=new ArrayList<>();
	public ArrayList<String> inputLexPrint=new ArrayList<>();
	public ArrayList<String> polizPrint=new ArrayList<>();
	private int shift=26;
	public ParseArifmPoliz() {
		stack=new LinkedList<>();
		inputLex=new LinkedList<>();
	}
	
	public void parsePolizArifm(LexAndGram[] gramLex,Lexema[] output, ParseGram parse){
		for(int i=0;i<output.length;i++){//all lexems after LA and SA засовываем все лексемы в список выходных лексем
			inputLex.add(output[i]);//Заношу весь код программы в список
		}
		
		String array="";//переменная, для вывода String в таблицу
		String tempPoliz="";
		/*первые значения*/
		stackPrint.add(array);
		polizPrint.add(tempPoliz);
		attitude.add("");//отношение
		
		
		if(inputLex.get(0).getCode()==43 || inputLex.get(0).getCode()==44){
			tempPoliz+=inputLex.get(0).getLexem()+" ";
		}else{
			if(inputLex.get(0).getCode()==14){
				inputLex.addFirst(new Lexema(0,"0",44,0));
				tempPoliz+="0 ";
			}else{
				JOptionPane.showMessageDialog(null, "Error! Cant start with operator - '"+inputLex.get(0).getLine()+"'");
			}
		}
		
		array=toStringL(inputLex);//весь оставшийся список входный лексем переводим в String 
		inputLexPrint.add(array);
		stack.add(gramLex[inputLex.poll().getCode()+shift]);//заносим в стек слово которое идет в голове списка выходных лексем(сейчас, это main)
		array=toStringG(stack);
		polizPrint.add(tempPoliz);
		
		stackPrint.add(array);//В вывод заносим слово
		array=toStringL(inputLex);//весь оставшийся список входный лексем переводим в String 
		inputLexPrint.add(array);
		
		System.out.println(stackPrint.get(0));
		//System.out.println(attitude.get(0));
		System.out.println(inputLexPrint.get(0));
		int i = 0,j;
		String axiom="<вираз>";
		//for(int j=0;j<inputLex.size();j++){
		while(inputLex.size()>0){
			if(stack.get(stack.size()-1).less.contains(gramLex[inputLex.get(0).getCode()+shift].getLex().getLexCode())){//< проверка на знак меньше
				if(inputLex.get(0).getCode()==43 || inputLex.get(0).getCode()==44){
					tempPoliz+=inputLex.get(0).getLexem()+" ";
				}
				stack.add(gramLex[inputLex.poll().getCode()+shift]);//если меньше, то заносим слово в стек				
				polizPrint.add(tempPoliz);//добавляем в столбик полиза
				array=toStringG(stack);//преобразовываем в String
				stackPrint.add(array);//заносим в таблицу стек
				attitude.add("<");//отношение
				array=toStringL(inputLex);//входное сообщение переганям в String
				i++;
				inputLexPrint.add(array);//добавляем в балицу вх сообщение
				
				System.out.println("Stack = "+stackPrint);
				System.out.println("InputLEx="+inputLexPrint);
				/*Проверочные выводы в консоль*/
			}else{
				if(stack.get(stack.size()-1).equal.contains(gramLex[inputLex.get(0).getCode()+shift].getLex().getLexCode())){//=
					if(inputLex.get(0).getCode()==43 || inputLex.get(0).getCode()==44){
						tempPoliz+=inputLex.get(0).getLexem()+" ";
					}
					stack.add(gramLex[inputLex.poll().getCode()+shift]);
					
					polizPrint.add(tempPoliz);//добавляем в столбик полиза
					array=toStringG(stack);
					stackPrint.add(array);
					attitude.add("=");
					array=toStringL(inputLex);
					inputLexPrint.add(array);
					i++;
					System.out.println("Stack = "+stackPrint);
					System.out.println("InputLEx="+inputLexPrint);
				}else{
					if(stack.get(stack.size()-1).more.contains(gramLex[inputLex.get(0).getCode()+shift].getLex().getLexCode())){//>
						int curIndex=stack.size()-1;
						String temp=stack.get(stack.size()-1).getLex().getLex();
						polizPrint.add(tempPoliz);
						/*Поиск основы*/
						while(curIndex>0){
							if(stack.get(curIndex-1).equal.contains(stack.get(curIndex).getLex().getLexCode())){
								temp=stack.get(curIndex-1).getLex().getLex()+" "+temp;
								/*если был знак в свертке, то вытягиваем его*/
								if((stack.get(curIndex-1).getLex().getLexCode()<=(17+shift) && stack.get(curIndex-1).getLex().getLexCode()>=(13+shift))){
									if(stack.get(curIndex-1).getLex().getLexCode()==14+shift){
										System.out.println("Element="+stack.get(curIndex-2).equal.contains(stack.get(curIndex-1).getLex().getLexCode()));
										if(!stack.get(curIndex-2).equal.contains(stack.get(curIndex-1).getLex().getLexCode())){
											tempPoliz+="@ ";
											polizPrint.remove(polizPrint.size()-1);
											polizPrint.add(tempPoliz);
										}else{
											tempPoliz+=stack.get(curIndex-1).getLex().getLex()+" ";
											polizPrint.remove(polizPrint.size()-1);
											polizPrint.add(tempPoliz);
										}
									}else{
										tempPoliz+=stack.get(curIndex-1).getLex().getLex()+" ";
										polizPrint.remove(polizPrint.size()-1);
										polizPrint.add(tempPoliz);
									}
									
								}
								stack.pollLast();
								curIndex--;
							}else{
								break;
							}
						}
						//////////////
						int indexOfRuleLex=findRule(temp,parse);//поиск правила
						stack.pollLast();
						stack.add(gramLex[indexOfRuleLex]);
						array=toStringG(stack);
						stackPrint.add(array);
						attitude.add(">");
						array=toStringL(inputLex);
						inputLexPrint.add(array);
						
						i++;
						System.out.println("Stack = "+stackPrint);
						System.out.println("InputLEx="+inputLexPrint);
					}
				}
			}
			array=toStringG(stack);
			if(attitude.get(attitude.size()-1).equals("!")){
				JOptionPane.showMessageDialog(null, "Error! In line"+inputLex.get(0).getLine());
				attitude.add("!");
				break;
			}
		}
		while(true){//сворачиваем до аксиомы
			polizPrint.add(tempPoliz);
			attitude.add(">");
			inputLexPrint.add("");
			int curIndex=stack.size()-1;
			String temp=stack.get(stack.size()-1).getLex().getLex();
			while(curIndex>0){
				if(stack.get(curIndex-1).equal.contains(stack.get(curIndex).getLex().getLexCode())){
					temp=stack.get(curIndex-1).getLex().getLex()+" "+temp;
					if((stack.get(curIndex-1).getLex().getLexCode()<=(17+shift) && stack.get(curIndex-1).getLex().getLexCode()>=(13+shift))){
						if(stack.get(curIndex-1).getLex().getLexCode()==14+shift){
							if(!stack.get(curIndex-2).equal.contains(stack.get(curIndex-1).getLex().getLexCode())){
								tempPoliz+="@ ";
								polizPrint.remove(polizPrint.size()-1);
								polizPrint.add(tempPoliz);
							}else{
								tempPoliz+=stack.get(curIndex-1).getLex().getLex()+" ";
								polizPrint.remove(polizPrint.size()-1);
								polizPrint.add(tempPoliz);
							}
						}else{
							tempPoliz+=stack.get(curIndex-1).getLex().getLex()+" ";
							polizPrint.remove(polizPrint.size()-1);
							polizPrint.add(tempPoliz);
						}
						System.out.println("Stack = "+stackPrint);
						System.out.println("InputLEx="+inputLexPrint);
						
					}
					stack.pollLast();
					curIndex--;
				}else{
					break;
				}
			}
			int indexOfRuleLex=findRule(temp,parse);
			stack.pollLast();
			stack.add(gramLex[indexOfRuleLex]);
			array=toStringG(stack);
			if(array.equals("<вираз1> ")){
				stackPrint.add(array);
				attitude.add(">");
				JOptionPane.showMessageDialog(null, "Successully!!!");
				break;
			}else{
				stackPrint.add(array);
				attitude.add(">");
				array=toStringL(inputLex);
				inputLexPrint.add(array);
			}
			
		}
		
	}
	private int findRule(String phrase, ParseGram parse){
		System.out.println("Phrase="+phrase);
		if(phrase=="<" || phrase==">"){
			return parse.getParsedGram().length-1;
		}
		for(int i=parse.getParsedGram().length-1; i>=0;i--){
			if(parse.getParsedGram()[i].contains("::="+phrase)){
				if(parse.getParsedGram()[i].contains("::="+phrase+" <")){
					continue;
				}
				//System.out.println(i);
				System.out.println("ParsedGram="+parse.getParsedGram()[i]);
				return i;
			}
		}
		for(int i=parse.getParsedGram().length-1; i>0;i--){
			if(parse.getParsedGram()[i].contains("/ "+phrase+"/")){
				System.out.println("ParsedGram /.../ "+parse.getParsedGram()[i]);
				return i;
			}
		}
		for(int i=parse.getParsedGram().length-1; i>0;i--){
			if(parse.getParsedGram()[i].contains(phrase+"/")){
				System.out.println("ParsedGram .../"+parse.getParsedGram()[i]);
				return i;
			}
		}
		for(int i=parse.getParsedGram().length-1; i>0;i--){	
			if(parse.getParsedGram()[i].contains("/ "+phrase)){
				System.out.println("ParsedGram /..."+parse.getParsedGram()[i]);
				return i;
			}	
		}
		if(phrase.equals("<вираз> - <терм1>")){
			System.out.println("Return 12");
			return 12;
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parse.getParsedGram().length;
	}
	
	private String toStringG(LinkedList<LexAndGram> stack){//Преобразовывает из списка в String для вывода в талбицу
		String res="";
		for(int i=0;i<stack.size();i++){
			res+=stack.get(i).getLex().getLex()+" ";
		}
		return res;
		
	}
	private String toStringL(LinkedList<Lexema> stack){//аналогично
		String res="";
		for(int i=0;i<stack.size();i++){
			res+=stack.get(i).getLexem()+" ";
		}
		return res;
		
	}
	
	public double make(){
		String elementInLine="";
		elementInLine=polizPrint.get(polizPrint.size()-1);
		String[] element=elementInLine.split(" ");
		LinkedList<Integer> number=new LinkedList<>();
		String operands="@-+*$^";
		for(String el:element){
			System.out.println(el);
			if(!operands.contains(el)){
				number.add(Integer.parseInt(el));
			}else{
				if(el.equals("@")){
					number.set(number.size()-1, number.get(number.size()-1)*(-1));
				}else{
					int right=number.pollLast();
					int left=number.pollLast();
					if(el.equals("-")){
						number.add(left-right);
					}
					if(el.equals("+")){
						number.add(left+right);
					}
					if(el.equals("*")){
						number.add(left*right);
					}
					if(el.equals("$")){
						number.add(left/right);
					}
					if(el.equals("^")){
						number.add((int) Math.pow(left,right));
					}
				}
			}
		}
		System.out.println(number.get(0));
		return number.get(0);
		
	}
}
