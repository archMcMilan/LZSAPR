package gram;

import java.util.ArrayList;

//gram lexem
public class LexGram {//Лексема граматики 
    private String lex;
    private int lexCode;
    private ArrayList<LexGram> equals;
    private ArrayList<LexGram> firsts;//������ first
    private ArrayList<LexGram> firstsPlus;//������ first+
    private ArrayList<LexGram> lasts;//������ last
    private ArrayList<LexGram> lastsPlus;//������ last+
    public String[] reserveLex = {"main", "{", "}", "int", "char", "bool", "=", "!=", "<", ">", "<=", ">=", "==", "+",
            "-", "*", "$", "^", "printf", "scanf", "for", "to", "by", "while", "end", ",", ";", "\"", ".", "(", ")", "if",
            "then", "&&", "!!", "||", "%i", "%c", "%b", "true", "false", "[", "]", "id", "con", "lit"};

    public LexGram(String lex, int lexCode) {
        super();
        this.lex = lex;
        this.lexCode = lexCode;
        equals = new ArrayList<LexGram>();
        firsts = new ArrayList<LexGram>();
        lasts = new ArrayList<LexGram>();
        firstsPlus = new ArrayList<LexGram>();
        lastsPlus = new ArrayList<LexGram>();
    }

    public String getLex() {
        return lex;
    }

    public int getLexCode() {
        return lexCode;
    }

    public void addFirst(ParseRight elem) {
        LexGram temp = new LexGram(elem.getLexema(), elem.getLexCode());

        firsts.add(temp);
        firstsPlus.add(temp);
    }

    public void addLast(ParseRight elem) {
        LexGram temp = new LexGram(elem.getLexema(), elem.getLexCode());

        lasts.add(temp);
        lastsPlus.add(temp);
    }

    public void addEqual(ParseRight elem) {

        LexGram temp = new LexGram(elem.getLexema(), elem.getLexCode());

        equals.add(temp);
    }

    public LexGram getLexGram() {
        return this;
    }

    public ArrayList<LexGram> getFirsts() {
        return firsts;
    }

    public void setFirsts(ArrayList<LexGram> firsts) {
        this.firsts = firsts;
    }

    public ArrayList<LexGram> getLasts() {
        return lasts;
    }

    public void setLasts(ArrayList<LexGram> lasts) {
        this.lasts = lasts;
    }


    public ArrayList<LexGram> getEquals() {
        return equals;
    }

    public void setEquals(ArrayList<LexGram> equals) {
        this.equals = equals;
    }

    public ArrayList<LexGram> getFirstsPlus() {
        return firstsPlus;
    }

    public void setFirstsPlus(ArrayList<LexGram> firstsPlus) {
        this.firstsPlus = firstsPlus;
    }

    public ArrayList<LexGram> getLastsPlus() {
        return lastsPlus;
    }

    public void setLastsPlus(ArrayList<LexGram> lastsPlus) {
        this.lastsPlus = lastsPlus;
    }

    public void addFirstPlus(LexGram lex[], LexGram elem) {
        for (int i = 0; i < elem.getFirsts().size(); i++) {
            if (elem.getFirsts().get(i).getLexCode() < (lex.length - reserveLex.length)) {
                for (int j = 0; j < lex.length; j++) {
                    if (lex[j].getLexCode() == elem.getFirsts().get(i).getLexCode()) {
                        boolean flagAdd = false;
                        for (int k = 0; k < lex[j].getFirsts().size(); k++) {
                            boolean flag = true;
                            for (int p = 0; p < firstsPlus.size(); p++)
                                if (lex[j].getFirsts().get(k).getLexCode() == firstsPlus.get(p).getLexCode())
                                    flag = false;
                            if (flag == true) {
                                firstsPlus.add(lex[j].getFirsts().get(k));
                                flagAdd = true;
                            }
                        }
                        if (flagAdd == true)
                            addFirstPlus(lex, lex[j]);
                        break;
                    }

                }
            }
        }
        /*System.out.print(elem.getLex()+" ");
		for(int j=0;j<elem.getFirstsPlus().size();j++)
			System.out.print(elem.getFirstsPlus().get(j).getLex()+" ");
		System.out.println();
		*/
    }

    public void addLastPlus(LexGram lex[], LexGram elem) {
        for (int i = 0; i < elem.getLasts().size(); i++) {
            //firstsPlus.add(elem.getFirsts().get(i).getLexGram());
            if (elem.getLasts().get(i).getLexCode() < (lex.length - reserveLex.length)) {
                for (int j = 0; j < lex.length; j++) {
                    if (lex[j].getLexCode() == elem.getLasts().get(i).getLexCode()) {
                        boolean flagAdd = false;
                        for (int k = 0; k < lex[j].getLasts().size(); k++) {
                            boolean flag = true;
                            for (int p = 0; p < lastsPlus.size(); p++)
                                if (lex[j].getLasts().get(k).getLexCode() == lastsPlus.get(p).getLexCode())
                                    flag = false;
                            if (flag == true) {
                                lastsPlus.add(lex[j].getLasts().get(k));
                                flagAdd = true;
                            }
                        }
                        if (flagAdd == true)
                            addLastPlus(lex, lex[j]);
                        break;
                    }

                }
            }
        }
		/*System.out.print("Elem: "+elem.getLex()+" Last:");
		for(int j=0;j<elem.getLastsPlus().size();j++)
			System.out.print(elem.getLastsPlus().get(j).getLex()+" ");
		System.out.println();*/

    }

    public void printFirstPlus() {
        for (int i = 0; i < firstsPlus.size(); i++)
            System.out.print(firstsPlus.get(i).getLex() + " ");
        System.out.println();
    }


}
