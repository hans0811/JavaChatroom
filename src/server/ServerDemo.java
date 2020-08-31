package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

public class ServerDemo {
	public static int socket_index = -1;
//	public static ArrayList<TreeMap<Integer, Socket>> socketList = new ArrayList<TreeMap<Integer, Socket>>();
//	public static ArrayList<Integer> allSocketIndexList = new ArrayList<Integer>();
	public static TreeMap<Integer, Socket> index_socketPair = new TreeMap<Integer, Socket>();
	public static TreeMap<Integer, String> index_loginidPair = new TreeMap<Integer, String>();
	public static void main(String[] args) {
		try {
			// ServerSocket serverSocket=new ServerSocket(10086);
			ServerSocket serverSocket = new ServerSocket(6666);
			System.out.println("The server has already been started 6666");
			CnctSqlDemo.cleanOnlineUser();
			//
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Client accesses serverDemo");
				new Thread(new CnctDemo(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
