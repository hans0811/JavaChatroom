package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This class is used to transfer messages between server and client. This class
 * use a flag to distinguish different requests. Flag is the first element of
 * requests send by clients.
 * 
 * Flag = 0: Request for login. Flag = 1: Request for socket index. Flag = 2:
 * Request for communicating with another online client. Flag = 3: Request for
 * register. Flag = 4: Broadcast new socket list when a new client connect to
 * the server.
 * 
 * When replying client, server will send the corresponding flag as the first
 * element so that client can distinguish replies. It is ";" which is used by
 * both server and client to distinguish flag and requests/ replies.
 * 
 * @author AzoraChin
 *
 */
public class CnctDemo implements Runnable {
	public final String splitMark = ";";
	int flag = 0;
	PrintWriter pwtoclien = null;
//	Scanner keybordscanner = null;
	Scanner inScanner = null;
	Socket socket;

	public CnctDemo(Socket socket) {
		try {
			this.socket = socket;
			pwtoclien = new PrintWriter(socket.getOutputStream());
//			ServerDemo.socketList.add(socket);
//			ServerDemo.socket_index++;
			// ServerDemo.allSocketIndexList.add(ServerDemo.socket_index);
			// Broadcast a new socket list to all clients.
//			new Thread(new Broadcast(ServerDemo.socketList, 0)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
//			System.out.println(
//					"A new client whose socket index is " + ServerDemo.socket_index + " connects to the server.");
			inScanner = new Scanner(socket.getInputStream());
			// Wait for message from clients.
			while (inScanner.hasNextLine()) {
				String indata = inScanner.nextLine();
				System.out.println("Client: " + indata);
				String[] indataArray = indata.split(splitMark);
				flag = Integer.parseInt(indataArray[0]);
				switch (flag) {
				case 0:// Login
					TreeMap<Integer, Socket> user = new TreeMap<Integer, Socket>();
					String u_loginid = indataArray[1];
					String u_password = indataArray[2];
					boolean login_S = CnctSqlDemo.login(u_loginid, u_password);
					// If login successfully,
					if (login_S) {
						// Update the List<Map<socketIndex, socket>>
						ServerDemo.socket_index++;
//						user.put(ServerDemo.socket_index, socket);
//						ServerDemo.socketList.add(user);
						ServerDemo.index_socketPair.put(ServerDemo.socket_index, socket);
						ServerDemo.index_loginidPair.put(ServerDemo.socket_index, u_loginid);
						CnctSqlDemo.insertOnlineUser(ServerDemo.socket_index, u_loginid);
						// Broadcast adding a new online user to all online users.
//						new Thread(new Broadcast(ServerDemo.socket_index, u_loginid, ServerDemo.socketList,0)).start();
						new Thread(new Broadcast(ServerDemo.index_loginidPair,ServerDemo.index_socketPair,0)).start();
					}
					pwtoclien.println(flag + splitMark + login_S + splitMark + ServerDemo.socket_index + splitMark
							+ u_loginid);
					System.out.println("Server login: " + ServerDemo.socket_index + " " + u_loginid + " "
							+ login_S);
					break;
				case 1:// Acquire socket index
					pwtoclien.println(flag + splitMark + ServerDemo.socket_index);
					System.out.println(
							"Server acquire socket index: the current socket_index is " + ServerDemo.socket_index);
					break;
				case 2:// Send message to another client
					Socket socketTarget = ServerDemo.index_socketPair.get(Integer.parseInt(indataArray[3]));
					PrintWriter pwtoclienSpecific = new PrintWriter(socketTarget.getOutputStream());
					if (CnctSqlDemo.storeMsg(indataArray[1], indataArray[2], indataArray[3])) {
						pwtoclienSpecific.println(flag + splitMark + indataArray[2] + splitMark + indataArray[1]);
						pwtoclienSpecific.flush();
						System.out.println("Server communicate: send " + indataArray[2] + " from " + indataArray[1]
								+ " to " + indataArray[3]);
					}
					break;
				case 3:// Register
					pwtoclien.println(flag + splitMark + CnctSqlDemo.register(indataArray[1], indataArray[2],
							indataArray[3], Integer.parseInt(indataArray[4]), indataArray[5], indataArray[6]));
					System.out.println("Server register: " + CnctSqlDemo.register(indataArray[1], indataArray[2],
							indataArray[3], Integer.parseInt(indataArray[4]), indataArray[5], indataArray[6]));
					break;
				default:
					pwtoclien.flush();
					break;
				}
				pwtoclien.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pwtoclien.close();
			inScanner.close();
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
