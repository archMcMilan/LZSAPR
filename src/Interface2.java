
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import gram.ParseGram;
import gram.ReadFile;
import la.Lexema;
import upComing.LexAndGram;
import upComing.UpParse;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

public class Interface2 extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private String filePath = System.getProperty("user.dir")+"/Gram.txt";;
	private ParseGram parse;
	private JScrollPane scrollPane;
	private String[] reserveLex = { "main", "{", "}", "int", "char", "bool", "=", "!=", "<", ">", "<=", ">=", "==", "+",
			"-", "*", "$", "^", "printf", "scanf", "for", "to", "by", "while", "end", ",", ";","\"",".", "(", ")", "if",
			"then", "&&", "!!", "||", "%i", "%c", "%b", "true", "false","[","]","id", "con", "lit" };
	public LexAndGram[] upComingLex;
	private String[][] data; 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface2 frame = new Interface2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LexAndGram[] getUpComingLex() {
		return upComingLex;
	}
	
	public ParseGram getParse() {
		return parse;
	}
	/**
	 * Create the frame.
	 */
	public Interface2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		//scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		String grammarInLine = null;
		try{
			grammarInLine = ReadFile.read(filePath);
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null, "File not found!");
		}
		parse=new ParseGram(grammarInLine);
		
		print();
		upComingLex=new LexAndGram[parse.getLex().length];
		for(int i=0;i<parse.getLex().length;i++){
			upComingLex[i]=new LexAndGram(parse.getLex()[i], data[i]);
		}
		
		//table = new JTable();
		//scrollPane.setViewportView(table);
	}
	
	public String[][] print()
	{
		for(int i=0;i<parse.getLex().length;i++){
			System.out.println(i+" "+parse.getLex()[i].getLex());
		}
		
		String[] columnNames1= new String[parse.getLex().length+1];
		columnNames1[0]="#";
		for (int i=0; i<parse.getLex().length; i++) { 
			columnNames1[i+1]=parse.getLex()[i].getLex();
		}
		
		data = new String[parse.getLex().length][parse.getLex().length+1]; 
		for (int i=0; i<parse.getLex().length; i++)
		{
			for (int j=0; j<parse.getLex().length+1; j++)
				data[i][j] = "";
		}
		for (int i=0; i<parse.getLex().length; i++)
		{
			data[i][0] = parse.getLex()[i].getLex();
		}
		for(int i=0; i<parse.getLex().length; i++)
		{
			if(parse.getLex()[i].getEquals().size()!=0)
			{
				for(int j=0;j<parse.getLex()[i].getEquals().size();j++)
				{
					for(int k=0;k<parse.getLex().length;k++)
					{
						if(parse.getLex()[k].getLexCode()==parse.getLex()[i].getEquals().get(j).getLexCode())
						{
							if(data[i][k+1].equals("="))
							{
								data[i][k+1]+="";
							}
							else
								data[i][k+1]+="=";
						}
					}
				}	
			}
		}
		for(int i=0; i<parse.getLex().length; i++)
		{
			if(parse.getLex()[i].getEquals().size()!=0)
			{
				for(int j=0;j<parse.getLex()[i].getEquals().size();j++)
				{
					for(int k=0;k<parse.getLex().length;k++)
					{
						if(parse.getLex()[k].getLexCode()==parse.getLex()[i].getEquals().get(j).getLexCode())
						{
							for(int p=0;p<parse.getLex()[k].getFirstsPlus().size();p++)
							{

								if(data[i][parse.getLex()[k].getFirstsPlus().get(p).getLexCode()+1].equals("<"))
								{
									data[i][parse.getLex()[k].getFirstsPlus().get(p).getLexCode()+1]+="";
								}
								else
									data[i][parse.getLex()[k].getFirstsPlus().get(p).getLexCode()+1]+="<";
							}
							if(parse.getLex()[k].getLex().equals("+")){
								System.out.println("Find +");
							}
							for(int p=0;p<parse.getLex()[i].getLastsPlus().size();p++)
							{
								if(data[parse.getLex()[i].getLastsPlus().get(p).getLexCode()][i+1].equals(">"))
								{
									data[parse.getLex()[i].getLastsPlus().get(p).getLexCode()][i+1]+="";
								}
								else
									data[parse.getLex()[i].getLastsPlus().get(p).getLexCode()][i+1]+=">";
								
								if(data[parse.getLex()[i].getLastsPlus().get(p).getLexCode()][k+1].equals(">"))
								{
									data[parse.getLex()[i].getLastsPlus().get(p).getLexCode()][k+1]+="";
								}
								else
									data[parse.getLex()[i].getLastsPlus().get(p).getLexCode()][k+1]+=">";
								
								for(int t=0;t<parse.getLex()[k].getFirstsPlus().size();t++){
									if(parse.getLex()[k].getLex().equals("+")){
										System.out.println(parse.getLex()[k].getFirstsPlus().get(t).getLex());
									}
									if(data[parse.getLex()[i].getLastsPlus().get(p).getLexCode()][parse.getLex()[k].getFirstsPlus().get(t).getLexCode()+1].equals(">"))
									{
										data[parse.getLex()[i].getLastsPlus().get(p).getLexCode()][parse.getLex()[k].getFirstsPlus().get(t).getLexCode()+1]+="";
									}
									else
										data[parse.getLex()[i].getLastsPlus().get(p).getLexCode()][parse.getLex()[k].getFirstsPlus().get(t).getLexCode()+1]+=">";
								}
							}
						}
						
					}
				}	
			}
		}
		data[27][47]="<";
		data[52][47]="<";
		data[58][47]="<";
		data[11][57]="=";
		data[41][70]="<";
		data[41][16]="=";
		data[14][42]="=";
		data[46][70]="=";
		data[40][70]="<";
		table = new JTable(data,columnNames1);
		table.getColumnModel().getColumn(0).setMinWidth(30);
		
		table.setForeground(Color.BLACK);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		scrollPane.setViewportView(table);
		return data;
	}
	
	
}
