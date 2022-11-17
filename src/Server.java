import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    int pt;
    ServerSocket ss = null;
    Socket socket = null;
    ExecutorService es = null;
    int clientCount = 0;

    public static void main(String[] args) throws IOException {
        Server sObject = new Server(8888);
        sObject.startServer();
    }

    Server(int pt) {
        this.pt = pt;
        es = Executors.newFixedThreadPool(5);
    }

    public void startServer() throws IOException {
        ss = new ServerSocket(pt);
        System.out.println("Server aslaa...");
        System.out.println("'Bayartai' gj ilgeeh uyd holbolt salna...");
        while (true) {
            socket = ss.accept();
            clientCount++;
            ServerThread st = new ServerThread(socket, clientCount, this);
            es.execute(st);
        }
    }

    private static class ServerThread implements Runnable {

        Server server = null;
        Socket client = null;
        BufferedReader s1;
        PrintStream s2;
        Scanner sc = new Scanner(System.in);
        int id;
        String s;

        ServerThread(Socket client, int count, Server server) throws IOException {
            this.client = client;
            this.server = server;
            this.id = count;
            System.out.println(id + " client holbogdloo.");

            s1 = new BufferedReader(new InputStreamReader(client.getInputStream()));
            s2 = new PrintStream(client.getOutputStream());
        }

        @Override
        public void run() {
            int x = 1;
            try {
                while (true) {
                    s = s1.readLine();
                    System.out.print("Client(" + id + ") : " + s + "\n");
                    System.out.print("Server: ");
                    s = sc.nextLine();
                    if (s.equalsIgnoreCase("Bayartai")) {
                        s2.println("Bayartai");
                        x = 0;
                        System.out.println("Holbolt sallaa...");
                        break;
                    }
                    s2.println(s);
                }

                s1.close();
                client.close();
                s2.close();
                if (x == 0) {
                    System.exit(0);
                }
            } catch (IOException e) {
                System.out.println("Aldaa : " + e);
            }
        }
    }
}