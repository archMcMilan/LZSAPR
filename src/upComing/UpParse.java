package upComing;


import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import gram.ParseGram;
import la.Lexema;

public class UpParse {
	/*private LexAndGram[] gramLex;
	private Lexema[] output;*/
	private LinkedList<LexAndGram> stack;
	public ArrayList<String> attitude=new ArrayList<>();
	private LinkedList<Lexema> inputLex;
	
	public ArrayList<String> stackPrint=new ArrayList<>();
	public ArrayList<String> inputLexPrint=new ArrayList<>();
	private int shift=26;
	public UpParse() {
		stack=new LinkedList<>();
		inputLex=new LinkedList<>();
	}
	
	public void upParsed(LexAndGram[] gramLex,Lexema[] output, ParseGram parse){
		for(int i=0;i<output.length;i++){//all lexems after LA and SA засовываем все лексемы в список выходных лексем
			inputLex.add(output[i]);//Заношу весь код программы в список
		}
		
		String array="";//переменная, для вывода String в таблицу
		stack.add(gramLex[inputLex.poll().getCode()+shift]);//заносим в стек слово которое идет в голове списка выходных лексем(сейчас, это main)
		array=toStringG(stack);
		stackPrint.add(array);//В вывод заносим слово
		array=toStringL(inputLex);//весь оставшийся список входный лексем переводим в String 
		inputLexPrint.add(array);

		System.out.println(stackPrint.get(0));
		//System.out.println(attitude.get(0));
		System.out.println(inputLexPrint.get(0));
		int i = 0,j,iter = 0;
		String program="main ( <список_оголошень1> ) { <список_оп-ів1> } ";
		//for(int j=0;j<inputLex.size();j++){
		while(inputLex.size()>0){
			if(stack.get(stack.size()-1).less.contains(gramLex[inputLex.get(0).getCode()+shift].getLex().getLexCode())){//< проверка на знак меньше
				stack.add(gramLex[inputLex.poll().getCode()+shift]);//если меньше, то заносим слово в стек
				array=toStringG(stack);
				stackPrint.add(array);
				attitude.add("<");
				array=toStringL(inputLex);
				i++;
				
				inputLexPrint.add(array);
				System.out.println("<"+i);
				System.out.println(stackPrint.get(i));
				System.out.println(inputLexPrint.get(i));
			}else{
				if(stack.get(stack.size()-1).equal.contains(gramLex[inputLex.get(0).getCode()+shift].getLex().getLexCode())){//=
					stack.add(gramLex[inputLex.poll().getCode()+shift]);
					array=toStringG(stack);
					stackPrint.add(array);
					attitude.add("=");
					array=toStringL(inputLex);
					inputLexPrint.add(array);
					
					i++;
					System.out.println("="+i);
					System.out.println(stackPrint.get(i));
					System.out.println(inputLexPrint.get(i));
				}else{
					if(stack.get(stack.size()-1).more.contains(gramLex[inputLex.get(0).getCode()+shift].getLex().getLexCode())){//>
						int curIndex=stack.size()-1;
						String temp=stack.get(stack.size()-1).getLex().getLex();
						while(curIndex>0){
							System.out.println(stack.get(curIndex-1).equal.contains(stack.get(curIndex).getLex().getLexCode()));
							if(stack.get(curIndex-1).equal.contains(stack.get(curIndex).getLex().getLexCode())){
								temp=stack.get(curIndex-1).getLex().getLex()+" "+temp;
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
						stackPrint.add(array);
						attitude.add(">");
						array=toStringL(inputLex);
						inputLexPrint.add(array);
						
						i++;
						System.out.println(">"+i);
						System.out.println(stackPrint.get(i));
						System.out.println(inputLexPrint.get(i));
					}else{
						int curIndex=stack.size()-1;
						String temp=stack.get(stack.size()-1).getLex().getLex();
						while(curIndex>0){
							if(stack.get(curIndex-1).equal.contains(stack.get(curIndex).getLex().getLexCode())){
								temp=stack.get(curIndex-1).getLex().getLex()+" "+temp;
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
						stackPrint.add(array);
						attitude.add("!");
						array=toStringL(inputLex);
						inputLexPrint.add(array);
						
						i++;
						System.out.println("?"+i);
						System.out.println(stackPrint.get(i));
						System.out.println(inputLexPrint.get(i));
					}
				}
			}
			iter++;
			array=toStringG(stack);
			if(array.equals(program)){
				attitude.add(">");
				inputLexPrint.add("");
				stackPrint.add("<програма>");
				JOptionPane.showMessageDialog(null, "Successully!!!");
				attitude.add(">");
				break;
			}	
			if(attitude.get(attitude.size()-1).equals("!")){
				JOptionPane.showMessageDialog(null, "Error! In line"+inputLex.get(0).getLine());
				attitude.add("!");
				break;
			}
		}
	}
	public int findRule(String phrase, ParseGram parse){
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
		
		return parse.getParsedGram().length;
	}
	
	public String toStringG(LinkedList<LexAndGram> stack){//Преобразовывает из списка в String для вывода в талбицу
		String res="";
		for(int i=0;i<stack.size();i++){
			res+=stack.get(i).getLex().getLex()+" ";
		}
		return res;
		
	}
	public String toStringL(LinkedList<Lexema> stack){//аналогично
		String res="";
		for(int i=0;i<stack.size();i++){
			res+=stack.get(i).getLexem()+" ";
		}
		return res;
		
	}
}
