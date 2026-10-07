import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer {
    public static void main(String[] args) {
        ServerSocket ss = null;

        try {
            ss = new ServerSocket(5000);
            System.out.println("Server is waiting for connections on port 5000...");

            while (true) {
                Socket s = null;
                try {
                    s = ss.accept();

                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

                    InetSocketAddress remoteadr = (InetSocketAddress) s.getRemoteSocketAddress();
                    System.out.println("Client connected: " + remoteadr.getHostName() + ":" + remoteadr.getPort());

                    String num1Str = in.readLine();
                    String num2Str = in.readLine();
                    String operator = in.readLine();

                    if (num1Str != null && num2Str != null && operator != null) {
                        double num1 = Double.parseDouble(num1Str);
                        double num2 = Double.parseDouble(num2Str);
                        double result = 0;

                        switch (operator) {
                            case "+": result = num1 + num2; break;
                            case "-": result = num1 - num2; break;
                            case "*": result = num1 * num2; break;
                            case "/":
                                if (num2 == 0) {
                                    out.println("Error: Div by Zero");
                                    out.flush();
                                    continue;
                                }
                                result = num1 / num2;
                                break;
                            default:
                                out.println("Error: Unknown operator");
                                out.flush();
                                continue;
                        }

                        System.out.println("Server calculated: " + result);
                        out.println(String.valueOf(result));
                        out.flush();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (s != null) s.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ss != null) ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}