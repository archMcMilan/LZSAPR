import execute.Executor;
import gram.ParseGram;
import la.Const;
import la.Ident;
import la.LA;
import la.Lexema;
import mpa.MPA;
import mpa.PA;
import poliz.Dijkstra;
import sa.SA;
import upComing.ParseArifmPoliz;
import upComing.UpParse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Interface extends JFrame {

    private JPanel contentPane;
    private String mas = "";
    public Lexema[] output;
    private JTable table;
    private JTable table_4;
    private JTable table_5;
    private JPanel panel_1;
    private JTable table_1;
    private JTable table_2;
    private JTable table_3;
    private JTable table_6;
    private JScrollPane scrollPane_1;
    private JScrollPane scrollPane_2;
    private JScrollPane scrollPane_3;
    private JScrollPane scrollPane_4;
    private JScrollPane scrollPane_5;
    private JScrollPane scrollPane_6;
    private JScrollPane scrollPane_7;
    private JScrollPane scrollPane_8;
    private JScrollPane scrollPane_9;
    private JScrollPane scrollPane_10;
    private JScrollPane scrollPane_11;
    private JScrollPane scrollPane_12;
    private JTable table_7;
    private JTable table_8;
    private MPA T;
    private PA M;
    private int last = 1;
    private JTable table_9;
    private JTable table_10;
    private JTable table_11;
    private JTable table_12;
    private String pathCode = System.getProperty("user.dir") + "/Code.txt";
    private String pathCode2 = System.getProperty("user.dir") + "/Code2.txt";
    private String pathOpen = System.getProperty("user.dir");
    Scanner in = null;
    Interface2 frame2;
    ParseArifmPoliz up = new ParseArifmPoliz();
    private JEditorPane editorPane_1;
    private Dijkstra dstra;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interface frame = new Interface();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * Create the frame.
     */
    public Interface() {
        setForeground(Color.ORANGE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 640);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 11, 1164, 580);
        contentPane.add(tabbedPane);

        JPanel panel = new JPanel();
        tabbedPane.addTab("Код", null, panel, null);
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.setLayout(null);

        JButton btnLa = new JButton("LA");
        btnLa.setBounds(498, 97, 155, 75);
        btnLa.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(btnLa);

        JButton btnSa = new JButton("SA");
        btnSa.setBounds(663, 97, 155, 75);
        btnSa.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(btnSa);

        JButton btnLoad = new JButton("Load");
        btnLoad.setBounds(662, 11, 155, 75);
        btnLoad.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(btnLoad);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(827, 11, 155, 75);
        btnSave.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(btnSave);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(23, 11, 465, 499);
        panel.add(scrollPane);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setFont(new Font("Courier New", Font.PLAIN, 17));
        scrollPane.setViewportView(editorPane);

        JButton btnMPA = new JButton("MPA\r\n");
        btnMPA.setBounds(828, 97, 155, 75);
        btnMPA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                T = new MPA(output);
                printMain();
                printExp();
                printBool();
                printOp();
                M = new PA(output);
                MPA();
                print();
            }
        });
        btnMPA.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(btnMPA);

        JButton button = new JButton("<=>");
        button.setBounds(498, 183, 155, 75);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    frame2 = new Interface2();
                    frame2.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(button);

        JButton btnOpen = new JButton("Open");
        btnOpen.setFont(new Font("Arial", Font.PLAIN, 20));
        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //mas="";
                File file = null;
                JFileChooser fileopen = new JFileChooser(System.getProperty("user.dir"));
                int ret = fileopen.showDialog(null, "OK");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file = fileopen.getSelectedFile();
                }
                try {
                    in = new Scanner(file);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                ArrayList<String> list = new ArrayList<String>();
                while (in.hasNext()) {
                    list.add(in.nextLine());
                }

                for (int i = 0; i < list.size(); i++) {
                    mas += (String) list.get(i);
                    mas += "\n";
                }
                editorPane.setText(mas);
            }
        });
        btnOpen.setBounds(497, 11, 155, 75);
        panel.add(btnOpen);

        JButton btnUp = new JButton("UP");
        btnUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpParse up = new UpParse();
                ParseGram parse = frame2.getParse();
                up.upParsed(frame2.getUpComingLex(), output, parse);
                upComingPrint(up);
            }
        });
        btnUp.setFont(new Font("Arial", Font.PLAIN, 20));
        btnUp.setBounds(663, 183, 155, 75);
        panel.add(btnUp);

        JButton btnMakeAll = new JButton("Dijkstra");
        btnMakeAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dstra = new Dijkstra(output);
                dstra.algorithm();
                dijkstraPoliz(dstra);
                
            }
        });
        btnMakeAll.setFont(new Font("Arial", Font.PLAIN, 20));
        btnMakeAll.setBounds(992, 11, 155, 115);
        panel.add(btnMakeAll);

        JButton btnPoliz = new JButton("POLIZ");
        btnPoliz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ParseGram parse = frame2.getParse();
                up.parsePolizArifm(frame2.getUpComingLex(), output, parse);
                arifmPoliz(up);
            }
        });
        btnPoliz.setFont(new Font("Arial", Font.PLAIN, 20));
        btnPoliz.setBounds(827, 183, 155, 75);
        panel.add(btnPoliz);
        
        JPanel panel_7 = new JPanel();
        panel_7.setBounds(498, 269, 651, 241);
        panel.add(panel_7);
        panel_7.setLayout(new BorderLayout(0, 0));
        
        JScrollPane scrollPane_13 = new JScrollPane();
        panel_7.add(scrollPane_13, BorderLayout.CENTER);
        
        editorPane_1 = new JEditorPane();
        editorPane_1.setFont(new Font("Courier New", Font.PLAIN, 17));
        scrollPane_13.setViewportView(editorPane_1);
        
        JButton execute = new JButton("Execute");
        execute.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Executor executor = new Executor(dstra.getPoliz());
                executor.execute();
                editorPane_1.setText(executor.output());
        	}
        });
        execute.setFont(new Font("Arial", Font.PLAIN, 20));
        execute.setBounds(993, 143, 155, 115);
        panel.add(execute);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String temp = editorPane.getText();
                try (FileWriter writer = new FileWriter(pathCode2, false)) {
                    writer.write(temp);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        });
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    in = new Scanner(new File(pathCode));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                ArrayList<String> list = new ArrayList<String>();
                while (in.hasNext()) {
                    list.add(in.nextLine());
                }

                for (int i = 0; i < list.size(); i++) {
                    mas += (String) list.get(i);
                    mas += "\n";
                }
                editorPane.setText(mas);

            }
        });
        btnSa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SA SAn = new SA(output);
                SAn.Analyzer();
            }
        });
        btnLa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Scanner in2 = null;
                try {
                    in2 = new Scanner(new File(pathCode2));
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                ArrayList<String> list = new ArrayList<String>();
                while (in2.hasNext())
                    list.add(in2.nextLine());
                LA An = new LA();
                output = An.Osn(list);
                printTableWords(An);
                printTableLex(An.getLexSize(), An.getLexem());
                printTableIDN(An.getIDNSize(), An.getIdent());
                printTableCON(An.getCONSize(), An.getConst());
            }
        });

        panel_1 = new JPanel();
        tabbedPane.addTab("Лексический", null, panel_1, null);
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 20));

        panel_1.setLayout(null);

        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(0, 0, 263, 324);
        panel_1.add(scrollPane_1);


        scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(273, 0, 588, 324);
        panel_1.add(scrollPane_2);


        scrollPane_3 = new JScrollPane();
        scrollPane_3.setBounds(0, 335, 420, 165);
        panel_1.add(scrollPane_3);

        table_3 = new JTable();
        table_3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        table_3.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane_3.setViewportView(table_3);

        scrollPane_4 = new JScrollPane();
        scrollPane_4.setBounds(441, 335, 420, 165);
        panel_1.add(scrollPane_4);

        table_6 = new JTable();
        table_6.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        table_6.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane_4.setViewportView(table_6);

        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Т-ця переходів МПА", null, panel_2, null);
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 20));
        panel_2.setLayout(null);

        JLabel label = new JLabel("Головний автомат");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setBounds(144, 3, 149, 22);
        panel_2.add(label);

        JLabel label_1 = new JLabel("Оператор");
        label_1.setFont(new Font("Arial", Font.PLAIN, 16));
        label_1.setBounds(638, 3, 149, 22);
        panel_2.add(label_1);

        JLabel label_2 = new JLabel("Вираз");
        label_2.setFont(new Font("Arial", Font.PLAIN, 16));
        label_2.setBounds(182, 258, 74, 22);
        panel_2.add(label_2);

        JLabel label_3 = new JLabel("Лог вираз");
        label_3.setFont(new Font("Arial", Font.PLAIN, 16));
        label_3.setBounds(638, 258, 84, 22);
        panel_2.add(label_3);

        scrollPane_5 = new JScrollPane();
        scrollPane_5.setBounds(10, 28, 424, 227);
        panel_2.add(scrollPane_5);

        scrollPane_6 = new JScrollPane();
        scrollPane_6.setBounds(444, 28, 417, 227);
        panel_2.add(scrollPane_6);


        scrollPane_7 = new JScrollPane();
        scrollPane_7.setBounds(10, 281, 424, 230);
        panel_2.add(scrollPane_7);

        scrollPane_8 = new JScrollPane();
        scrollPane_8.setBounds(444, 281, 417, 230);
        panel_2.add(scrollPane_8);

        JPanel panel_3 = new JPanel();
        tabbedPane.addTab("МПА", null, panel_3, null);
        panel_3.setLayout(null);

        scrollPane_9 = new JScrollPane();
        scrollPane_9.setBounds(0, 0, 1159, 480);
        panel_3.add(scrollPane_9);

        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                last++;
                M = new PA(output);
                MPA();
                print();
            }
        });
        btnNext.setBounds(422, 491, 102, 31);
        panel_3.add(btnNext);

        JButton btnNewButton_1 = new JButton("All");
        btnNewButton_1.addActionListener(e -> {
                    M = new PA(output);
                    last = M.getLex().length - 1;
                    MPA();
                    print();

                }
        );
        btnNewButton_1.setBounds(310, 491, 102, 31);
        panel_3.add(btnNewButton_1);

        JPanel panel_4 = new JPanel();
        tabbedPane.addTab("Висхідний розбір", null, panel_4, null);
        panel_4.setLayout(null);

        scrollPane_10 = new JScrollPane();
        scrollPane_10.setBounds(0, 0, 1159, 527);
        panel_4.add(scrollPane_10);

        JPanel panel_5 = new JPanel();
        tabbedPane.addTab("Арифм вир", null, panel_5, null);
        panel_5.setLayout(new BorderLayout(0, 0));

        scrollPane_11 = new JScrollPane();
        panel_5.add(scrollPane_11);

        JButton btnMake = new JButton("Make");
        btnMake.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                double count = up.make();
                JOptionPane.showMessageDialog(null, "Answer=" + count);

            }
        });
        panel_5.add(btnMake, BorderLayout.SOUTH);

        JPanel panel_6 = new JPanel();
        tabbedPane.addTab("Дікстри алгоритм", null, panel_6, null);
        panel_6.setLayout(new BorderLayout(0, 0));
        scrollPane_12 = new JScrollPane(table_12, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel_6.add(scrollPane_12);


    }

    public void printTableWords(LA An)//Таблица зарезервированых слов
    {
        //вывод выходной таблицы лексем
        String[] columnNames1 = {
                "Code",
                "Lex",
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[An.TABLE.length][2];
        for (int i = 0; i < An.TABLE.length; i++) {
            for (int j = 0; j < 2; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = Integer.toString(i);
                        break;
                    case 1:
                        data[i][j] = An.TABLE[i];
                        break;
                }
            }
        }

        //вывод таблички Зар-ых слов
        table_1 = new JTable(data, columnNames1);
        table_1.setFont(new Font("Arial", Font.PLAIN, 14));
        table_1.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        table_1.setBounds(0, 0, 260, 324);
        scrollPane_1.setViewportView(table_1);
    }

    public void printTableLex(int leng, Lexema[] A1)//вывод выходной таблицы лексем
    {
        //объявление чтоесть табличка
        String[] columnNames1 = {
                "Line #",
                "Lex",
                "Code",
                "IDN/CON",
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[leng][4];
        for (int i = 0; i < leng; i++) {
            for (int j = 0; j < 4; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = Integer.toString(A1[i].getLine());
                        break;
                    case 1:
                        data[i][j] = A1[i].getLexem();
                        break;
                    case 2:
                        data[i][j] = Integer.toString(A1[i].getCode());
                        break;
                    case 3:
                        if (A1[i].getIDNCON() == -1)
                            data[i][j] = "";
                        else
                            data[i][j] = Integer.toString(A1[i].getIDNCON());
                        break;
                }
            }
        }

        table_2 = new JTable(data, columnNames1);
        table_2.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        table_2.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane_2.setViewportView(table_2);
    }

    public void printTableIDN(int leng, Ident[] A1) {
        String[] columnNames1 = {
                "#",
                "IDN",
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[leng][2];
        for (int i = 0; i < leng; i++) {
            for (int j = 0; j < 2; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = Integer.toString(A1[i].getNum());
                        break;
                    case 1:
                        data[i][j] = A1[i].getIdn();
                        break;
                }
            }
        }

        //вывод таблички Зар-ых слов
        table_3 = new JTable(data, columnNames1);
        table_3.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        table_3.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane_3.setViewportView(table_3);
    }

    public void printTableCON(int leng, Const[] A1)//Таблица зарезервированых слов
    {

        String[] columnNames1 = {
                "#",
                "CON",
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[leng][2];
        for (int i = 0; i < leng; i++) {
            for (int j = 0; j < 2; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = Integer.toString(A1[i].getNum());
                        break;
                    case 1:
                        data[i][j] = A1[i].getCons();
                        break;
                }
            }
        }

        //вывод таблички Зар-ых слов
        table_6 = new JTable(data, columnNames1);
        table_6.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        table_6.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane_4.setViewportView(table_6);
    }

    public void printMain()//Таблица зарезервированых слов
    {

        String[] columnNames1 = {
                "α",
                "Мітка",
                "β",
                "Стек",
                "Сем п/п"
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[T.AMain.length][5];
        for (int i = 0; i < T.AMain.length; i++) {
            for (int j = 0; j < 5; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = T.AMain[i];
                        break;
                    case 1:
                        data[i][j] = T.MitkaMain[i];
                        break;
                    case 2:
                        data[i][j] = T.BMain[i];
                        break;
                    case 3:
                        data[i][j] = T.StekMain[i];
                        break;
                    case 4:
                        data[i][j] = T.SemMain[i];
                        break;
                }
            }
        }
        //вывод таблички Зар-ых слов
        table_4 = new JTable(data, columnNames1);

        table_4.setForeground(Color.BLACK);
        table_4.getColumnModel().getColumn(4).setPreferredWidth(200);
        table_4.setFont(new Font("Arial", Font.PLAIN, 14));
        table_4.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        scrollPane_5.setViewportView(table_4);
    }

    public void printExp()//Таблица зарезервированых слов
    {

        String[] columnNames1 = {
                "α",
                "Мітка",
                "β",
                "Стек",
                "Сем п/п"
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[T.AExp.length][5];
        for (int i = 0; i < T.AExp.length; i++) {
            for (int j = 0; j < 5; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = T.AExp[i];
                        break;
                    case 1:
                        data[i][j] = T.MitkaExp[i];
                        break;
                    case 2:
                        data[i][j] = T.BExp[i];
                        break;
                    case 3:
                        data[i][j] = T.StekExp[i];
                        break;
                    case 4:
                        data[i][j] = T.SemExp[i];
                        break;
                }
            }
        }
        //вывод таблички Зар-ых слов
        table_5 = new JTable(data, columnNames1);

        table_5.setForeground(Color.BLACK);
        table_5.getColumnModel().getColumn(4).setPreferredWidth(200);
        table_5.setFont(new Font("Arial", Font.PLAIN, 14));
        table_5.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        scrollPane_7.setViewportView(table_5);
    }

    public void printBool()//Таблица зарезервированых слов
    {

        String[] columnNames1 = {
                "α",
                "Мітка",
                "β",
                "Стек",
                "Сем п/п"
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[T.ABool.length][5];
        for (int i = 0; i < T.ABool.length; i++) {
            for (int j = 0; j < 5; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = T.ABool[i];
                        break;
                    case 1:
                        data[i][j] = T.MitkaBool[i];
                        break;
                    case 2:
                        data[i][j] = T.BBool[i];
                        break;
                    case 3:
                        data[i][j] = T.StekBool[i];
                        break;
                    case 4:
                        data[i][j] = T.SemBool[i];
                        break;
                }
            }
        }
        //вывод таблички Зар-ых слов
        table_8 = new JTable(data, columnNames1);

        table_8.setForeground(Color.BLACK);
        table_8.getColumnModel().getColumn(4).setPreferredWidth(200);
        table_8.setFont(new Font("Arial", Font.PLAIN, 14));
        table_8.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        scrollPane_8.setViewportView(table_8);
    }

    public void printOp()//Таблица зарезервированых слов
    {

        String[] columnNames1 = {
                "α",
                "Мітка",
                "β",
                "Стек",
                "Сем п/п"
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[T.AOp.length][5];
        for (int i = 0; i < T.AOp.length; i++) {
            for (int j = 0; j < 5; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = T.AOp[i];
                        break;
                    case 1:
                        data[i][j] = T.MitkaOp[i];
                        break;
                    case 2:
                        data[i][j] = T.BOp[i];
                        break;
                    case 3:
                        data[i][j] = T.StekOp[i];
                        break;
                    case 4:
                        data[i][j] = T.SemOp[i];
                        break;
                }
            }
        }
        //вывод таблички Зар-ых слов
        table_7 = new JTable(data, columnNames1);

        table_7.setForeground(Color.BLACK);
        table_7.getColumnModel().getColumn(4).setPreferredWidth(200);
        table_7.setFont(new Font("Arial", Font.PLAIN, 14));
        table_7.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        scrollPane_6.setViewportView(table_7);
    }

    public void print()//Вивід
    {

        String[] columnNames1 = {
                "α",
                "Вхідна лексема",
                "β",
                "Стек"
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[M.OutA.size()][4];
        for (int i = 0; i < M.OutA.size(); i++) {
            for (int j = 0; j < 4; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = Integer.toString(M.OutA.get(i));
                        break;
                    case 1:
                        data[i][j] = M.OutMitka.get(i);
                        break;
                    case 2:
                        data[i][j] = Integer.toString(M.OutB.get(i));
                        break;
                    case 3:
                        data[i][j] = M.OutStack.get(i);
                        break;
                }
            }
        }
        //вывод таблички Зар-ых слов
        table_9 = new JTable(data, columnNames1);
        table_9.setForeground(Color.BLACK);
        table_9.setFont(new Font("Arial", Font.PLAIN, 14));
        table_9.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        scrollPane_9.setViewportView(table_9);
    }

    public void MPA() {
        M.OutA.add(M.getAvt().get(0).getA());
        M.OutMitka.add(M.getLex()[M.getK()].getLexem());
        int first = M.Move(M.getAvt().get(0).getA(), M.getLex()[0].getCode());
        M.OutB.add(first);
        M.OutStack.add("");
        while (M.getK() < last) {
            M.OutA.add(first);
            M.OutMitka.add(M.getLex()[M.getK()].getLexem());
            first = M.Move(first, M.getLex()[M.getK()].getCode());
            M.OutB.add(first);
            String temp = "";
            for (int i = 0; i < M.getStack().size(); i++) {
                temp += M.getStack().get(i);
                temp += " ";
            }
            M.OutStack.add(temp);

        }
        if (last == M.getLex().length - 1 && first != 8) {
            JOptionPane.showMessageDialog(null, "Error!!!");
        }
        if (first == 8) {
            M.OutA.add(first);
            M.OutMitka.add(M.getLex()[M.getK()].getLexem());
            M.OutB.add(0);
            M.OutStack.add("");
            if (M.getK() == M.getLex().length - 1) {
                JOptionPane.showMessageDialog(null, "Successully!!!");
            }
        }

    }

    public void upComingPrint(UpParse up) {
        String[] columnNames1 = {
                "#",
                "Стек",
                "Відношення",
                "Вхідна лексема"
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[up.stackPrint.size()][4];
        //System.out.println(up.stackPrint.size());
        for (int i = 0; i < up.stackPrint.size(); i++) {
            for (int j = 0; j < 4; j++) {

                switch (j) {
                    case 0:
                        data[i][j] = i + "";
                        break;
                    case 1:
                        data[i][j] = up.stackPrint.get(i);
                        break;
                    case 2:
                        data[i][j] = up.attitude.get(i);
                        break;
                    case 3:
                        data[i][j] = up.inputLexPrint.get(i);
                        break;
                }
            }
        }
        //вывод таблички Зар-ых слов
        table_10 = new JTable(data, columnNames1);
        table_10.setForeground(Color.BLACK);
        table_10.setFont(new Font("Arial", Font.PLAIN, 14));
        table_10.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        scrollPane_10.setViewportView(table_10);
    }

    public void arifmPoliz(ParseArifmPoliz up) {
        String[] columnNames1 = {
                "#",
                "Стек",
                "Відношення",
                "Вхідна лексема",
                "Поліз"
        };
        //табличка Зарезервированых слов- закидывание значениями
        String[][] data = new String[up.stackPrint.size()][5];
        //System.out.println(up.stackPrint.size());
        for (int i = 0; i < up.stackPrint.size(); i++) {
            for (int j = 0; j < 5; j++) {
                switch (j) {
                    case 0:
                        data[i][j] = i + "";
                        break;
                    case 1:
                        data[i][j] = up.stackPrint.get(i);
                        break;
                    case 2:
                        data[i][j] = up.attitude.get(i);
                        break;
                    case 3:
                        data[i][j] = up.inputLexPrint.get(i);
                        break;
                    case 4:
                        data[i][j] = up.polizPrint.get(i);
                        break;
                }
            }
        }
        //вывод таблички Зар-ых слов
        table_11 = new JTable(data, columnNames1);
        table_11.setForeground(Color.BLACK);
        table_11.setFont(new Font("Arial", Font.PLAIN, 14));
        table_11.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        scrollPane_11.setViewportView(table_11);
    }

    public void dijkstraPoliz(Dijkstra dijkstra) {
        String[] columnNames1 = {
                "Вхідна лексема",
                "Стек",
                "ЗПЦ",
                "ОЦ",
                "Поліз"
        };
        //табличка алгоритм Дейкстры слов- закидывание значениями
        String[][] data = new String[dijkstra.getSize()][5];
        String temp = "";
        String polizString = "";
        for (int i = 0; i < dijkstra.getSize(); i++) {
            for (int j = 0; j < 5; j++) {
                switch (j) {
                    case 0:
                        //записиваем коллонку входной лексемы
                        data[i][j] = dijkstra.getInputLex().get(i).getLexem();
                        break;
                    case 1:
                        temp = "";
                        for (int k = 0; k < dijkstra.getStack().get(i).size(); k++) {//проход по внутреннему списку каждого элемента стека
                            temp += dijkstra.getStack().get(i).get(k).getLexem() + " ";//запоминаем что считали

                            if (dijkstra.getStack().get(i).get(k).getLabel().size() != 0) {//Если на эту лексему навешена метка,то

                                if (i >= ((Lexema) dijkstra.getStack().get(i).get(k)).getLabelAddPos()) {//и если позиция в входном
                                    //сообщении >= установленной для этой лексемы, то
                                    temp += " m[" + dijkstra.getStack().get(i).get(k).getStringLabel() + "] ";//добавляем и метку
                                }

                            }

                        }
                        data[i][j] = temp;
                        break;
                    case 2:
                        data[i][j] = dijkstra.getVariableCycleParam().get(i).getLexem();
                        break;
                    case 3:
                        data[i][j] = dijkstra.getCycleSign().get(i) + "";
                        break;
                    case 4:
                        temp = "";
                        for (int k = 0; k < dijkstra.getPoliz().get(i).size(); k++) {//проход по внутреннему списку каждого элемента полиза
                            if (dijkstra.getPoliz().get(i).get(k) instanceof Lexema) {//если элемент полиза наследуется от класса лексема,
                                if (((Lexema) dijkstra.getPoliz().get(i).get(k)).getLabel().size() != 0) {//и если на этот элемент навешена метка
                                    temp += " m[" + ((Lexema) dijkstra.getPoliz().get(i).get(k)).getStringLabel() + "]: ";//добавляем метку
                                } else {
                                    temp += ((Lexema) dijkstra.getPoliz().get(i).get(k)).getLexem() + " ";//если нет метки, то просто записываем в полиз
                                }
                            } else {
                                temp += (String) dijkstra.getPoliz().get(i).get(k) + " ";// если не наследуется от Lexema, то это Стринговый объект, который записывается просто
                            }
                        }
                        data[i][j] = temp;
                        polizString += temp;
                        break;
                }
            }
        }
        System.out.println(polizString);
        //вывод таблички Зар-ых слов
        table_12 = new JTable(data, columnNames1);
        table_12.setForeground(Color.BLACK);
        table_12.setFont(new Font("Arial", Font.PLAIN, 14));
        table_12.setBorder(new LineBorder(new Color(0, 0, 0), 0));

        scrollPane_12.setViewportView(table_12);

    }
}
