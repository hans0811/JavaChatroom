package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientDemo1 {
	public static int flag = 0;
	private static int socketIndex = 0;
	public final static String splitMark = ";";

	Socket socket = null;
	static Scanner inScanner = null;
	static PrintWriter pwtoserver = null;
	Thread clientReceiveThread;

	static boolean login_Reply = false;
	static String communicate_Reply = "";
	static int acquireSocketIndex_Reply = 0;

	static boolean login_Finish = false;
	static boolean communicate_Finish = false;
	static boolean acquireSocketIndex_Finish = false;

	public ClientDemo1() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), 6666);
			inScanner = new Scanner(socket.getInputStream());
			pwtoserver = new PrintWriter(socket.getOutputStream());
			clientReceiveThread = new Thread(new ClientReceive(inScanner));
			clientReceiveThread.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String u_id, String u_password) {
		flag = 0;
		String msg = flag + splitMark + u_id + splitMark + u_password;
		System.out.println("Request for login...");
		pwtoserver.println(flag + ";" + u_id + ";" + u_password);
		pwtoserver.flush();
		while (!login_Finish) {
			System.out.print("");
		}
		System.out.println("Login msg: " + login_Reply);
		if(login_Reply) {
			acquireSocketIndex();
		}
		return login_Reply;
	}

	public void acquireSocketIndex() {
		flag = 1;
		System.out.println("Request for SocketIndex...");
		pwtoserver.println(flag);
		pwtoserver.flush();
		while (!acquireSocketIndex_Finish) {
			System.out.print("");
		}
		System.out.println("AcquireSocketIndex msg: " + acquireSocketIndex_Reply);
	}

	public static String communicate(String senderSocketIndex, String msg, String receiverSocketIndex) {
		try {
			flag = 2;
			System.out.println("Request for communicate...");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String name;
			name = br.readLine();
			pwtoserver
					.println(flag + splitMark + senderSocketIndex + splitMark + name + splitMark + receiverSocketIndex);
			pwtoserver.flush();
			while (!communicate_Finish) {
				System.out.print("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Communicate msg: " + communicate_Reply);
		return communicate_Reply;
	}

	public static void main(String[] args) throws IOException {
		ClientDemo1 clientDemo1 = new ClientDemo1();
//		clientDemo1.acquireSocketIndex();
//		ClientDemo1.register("Ciel Phantomhive", "ciel@gmail.com", "01234567890", "cip123", "ciel1234",1);
		clientDemo1.login("ans", "anna123456");
		ClientDemo1.communicate("1", "000to111", "0");
	}
}
