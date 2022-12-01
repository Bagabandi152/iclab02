import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader in, stdin;
    PrintStream out;

    Server() {
        try {
            System.out.println("Server started...");
            serverSocket = new ServerSocket(8888);
            socket = serverSocket.accept();
            out = new PrintStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            stdin = new BufferedReader(new InputStreamReader(System.in));

            Thread sender = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        while (true) {
                            System.out.print("Server: ");
                            msg = stdin.readLine();
                            out.println(msg);
                            out.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            sender.start();

            Thread receive = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        System.out.println("Client connected");
                        msg = in.readLine();
                        while (msg != null) {
                            System.out.println("Client : " + msg);
                            msg = in.readLine();
                        }
                        out.close();
                        socket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}