package gram;
import java.util.ArrayList;

//������� ����� ������� ��������� 
public class ParseRight{
	private String lexema;//���
	private int line;//����� �����
	private int alterLineNum;//����� ������������
	private int posInLine;//������� � ������������
	private int lexCode;//��� �������
	
	
	public ParseRight(String lexema, int line, int alterLineNum, int posInLine) {
		this.lexema = lexema;
		this.line = line;
		this.alterLineNum = alterLineNum;
		this.posInLine = posInLine;
		this.lexCode=0;
	}
	public String getLexema() {
		return lexema;
	}
	public int getLine() {
		return line;
	}
	public int getAlterLineNum() {
		return alterLineNum;
	}
	public int getPosInLine() {
		return posInLine;
	}
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public void setAlterLineNum(int alterLineNum) {
		this.alterLineNum = alterLineNum;
	}
	public void setPosInLine(int posInLine) {
		this.posInLine = posInLine;
	}
	public int getLexCode() {
		return lexCode;
	}
	public void setLexCode(int lexCode) {
		this.lexCode = lexCode;
	}

}
