import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class RunAssignment extends JFrame {

    private JPanel contentPane;
    private JTextField txtV;
    private JTextField txtT;
    private JTextField txtS;
    private JTextField txtPLeft;
    private JTextField txtPRight;
    private JTextField txtInput;
    private JTextArea txaOutput;
    private Grammar obj= new Grammar();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RunAssignment frame = new RunAssignment();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public RunAssignment() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 938, 459);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(100, 191, 216));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("G = (V, T, S, P)");
        lblNewLabel.setBounds(10, 11, 179, 28);
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 23));
        contentPane.add(lblNewLabel);

        JLabel lblV = new JLabel("V =");
        lblV.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblV.setBounds(20, 50, 39, 22);
        contentPane.add(lblV);

        txtV = new JTextField();
        txtV.setBounds(84, 53, 161, 20);
        contentPane.add(txtV);
        txtV.setColumns(10);

        JLabel label = new JLabel("{");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        label.setBounds(71, 43, 16, 37);
        contentPane.add(label);

        JLabel label_1 = new JLabel("}");
        label_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        label_1.setBounds(250, 43, 16, 37);
        contentPane.add(label_1);

        JLabel lblT = new JLabel("T =");
        lblT.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblT.setBounds(20, 90, 39, 22);
        contentPane.add(lblT);

        JLabel label_3 = new JLabel("{");
        label_3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        label_3.setBounds(71, 83, 16, 37);
        contentPane.add(label_3);

        txtT = new JTextField();
        txtT.setColumns(10);
        txtT.setBounds(84, 93, 161, 20);
        contentPane.add(txtT);

        JLabel label_4 = new JLabel("}");
        label_4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        label_4.setBounds(250, 83, 16, 37);
        contentPane.add(label_4);

        JLabel lblS = new JLabel("S =");
        lblS.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblS.setBounds(20, 130, 39, 22);
        contentPane.add(lblS);

        JLabel label_5 = new JLabel("{");
        label_5.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        label_5.setBounds(71, 123, 16, 37);
        contentPane.add(label_5);

        txtS = new JTextField();
        txtS.setColumns(10);
        txtS.setBounds(84, 133, 161, 20);
        contentPane.add(txtS);

        JLabel label_6 = new JLabel("}");
        label_6.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        label_6.setBounds(250, 123, 16, 37);
        contentPane.add(label_6);

        JLabel lblP = new JLabel("P:");
        lblP.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblP.setBounds(20, 170, 39, 22);
        contentPane.add(lblP);

        txtPLeft = new JTextField();
        txtPLeft.setBounds(84, 170, 37, 20);
        contentPane.add(txtPLeft);
        txtPLeft.setColumns(10);

        JLabel label_2 = new JLabel("->");
        label_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        label_2.setBounds(131, 168, 39, 22);
        contentPane.add(label_2);

        txtPRight = new JTextField();

        txtPRight.setColumns(10);
        txtPRight.setBounds(159, 170, 86, 20);
        contentPane.add(txtPRight);

        JButton btnAddProductionRule = new JButton("ADD RULE");

        btnAddProductionRule.setBounds(84, 203, 161, 23);
        contentPane.add(btnAddProductionRule);

        JButton btnCheck = new JButton("CHECK");

        btnCheck.setBounds(84, 357, 161, 48);
        contentPane.add(btnCheck);

        DefaultListModel<String> model = new DefaultListModel<String>();
        JList list = new JList(model);
        list.setFont(new Font("Monospaced", Font.PLAIN, 13));

        ArrayList<String> PLeft = new ArrayList<String>();//list for left hand side of production rule
        ArrayList<String> PRight = new ArrayList<String>();//list for right hand side of production rule

        JButton btnRemoveRule = new JButton("REMOVE RULE");
        btnRemoveRule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PLeft.remove(list.getSelectedIndex());//remove from left hand side of production rule
                    PRight.remove(list.getSelectedIndex());//remove from right hand side of production rule
                } catch (Exception e2) {
                }
                model.removeElement(list.getSelectedValue());//delete from list
            }
        });
        btnRemoveRule.setBounds(250, 325, 115, 23);
        contentPane.add(btnRemoveRule);

        txtInput = new JTextField();
        txtInput.setEnabled(false);
        txtInput.setColumns(10);
        txtInput.setBounds(495, 44, 331, 20);
        contentPane.add(txtInput);

        JButton btnParseInput = new JButton("PARSE INPUT");
        btnParseInput.setEnabled(false);

        btnParseInput.setBounds(607, 74, 115, 23);
        contentPane.add(btnParseInput);

        txaOutput = new JTextArea();
        txaOutput.setEditable(false);
        txaOutput.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JLabel lblInputString = new JLabel("INPUT STRING:");
        lblInputString.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblInputString.setBounds(495, 23, 158, 22);
        contentPane.add(lblInputString);

        JScrollPane scrollPane = new JScrollPane(txaOutput);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(495, 111, 331, 240);
        contentPane.add(scrollPane);

        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listScrollPane.setBounds(84, 237, 161, 109);
        contentPane.add(listScrollPane);

        JButton btnLoadTextfile = new JButton("LOAD TEXTFILE");
        btnLoadTextfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    txaOutput.setText("");
                    model.clear();//clear GUI list for production rules
                    PLeft.clear();//clear arraylist for left hand side of production rules
                    PRight.clear();//clear arraylist for right hand side of production rules
                    Scanner sc = new Scanner(new FileReader("TestData.txt"));//load textfile
                    String line = sc.nextLine();//read line
                    while(sc.hasNext()) {//loop while file has more lines

                        if(line.equals("***")) {//*** marks a new grammar in textfile

                            //read from textfile then add to GUI elements
                            txtInput.setText(sc.nextLine());//first line of textfile is the input string
                            txtV.setText(sc.nextLine());//second line is the variables for set V
                            txtT.setText(sc.nextLine());//third line is the variables for set T
                            txtS.setText(removeWhitespacesAndCommas(sc.nextLine()));//fourth line is the starting character S

                            line = sc.nextLine();//fifth line and onwards are the production rules until *** or 'end' is reached
                            while(!line.equals("***") && sc.hasNext() && !line.equals("end")){
                                String [] details = line.split("->");//split the line into details array
                                //read from textfile then add to GUI elements
                                txtPLeft.setText(details[0]);//details[0] contains left hand side of production rule
                                txtPRight.setText(details[1]);//details[1] contains right hand side of production rule
                                line = sc.nextLine();//load next line
                                btnAddProductionRule.doClick();//programmatically press Add Rule button
                            }

                            btnCheck.doClick();//after all rules added ,programmatically press Check button
                            btnParseInput.doClick();//programmatically press Parse Input button
                            if(line.equals("***")) {//another grammar is available in textfile
                                int option = JOptionPane.showOptionDialog(null, "Another S-Grammar available, would you like to test it?", "Load Textfile", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,null);

                                if(option==0) {//if yes clicked
                                    model.clear();//clear GUI list for production rules
                                    PLeft.clear();//clear arraylist for left hand side of production rules
                                    PRight.clear();//clear arraylist for right hand side of production rules
                                    continue;
                                }
                                else {//if no or cancel clicked exit loop
                                    break;
                                }
                            }else if(line.equals("end")) {//end of textfile
                                break;
                            }

                        }
                        line = sc.nextLine(); // skip commented lines in textfile
                    }
                } catch (FileNotFoundException e1) {//file could not be loaded
                    JOptionPane.showMessageDialog(null, "Error! File not found.","Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        btnLoadTextfile.setBounds(665, 357, 161, 48);
        contentPane.add(btnLoadTextfile);


        btnAddProductionRule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String left = removeWhitespacesAndCommas(txtPLeft.getText());//remove spaces and commas from production rule textfield
                String right = removeWhitespacesAndCommas(txtPRight.getText());//remove spaces and commas from production rule textfield

                if(!right.contains("|")) {//rule with no or statements
                    PLeft.add(left);//add to left hand side of production rule list
                    PRight.add(right);//add to right hand side of production rule list
                    model.addElement(left + " -> "+right);//add to GUI list
                }
                else {//or statements found
                    String [] details = right.split("\\|");//split or statements

                    for (int i = 0; i < details.length; i++) {//separate or statements into their own production rules
                        PLeft.add(left);
                        PRight.add(details[i]);
                        model.addElement(left + " -> "+details[i]);
                    }
                }
                txtPLeft.setText("");
                txtPRight.setText("");

            }
        });

        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(txtS.getText().length() !=1) {//check if textfield for S contains one character only
                    JOptionPane.showMessageDialog(null, "Please enter 1 character only for S", "Error", JOptionPane.ERROR_MESSAGE);
                }else {
                    ArrayList<Character> V = stringToArrayList(removeWhitespacesAndCommas(txtV.getText()));//convert text from V textfield to arraylist
                    ArrayList<Character> T = stringToArrayList(removeWhitespacesAndCommas(txtT.getText()));//convert text from T textfield to arraylist

                    obj = new Grammar(V, T, txtS.getText().charAt(0), PLeft, PRight);//create new object
                    if(obj.isValidGrammar()) {//check if grammar valid
                        JOptionPane.showMessageDialog(null, "S-Grammar is valid.", "Valid", JOptionPane.INFORMATION_MESSAGE);
                        btnParseInput.setEnabled(true);//enable input textfield and parse input button
                        txtInput.setEnabled(true);
                    }
                    else {//grammar not valid
                        JOptionPane.showMessageDialog(null, "S-Grammar is NOT valid.", "Not Valid", JOptionPane.ERROR_MESSAGE);
                        txtInput.setEnabled(false);
                        btnParseInput.setEnabled(false);
                    }


                }

            }
        });
        btnParseInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txaOutput = obj.leftDerivation(txtInput.getText(), txaOutput);//call method in grammar class - returns output

            }
        });
        txtPRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {//check if enter key is pressed on right hand side of production rule textfield
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnAddProductionRule.doClick();
                    txtPLeft.requestFocus();//makes adding rules more user friendly
                }
            }
        });
    }

    public String removeWhitespacesAndCommas(String s) {//method to remove whitespaces and commas from string
        String str = s;
        str = str.trim().replaceAll("\\s", "").replaceAll(",", "");
        return str;
    }

    public ArrayList<Character> stringToArrayList(String s){//method to convert string to arraylist of characters
        ArrayList<Character> list = new ArrayList<Character>();
        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i));
        }

        return list;
    }
}
