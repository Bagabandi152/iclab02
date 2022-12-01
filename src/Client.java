import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    BufferedReader in, stdin;
    PrintStream out;

    Client() {
        try {
            socket = new Socket("localhost", 8888);
            out = new PrintStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdin = new BufferedReader(new InputStreamReader(System.in));
            Thread sender = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        while (true) {
                            System.out.print("Client : ");
                            msg = stdin.readLine();
                            out.println(msg);
                            out.flush();
                            if (msg.equalsIgnoreCase("close")) {
                                System.out.println("Connection has broken...");
                                out.close();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            sender.start();

            Thread receiver = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while (msg != null) {
                            System.out.println("Server : " + msg);
                            msg = in.readLine();
                        }
                        System.out.println("Server out of service");
                        out.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}