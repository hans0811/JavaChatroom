package serverTest;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;

public class ServerWorker extends Thread{

    private final Socket clientSocket;

    public ServerWorker(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    // each Client has Thread
    @Override
    public void run(){
        try {
            handleClientSocket();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        // Reading inputStream from client.
        InputStream inputStream = clientSocket.getInputStream();
        // To get data from client
        OutputStream outputStream = clientSocket.getOutputStream();
        // Reading input line by line
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // echo, send back to the client
        String line;
        while((line = reader.readLine()) != null){
            // StringUtils from commom-lang3, using for server
            String[] tokens = StringUtils.split(line);

            if(tokens != null && tokens.length >0){
                String cmd = tokens[0];
                if("quit".equalsIgnoreCase(cmd)){
                    break;
                }else{
                    String msg = "unkown" + cmd + "\n";
                    outputStream.write(msg.getBytes());
                }
                String msg = "You typed: " + line + "\n";
                outputStream.write(msg.getBytes());
            }// End tokens if
        }

        clientSocket.close();
    }
}
