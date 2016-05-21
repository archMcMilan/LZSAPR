package la;

import java.util.ArrayList;

public class Lexema
{
    private int line;
    private String lexem;
    private int code;
    private int idn_con;
    private ArrayList<Integer> label=new ArrayList<>();// для каждого экземпляра добавляем поле метка,
    // где будет хранится ее список меток
    private int labelAddPos=-1;//позиция добавления метки на лексему в стек

    public Lexema()
    {
        lexem="";
        line=-1;
        code=-1;
        idn_con=-1;
    }
    public Lexema(int l,String le,int c,int ic)
    {
        line=l;
        lexem=le;
        code=c;
        idn_con=ic;
    }
    /**
     * На лексему навешиваем метку
     * @param index
     */
    public void addLabel(int index) {
        label.add(index);
    }

    /**
     * На лексему навешиваем индекс, на котором навешивается метка
     * @param pos
     */
    public void addPos(int pos){
        labelAddPos=pos;
    }


    public ArrayList<Integer> getLabel() {
        return label;
    }

    /**
     * Возвращает подготовленный для записи список метод
     * @return String
     */
    public String getStringLabel() {
        String res="";
        for(int i=0;i<label.size()-1;i++){
            res+=label.get(i)+" ";
        }
        res+=label.get(label.size()-1);
        return res;
    }

    public boolean containsLabel(int label){
        if(this.label.contains(label)){
            return true;
        }else{
            return false;
        }
    }


    public int getLabelAddPos() {
        return labelAddPos;
    }
    public int getLine()
    {
        return line;
    }
    public String getLexem()
    {
        return lexem;
    }
    public int getCode()
    {
        return code;
    }
    public int getIDNCON()
    {
        return idn_con;
    }
    public void setLine(int l)
    {
        line=l;
    }
    public void setLexem(String le)
    {
        lexem=le;
    }
    public void setCode(int c)
    {
        code=c;
    }
    public void setIDNCON(int ic)
    {
        idn_con=ic;
    }
}