import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;



public class Calculator {
    final private JFrame frame;
    final private JTextField firstNumberField;
    final private JTextField secondNumberField;
    final private JTextField resultField;
    final private JComboBox<String> firstOperatorComboBox;
    final private JComboBox<String> secondOperatorComboBox;
    final private JComboBox<String> thirdOperatorComboBox;

    final private JTextField thirdNumberField;
    final private JTextField fourthNumberField;
    final private JTextField roundedResultField;
    final private JComboBox roundingModeComboBox;

    public Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));

        JLabel fio = new JLabel("Ульнирова Полина Алексеевна, 4 курс, 4 группа, 2023");
        fio.setHorizontalAlignment(JLabel.CENTER);
        fio.setFont(new Font("Arial", Font.BOLD, 16));

        firstNumberField = new JTextField("0",10);
        firstOperatorComboBox = new JComboBox<>(new String[]{"+", "-", "*", "/"});
        secondNumberField = new JTextField("0",10);
        secondOperatorComboBox =  new JComboBox<>(new String[]{"+", "-", "*", "/"});
        thirdNumberField = new JTextField("0",10);
        thirdOperatorComboBox =  new JComboBox<>(new String[]{"+", "-", "*", "/"});
        fourthNumberField = new JTextField("0",10);

        JButton calculateButton = new JButton("Calculate");
        resultField = new JTextField(15);
        resultField.setEditable(false);
        roundingModeComboBox = new JComboBox<>(new String[]{"Math", "Accounting", "Truncate"}); // Add rounding mode options
        roundedResultField = new JTextField(15);
        roundedResultField.setEditable(false);
        inputPanel.add(fio);
        inputPanel.add(new JLabel(""));
        inputPanel.add(firstNumberField);
        inputPanel.add(firstOperatorComboBox);
        inputPanel.add(secondNumberField);
        inputPanel.add(secondOperatorComboBox);
        inputPanel.add(thirdNumberField); // Добавить поле для третьего операнда
        inputPanel.add(thirdOperatorComboBox);
        inputPanel.add(fourthNumberField); // Добавить поле для четвертого операнда
        inputPanel.add(calculateButton);
        inputPanel.add(new JLabel("Rounding Mode:"));
        inputPanel.add(roundingModeComboBox);
        inputPanel.add(resultField);
        frame.add(inputPanel, BorderLayout.CENTER);
        //frame.add(resultField, BorderLayout.SOUTH);
        frame.add(roundedResultField, BorderLayout.SOUTH);


        calculateButton.addActionListener(e -> calculate());
    }

    private void calculate() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.######");
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        customSymbols.setGroupingSeparator(' ');
        customSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(customSymbols);
        String firstNumberText = firstNumberField.getText();
        String firstOperator = (String) firstOperatorComboBox.getSelectedItem();
        String secondNumberText = secondNumberField.getText();
        String secondOperator = (String)secondOperatorComboBox.getSelectedItem();
        String thirdOperator = (String)thirdOperatorComboBox.getSelectedItem();
        String thirdNumberText = thirdNumberField.getText();
        String fourthNumberText = fourthNumberField.getText();
        String roundingModeText = (String) roundingModeComboBox.getSelectedItem();
        // Проверка на наличие множественных пробелов
        if(firstNumberText.contains("e") || secondNumberText.contains("e") || thirdNumberText.contains("e") ||
        fourthNumberText.contains("e")){
            JOptionPane.showMessageDialog(frame, "Exponential!");
            return;
        }
        if (firstNumberText.contains("  ") || secondNumberText.contains("  ") ||
        thirdNumberText.contains("  ") || fourthNumberText.contains("  ") ) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
            return;
        }

        if (!firstNumberText.matches("-?[0-9]+(\\s[0-9]{3})*([.,])?[0-9]*")) {
            JOptionPane.showMessageDialog(frame, "Invalid input for the first number. Please enter a valid number.");
            return;
        }


        if (!secondNumberText.matches("-?[0-9]+(\\s[0-9]{3})*([.,])?[0-9]*")) {
            JOptionPane.showMessageDialog(frame, "Invalid input for the second number. Please enter a valid number.");
            return;
        }
        if (!thirdNumberText.matches("-?[0-9]+(\\s[0-9]{3})*([.,])?[0-9]*")) {
            JOptionPane.showMessageDialog(frame, "Invalid input for the second number. Please enter a valid number.");
            return;
        }
        if (!fourthNumberText.matches("-?[0-9]+(\\s[0-9]{3})*([.,])?[0-9]*")) {
            JOptionPane.showMessageDialog(frame, "Invalid input for the second number. Please enter a valid number.");
            return;
        }
        // Преобразование введенных строк в числа
        String firstT = firstNumberText.replaceAll("\\s", "").replace(",", ".");
        String secondT = secondNumberText.replaceAll("\\s", "").replace(",", ".");
        String thirdT = thirdNumberText.replaceAll("\\s", "").replace(",", ".");
        String fourthT = fourthNumberText.replaceAll("\\s", "").replace(",", ".");
        BigDecimal first;
        BigDecimal second;
        BigDecimal third;
        BigDecimal fourth;

        try {
            first = new BigDecimal(firstT);
            second = new BigDecimal(secondT);
            third = new BigDecimal(thirdT);
            fourth = new BigDecimal(fourthT);
            BigDecimal result = null;
            BigDecimal preResult = null;
            BigDecimal preResultSecond = null;
            switch (secondOperator) {
                case "+":
                    preResult = second.add(third);
                    break;
                case "-":
                    preResult = second.subtract(third);
                    break;
                case "*":
                    preResult = second.multiply(third);
                    break;
                case "/":
                    try {
                        preResult = second.divide(third, 10, RoundingMode.HALF_UP);
                    } catch (ArithmeticException ex) {
                        JOptionPane.showMessageDialog(frame, "Division by zero");
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Operation not found.");
                    break;
            }

            if (preResult != null) {
                resultField.setText(decimalFormat.format(preResult));
            }
            switch (firstOperator) {
                case "+":
                    preResultSecond = first.add(preResult);
                    break;
                case "-":
                    preResultSecond = first.subtract(preResult);
                    break;
                case "*":
                    preResultSecond = first.multiply(preResult);
                    break;
                case "/":
                    try {
                        preResultSecond = first.divide(preResult, 10, RoundingMode.HALF_UP);
                    } catch (ArithmeticException ex) {
                        JOptionPane.showMessageDialog(frame, "Division by zero");
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Operation not found.");
                    break;
            }
            if (preResultSecond != null) {
                resultField.setText(decimalFormat.format(preResultSecond));
            }
            switch (thirdOperator) {
                case "+":
                    result = preResultSecond.add(fourth);
                    break;
                case "-":
                    result = preResultSecond.subtract(fourth);
                    break;
                case "*":
                    result = preResultSecond.multiply(fourth);
                    break;
                case "/":
                    try {
                        result = preResultSecond.divide(fourth, 6, RoundingMode.HALF_UP);
                    } catch (ArithmeticException ex) {
                        JOptionPane.showMessageDialog(frame, "Division by zero");
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Operation not found.");
                    break;
            }
            if (result != null) {
                resultField.setText(decimalFormat.format(result));
                BigDecimal roundedResult ;
                switch (roundingModeText) {
                    case "Math":
                        roundedResult = result.setScale(0, RoundingMode.HALF_UP);
                        break;
                    case "Accounting":
                        roundedResult = result.setScale(0, RoundingMode.HALF_EVEN);
                        if (roundedResult.compareTo(BigDecimal.ZERO) != 0) {
                            roundedResult = roundedResult.setScale(0, RoundingMode.HALF_UP);
                        }
                        break;
                    case "Truncate":
                        roundedResult = result.setScale(0, RoundingMode.DOWN);
                        break;
                    default:
                        roundedResult = result;
                        break;
                }
                roundedResultField.setText(decimalFormat.format(roundedResult));
            }

        } catch (NumberFormatException | ArithmeticException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
        }

    }



    public void show() {
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.show();
        });
    }
}
