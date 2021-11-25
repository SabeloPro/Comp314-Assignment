import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Grammar {
    ArrayList<Character> VCharacters = new ArrayList<Character>(Arrays.asList('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'));
    ArrayList<Character> TCharacters = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','-','+','*','/','}','{','=', '!', '#', '%', '^', '&', '(', ')', '?')) ;
    ArrayList<Character> V;
    ArrayList<Character> T;
    char S;
    ArrayList<String> PLeft;//left hand side of production rule
    ArrayList<String> PRight;//right hand side of production rule
    String [][] derivationTable;

    public Grammar(ArrayList<Character> V, ArrayList<Character> T, char S, ArrayList<String> PLeft, ArrayList<String> PRight) {
        this.V = V;
        this.T = T;
        this.S = S;
        this.PLeft = PLeft;
        this.PRight = PRight;
    }

    public Grammar() {
    }

    public boolean isValidGrammar() {
        boolean valid = true;



        for (int i = 0; i < V.size(); i++) {//checking if characters in V are all capital letters
            if(!VCharacters.contains(V.get(i))) {
                return false;
            }

        }

        for (int i = 0; i < T.size(); i++) {//checking if the terminals are all lowercase letters or special characters
            if(!TCharacters.contains(T.get(i))) {
                return false;
            }
        }

        if(!V.contains(S)) {//checking if S is an element of the Variables
            return false;
        }

        for (int i = 0; i < PLeft.size(); i++) {
            if(PLeft.get(i).length()!=1) {//checking if there is more than one character on the left hand side of production rules
                return false;
            }
            else if(!V.contains(PLeft.get(i).charAt(0))){//checking for unknown variable in production rules
                return false;
            }

        }

        int terminalCount = 0;
        for (int i = 0; i < PRight.size(); i++) {
            String s = PRight.get(i);
            if(s.length()>0) {//check if right hand side of production rule is not empty
                for (int j = 0; j < s.length(); j++) {
                    if(!V.contains(s.charAt(j)) && !T.contains(s.charAt(j))) {//checking if character on right hand side of production rule is not an element of V or T
                        return false;
                    }

                    if(j==0 && !T.contains(s.charAt(j))) {//if right hand side of production starts with a non-terminal
                        return false;
                    }

                    if(T.contains(s.charAt(j))) {//count number of terminals
                        terminalCount++;;
                    }

                    if(terminalCount>1) {//number of terminals cannot be larger than 1
                        return false;
                    }
                }
                terminalCount = 0;
            }
            else {//right hand side of production rule is empty
                return false;
            }

        }

        for (int i = 0; i < V.size(); i++) {
            if(!PLeft.contains(V.get(i).toString())) {//checking if every variable in V has a production rule
                JOptionPane.showMessageDialog(null, "Error! Please add production rule for Variable "+V.get(i).toString(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        for (int i = 0; i < PLeft.size(); i++) {//checking if two productions with same variable on left hand side start with the same terminal on the right hand side
            for (int j = 0; j < PLeft.size(); j++) {
                if(i!=j && PLeft.get(i).equals(PLeft.get(j)) && PRight.get(i).charAt(0)==PRight.get(j).charAt(0)) {
                    return false;
                }
            }
        }

        generateDerivationTable();//if grammar is valid generate derivation table
        return valid;
    }

    private void generateDerivationTable() {//generate 2d array to store derivation rules
        derivationTable = new String[V.size()][T.size()];//each element in V has a row and each terminal in T has a column
        for (int i = 0; i < V.size(); i++) {
            for (int j = 0; j < PLeft.size(); j++) {
                if(V.get(i).toString().equals(PLeft.get(j))) {//checking if variable in V = left hand side of production rule to find which row to insert rule
                    int column = T.indexOf(PRight.get(j).charAt(0));//finding which row to insert rule
                    derivationTable[i][column] = PRight.get(j);
                }
            }
        }
    }

    public JTextArea leftDerivation(String input, JTextArea txaOutput) {
        Stack<Character> stack = new Stack<Character>();
        String orginalInput = input;//to compare if derivation is complete
        input = input + "$";//$ indicates end of string
        char lookahead = input.charAt(0);//used to check if derivation matches 'next' (first) character of input string
        stack.push(S);
        txaOutput.setText(String.format("%-20s%-18s\n", "Production", "Derivation"));
        txaOutput.append(String.format("%-20s%-18s\n", "", S+""));
        boolean member = true;
        String derivationString = S+"";
        while(member) {
            if(!lookaheadExists(lookahead)) {//check if lookahead is a valid terminal
                txaOutput.append("\nNo rule for "+stack.pop()+" with "+lookahead+" as lookahead. \nString rejected.");
                break;
            }
            else {
                char variable = stack.pop();

                String rule = derivationTable[V.indexOf(variable)][T.indexOf(lookahead)];//get production rule for variable V removed from top of stack
                if(rule!=null) {//if a production rule exists
                    for (int i = rule.length()-1; i >=0; i--) {
                        stack.push(rule.charAt(i));//add each character of the production rule backwards (left derivation)
                    }

                }
                else {//if no rule found
                    txaOutput.append("\nNo rule for "+variable+" with "+lookahead+" as lookahead. \nString rejected.");
                    break;
                }
                derivationString = derivationString.replaceFirst(variable+"", rule);//replace variable V in string with rule to show derivation
                txaOutput.append(String.format("%-20s%-18s\n", variable+" -> "+rule, derivationString));
                char terminal = stack.pop();//pop terminal from stack
                if(terminal==lookahead) {//match found
                    input = input.substring(1);//remove first character from input string
                    lookahead = input.charAt(0);//lookahead is at first character which is technically the 'next' one
                    if(lookahead == '$' || stack.isEmpty()) {//end of string reached or stack is empty
                        if(derivationString.equals(orginalInput)) {//derivation successful
                            txaOutput.append("\nSTRING SUCCESSFULLY PARSED!");
                        }
                        else {//derivation unsuccessful
                            txaOutput.append("\nString rejected.");
                        }
                        break;//exit loop
                    }
                }else {//terminal does not match lookahead thus string rejected
                    txaOutput.append("\nNo rule for "+terminal+" with "+lookahead+" as lookahead. \nString rejected.");
                    break;
                }
            }
        }
        return txaOutput;

    }

    private boolean lookaheadExists(char c) {//method to check if the lookahead exists in T
        return T.contains(c);
    }
}
