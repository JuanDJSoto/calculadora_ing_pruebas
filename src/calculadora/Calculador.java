package calculadora;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.JOptionPane;

public class Calculador {
    int conpunto = 0;
    public static int con=0;
    public static double[] hist = new double[11];
    int boardWidth = 360;
    int boardHeight = 540;

    //Color customLightBlue = new Color(34,56,141); 
    //Color customDarkBlue = new Color(26,37,135);
    //Color customBlack = new Color(28, 28, 28);
    //Color customPurple = new Color(123,56,141);
    //Color customGray = new Color(62,81,181);
    
    Color customLightBlue = new Color(0, 180, 216);
    Color customDarkBlue = new Color(3, 4, 94);
    Color customBlack = new Color(28, 28, 28);
    Color customPurple = new Color(0, 119, 182);
    Color customGray = new Color(144, 224, 239);
    Color customwhite = new Color(255,255,255);

    String[] buttonValues = {
        "AC", "C", "H", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        ".", "0", "00", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "C", "H"};
    String[] alternative = {""};
    
    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    //A+B, A-B, A*B, A/B
    String A = "0";
    String operator = null;
    String B = null;
    double resultado;

    Calculador() {
        // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
//cambio de tamaño
        displayLabel.setSize(360,85);
        displayLabel.setBackground(customGray);
        displayLabel.setForeground(Color.black);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(BorderFactory.createBevelBorder(0));
            //button.setBorder(BorderFactory.createEtchedBorder(1));
            //button.setBorder(new LineBorder(customBlack));
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customDarkBlue);
                button.setForeground(customwhite);
            }
            else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customPurple);
                button.setForeground(Color.white);
            }else if (Arrays.asList(alternative).contains(buttonValue)) {
                button.setBackground(customDarkBlue);
                button.setForeground(Color.white);
                button.setEnabled(false);
            }
            else {
                button.setBackground(customLightBlue);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue == "=") {
                            if (A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator == "+") {
                                    resultado = numA+numB;
                                    displayLabel.setText(String.format("%.2f", resultado));
                                    historial();
                                }
                                else if (operator == "-") {
                                    resultado = numA-numB;
                                    displayLabel.setText(String.format("%.2f", resultado));
                                    historial();
                                }
                                else if (operator == "×") {
                                    resultado = numA*numB;
                                    displayLabel.setText(String.format("%.2f", resultado));
                                    historial();
                                }
                                else if (operator == "÷") {
                                    if (numB == 0){
                                        JOptionPane.showMessageDialog(null, "No es posible dividir entre 0");
                                    }else{
                                    resultado = numA/numB;
                                    displayLabel.setText(String.format("%.2f", resultado));
                                    historial();
                                    }
                                }
                                clearAll();
                            }
                        }
                        else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                                conpunto++;
                            }
                            operator = buttonValue;
                        }
                    }
                    else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            clearAll();
                            displayLabel.setText("0");
                        }
                        else if (buttonValue == "C") {
                            String valor = "";
                            valor = displayLabel.getText();
                            if(valor.length()==1){
                                displayLabel.setText("0");
                            }else{
                            valor = valor.substring(0,valor.length()-1);
                            displayLabel.setText(valor);
                            }
                            //displayLabel.setText(removeZeroDecimal(numDisplay));
                        }else if(buttonValue == "H"){
                            new History_Manager().setVisible(true);
                        }
                    }
                    else { //digits or . 
                        if (buttonValue == ".") {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                if(conpunto>1){
                                    JOptionPane.showMessageDialog(null, "Solo un punto por operando");
                                }else{
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                                }
                                }
                        }
                        else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            }
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }else if ("00".contains(buttonValue)){
                            displayLabel.setText(displayLabel.getText() + buttonValue);
                        }
                    }
                }
            });
            frame.setVisible(true);
        }
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }
    
    void historial(){
        if(con<10){
            hist[con]= resultado;
            con++;
        }else{
            for(int i = 0 ; i < 10 ; i++){
                hist[i] = hist[i+1];
            }
            hist[10] = 0;
            hist[9]=resultado;
        }
    //JOptionPane.showMessageDialog(null, con+"-----"+hist[con-1]);
}

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
