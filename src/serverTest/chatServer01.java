package serverTest;

import java.net.ServerSocket;
import java.net.Socket;

public class chatServer01 {

    public static void main(String[] args) throws Exception {
        int port = 8818;
        // for server
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            System.out.println("Accept Client connection");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accept Client from clientSocket");
            ServerWorker worker = new ServerWorker(clientSocket);
            worker.start();
        }


    }
}
