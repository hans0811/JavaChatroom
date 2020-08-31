package serverTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	private String uID;
	private int port;
	private InetAddress address;
	static Socket clientSocket;
	static BufferedWriter bfWriter;
	static BufferedReader bfReader;

	public static boolean login(String uID, String uPassword) {
		boolean judge_Login = false;
		try {
			//clientSocket = new Socket(InetAddress.getLocalHost(), 40000);
			InetAddress localhost = InetAddress.getLocalHost();
			System.out.println(localhost);
			bfWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			bfReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			bfWriter.write(uID+uPassword);
            judge_Login = true;
			System.out.println("Message from client");
			//bfWriter.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
	            bfWriter.close();
	            bfReader.close();
	            clientSocket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		return judge_Login;
	}

	public static boolean sendMessage(String Mesg, String ip){
		boolean judge_Login = false;
		try {
			clientSocket = new Socket(InetAddress.getLocalHost(), 40000);
			InetAddress localhost = InetAddress.getLocalHost();
			System.out.println(localhost);
			bfWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			bfReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			bfWriter.write(Mesg+ip);
			judge_Login = true;
			System.out.println("SendMessage from client");
			//bfWriter.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				bfWriter.close();
				bfReader.close();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return judge_Login;

	}

	public static void main(String[] args) {

	    login("ans", "anna1234");

	}
}
