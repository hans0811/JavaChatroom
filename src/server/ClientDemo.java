package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class ClientDemo {
	public static int flag = 0;
	public static int socket_index = 0;
	public static String u_loginid = "";
	public final static String splitMark = ";";

	Socket socket = null;
	static Scanner inScanner = null;
	static PrintWriter pwtoserver = null;
	Thread clientReceiveThread;

	static boolean login_Reply = false;
	static String communicate_Reply = "";
	static int acquireSocketIndex_Reply = 0;
	static boolean register_Reply = false;
	static ArrayList<TreeMap<Integer, Socket>> socketList = new ArrayList<TreeMap<Integer, Socket>>();
	public static TreeMap<String, Integer> loginid_indexPair = new TreeMap<String, Integer>();
	public static TreeMap<Integer, String> index_loginidPair = new TreeMap<>();
	static boolean login_Finish = false;
	static boolean communicate_Finish = false;
	static boolean acquireSocketIndex_Finish = false;
	static boolean register_Finish = false;
	static boolean updateOlineUser_Finish = false;

	public ClientDemo() {
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

	public static boolean login(String u_id, String u_password) {
		flag = 0;
		String msg = flag + splitMark + u_id + splitMark + u_password;
		System.out.println("Request for login...");
		pwtoserver.println(flag + ";" + u_id + ";" + u_password);
		pwtoserver.flush();
		while (!login_Finish) {
			System.out.print("");
		}
		System.out.println("Login msg: " + socket_index + " " + u_loginid + " " + login_Reply);
		return login_Reply;
	}

//	public static void acquireSocketIndex() {
//		flag = 1;
//		System.out.println("Request for SocketIndex...");
//		pwtoserver.println(flag);
//		pwtoserver.flush();
//		while (!acquireSocketIndex_Finish) {
//			System.out.print("");
//		}
//		System.out.println("AcquireSocketIndex msg: " + acquireSocketIndex_Reply);
//	}

	public static void communicate(String senderSocketIndex, String msg, String receiverSocketIndex) {
        flag = 2;
        System.out.println("Request for communicate...");
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			String name;
//			name = br.readLine();
        pwtoserver
                .println(flag + splitMark + senderSocketIndex + splitMark + msg + splitMark + receiverSocketIndex);
        pwtoserver.flush();
    }

	public static boolean register(String u_realname, String u_username, String u_password, int u_gender,
			String u_email, String u_phonenum) {
		flag = 3;
		System.out.println("Request for Register...");
		pwtoserver.println(flag + splitMark + u_realname + splitMark + u_username + splitMark + u_password + splitMark
				+ u_gender + splitMark + u_email + splitMark + u_phonenum);
		pwtoserver.flush();
		while (!register_Finish) {
			System.out.print("");
		}
		System.out.println("Register msg: " + register_Reply);
//		if(register_Reply) {
//			acquireSocketIndex();
//		}
		return register_Reply;
	}

//	public static void displayReply(String msg, String senderSocketIndex) {
//		// Assign msg to GUI and display.
//		// This method should be in GUI.
//		// setText(msg);
//		System.out.println("You receive a new msg from another client: " + msg);
//	}

	public static void main(String[] args) throws IOException {
		ClientDemo clientDemo = new ClientDemo();
//		clientDemo.acquireSocketIndex();
//		ClientDemo.register("Ciel Phantomhive", "cip123","ciel1234",1,"ciel@gmail.com", "01234567890");
		clientDemo.login("ans", "anna123456");
//		ClientDemo.communicate("1", "000to111", "0");
	}
}
