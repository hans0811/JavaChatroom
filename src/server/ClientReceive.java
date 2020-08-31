package server;

import UI.ChatSingleController;
import UI.ChatWindowController;

import java.util.Scanner;

public class ClientReceive extends Thread {
	public final static String splitMark = ";";
	int flag = 0;
	Scanner inScanner = null;
//	static boolean login_Finish = false;
//	static boolean communicate_Finish = false;
//	static boolean acquireSocketIndex_Finish = false;

	public ClientReceive(Scanner inScanner) {
		this.inScanner = inScanner;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (inScanner.hasNextLine()) {
			String indata = inScanner.nextLine();
		//	System.out.println("Receive: " + indata);
			String[] indataArray = indata.split(splitMark);
			flag = Integer.parseInt(indataArray[0]);
			switch (flag) {
			case 0:// Login Receive
				ClientDemo.login_Reply = Boolean.parseBoolean(indataArray[1]);
				ClientDemo.socket_index = Integer.parseInt(indataArray[2]);
				ClientDemo.u_loginid = indataArray[3];
				ClientDemo.login_Finish = true;
		//		System.out.println("Login finish = "+ClientDemo.login_Finish);
				break;
			case 1:
				// System.out.println("AcquireSocketIndex msg: " + indataArray[1]);
				ClientDemo.acquireSocketIndex_Reply = Integer.parseInt(indataArray[1]);
				ClientDemo.acquireSocketIndex_Finish = true;
				break;
			case 2:// Communicate
				ChatSingleController chatSingleController = new ChatSingleController();
				chatSingleController.displayReply(indataArray[1], indataArray[2]);
				// String
				System.out.println("Receive new msg: " + indataArray[1]);
				break;
			case 3:
				ClientDemo.register_Reply = Boolean.parseBoolean(indataArray[1]);
				ClientDemo.register_Finish = true;
				break;
			case 4:// Broadcast
				int instruction = Integer.parseInt(indataArray[1]);
				switch (instruction) {
				case 0://Add a new online user into onlineUserList.
			//		ClientDemo.socketList = ;
					if(!indataArray[2].equals("END")) {
						int index = Integer.parseInt(indataArray[2]);
						String loginid = indataArray[3];
						ClientDemo.loginid_indexPair.put(loginid, index);
						ClientDemo.index_loginidPair.put(index, loginid);
						System.out.println("Broadcast instruction 0: " + indataArray[2]+" "+indataArray[3]);
					}else {
						System.out.println("Broadcast instruction 0: " + indataArray[2]);
						ClientDemo.updateOlineUser_Finish = true;
						ChatWindowController chatWindowController = new ChatWindowController();

						//if(chatWindowController.nameList)

						//chatWindowController.clear();
                        //chatWindowController.genNames();
						//chatWindowController.add();


					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
	}
}
