import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Calculator {
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextField textField3;

    public Calculator() {
        button1.addActionListener(e -> sendToServer("+"));
        button2.addActionListener(e -> sendToServer("-"));
        button3.addActionListener(e -> sendToServer("*"));
        button4.addActionListener(e -> sendToServer("/"));
    }

    private void sendToServer(String operator) {
        String num1 = textField1.getText();
        String num2 = textField2.getText();

        if (num1.isEmpty() || num2.isEmpty()) {
            textField3.setText("Missing numbers");
            return;
        }

        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(num1);
            out.println(num2);
            out.println(operator);

            String result = in.readLine();
            textField3.setText(result);

        } catch (IOException e) {
            textField3.setText("Server offline");
            e.printStackTrace();
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}