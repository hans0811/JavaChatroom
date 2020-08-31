package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class is used to broadcast message to members in the socketList. The
 * message is decided by instruction. Instruction = 0: There is a new client
 * connected to the server. Broadcast the new online list to every clients.
 * 
 * Instruction = 0: Add a online user into onlineUserList. Instruction = 1:
 * Delete a online user from onlineUserList. Instruction = 2: Group chat.
 * 
 * @author AzoraChin
 *
 */
public class Broadcast implements Runnable {
	public final String splitMark = ";";
//	TreeMap<Integer, Socket> allSocketList = new TreeMap<Integer, Socket>();
	TreeMap<Integer, Socket> groupSocketList = new TreeMap<Integer, Socket>();
	public static TreeMap<Integer, Socket> index_socketPair = new TreeMap<Integer, Socket>();
	public static TreeMap<Integer, String> index_loginidPair = new TreeMap<Integer, String>();
//	ArrayList<TreeMap<Integer, Socket>> socketList = new ArrayList<TreeMap<Integer, Socket>>();
	int instruction = 0;
	int flag = 4;

	int socket_index = 0;
	String u_loginid = "";

	/**
	 * This constructor is designed for add or delete a user from current
	 * onlineUserList.
	 * 
	 * @param user         The pair of socket_index and socket which should be added
	 *                     or deleted.
	 * @param insctruction 0/1
	 */
	public Broadcast(TreeMap<Integer, String> index_loginidPair, TreeMap<Integer, Socket> index_socketPair,
			int insctruction) {
		this.index_loginidPair = index_loginidPair;
		this.index_socketPair = index_socketPair;
		this.instruction = insctruction;
	}

	/**
	 * This constructor is designed for group chat.
	 * 
	 * @param socketList   All socket_index and socket pairs in the group.
	 * @param insctruction 2
	 */
	public Broadcast(TreeMap<Integer, Socket> groupSocketList, int insctruction) {
		this.groupSocketList = groupSocketList;
		this.instruction = insctruction;
	}

	public void run() {
		switch (instruction) {
		case 0:// Add a online user into onlineList.
			addOnlineUser();// ??????update?????????
//			pwtoclien.println(flag+";" + String.valueOf(allSocketIndex));
			break;

		default:
			break;
		}
	}

	public void addOnlineUser() {
		Set<Map.Entry<Integer, Socket>> ISEntries = index_socketPair.entrySet();
		Set<Map.Entry<Integer, String>> ILEntries = index_loginidPair.entrySet();
		int size = index_loginidPair.size();
		ISEntries.forEach((ISEntry) -> {
			try {
				Socket socket = ISEntry.getValue();
				PrintWriter pwtoclien;
				pwtoclien = new PrintWriter(socket.getOutputStream());
				ILEntries.forEach((ILEntry) -> {
					int index = ILEntry.getKey();
					String loginid = ILEntry.getValue();
					pwtoclien.println(flag + splitMark + instruction + splitMark + index + splitMark + loginid
							+ splitMark + size);
//					pwtoclien.flush();
				});
				pwtoclien.println(flag + splitMark + instruction + splitMark +"END");
				pwtoclien.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		System.out.println("Server broadcast: Successfully.");
	}
}
