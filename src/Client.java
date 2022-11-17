import java.io.*;
import java.net.*;

public class Client {
    public static void main(String args[]) throws Exception {
        Socket socket = new Socket("localhost", 8888);
        BufferedReader inputVar = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream outVar = new PrintStream(socket.getOutputStream());
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while (true) {
            System.out.print("Client : ");
            str = stdin.readLine();
            outVar.println(str);
            if (str.equalsIgnoreCase("Bayartai")) {
                System.out.println("Holbolt sallaa...");
                break;
            }
            str = inputVar.readLine();
            System.out.print("Server : " + str + "\n");
        }
        socket.close();
        inputVar.close();
        outVar.close();
        stdin.close();
    }
}
