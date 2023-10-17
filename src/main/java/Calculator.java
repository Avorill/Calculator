import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class Calculator {
    private JFrame frame;
    private JTextField firstNumberField;
    private JTextField secondNumberField;
    private JTextField resultField;
    private JLabel fio;
    private JComboBox<String> operatorComboBox;
    private JButton calculateButton;
    public static void expoException()
            throws Exception
    {

        throw new Exception(
                "Exponentrial ");
    }
    public Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        fio = new JLabel("Ульнирова Полина Алексеевна, 4 курс, 4 группа, 2023");
        fio.setHorizontalAlignment(JLabel.CENTER);
        fio.setFont(new Font("Arial", Font.BOLD, 16));

        firstNumberField = new JTextField(10);
        operatorComboBox = new JComboBox<>(new String[]{"+", "-", "*", "/"});
        secondNumberField = new JTextField(10);
        calculateButton = new JButton("Calculate");
        resultField = new JTextField(15);
        resultField.setEditable(false);
        inputPanel.add(fio);
        inputPanel.add(new JLabel(""));
        inputPanel.add(firstNumberField);
        inputPanel.add(operatorComboBox);
        inputPanel.add(secondNumberField);
        inputPanel.add(calculateButton);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(resultField, BorderLayout.SOUTH);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });
    }

    private void calculate() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.#######");
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        customSymbols.setGroupingSeparator(' ');
        customSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(customSymbols);
        String firstNumberText = firstNumberField.getText();
        String operator = (String) operatorComboBox.getSelectedItem();
        String secondNumberText = secondNumberField.getText();

        // Проверка на наличие множественных пробелов
        if (firstNumberText.contains("  ") || secondNumberText.contains("  ")) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
            return;
        }

        if (!firstNumberText.matches("[0-9]+(\\s[0-9]{3})*(\\.|,)?[0-9]*")) {
            JOptionPane.showMessageDialog(frame, "Invalid input for the first number. Please enter a valid number.");
            return;
        }

// Проверка формата второго числа
        if (!secondNumberText.matches("[0-9]+(\\s[0-9]{3})*(\\.|,)?[0-9]*")) {
            JOptionPane.showMessageDialog(frame, "Invalid input for the second number. Please enter a valid number.");
            return;
        }

        // Преобразование введенных строк в числа
        String firstT = firstNumberText.replaceAll("\\s", "").replace(",", ".");
        String secondT = secondNumberText.replaceAll("\\s", "").replace(",", ".");
        BigDecimal first;
        BigDecimal second;

        try {
            try {
                if (firstT.contains("e") || firstT.contains("E")) {
                    expoException();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Exponential!");
                return;
            }
            first = new BigDecimal(firstT);
            second = new BigDecimal(secondT);
            BigDecimal result = null;

            switch (operator) {
                case "+":
                    result = first.add(second);
                    break;
                case "-":
                    result = first.subtract(second);
                    break;
                case "*":
                    result = first.multiply(second);
                    break;
                case "/":
                    try {
                        result = first.divide(second, 6, RoundingMode.HALF_UP);
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Calculator calculator = new Calculator();
                calculator.show();
            }
        });
    }
}
