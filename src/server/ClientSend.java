package server;

import java.io.PrintWriter;

public class ClientSend implements Runnable {
	PrintWriter pwtoserver = null;
	String msg = "";
	String senderSocketIndex = "";
	String receiverSocketIndex = "";
	int flag = 0;
	public ClientSend(String msg, PrintWriter pwtoserver) {
		this.msg = msg;
		this.pwtoserver = pwtoserver;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		boolean running = true;
		while (true) {
			if (null != msg && !msg.equals("")) {
				pwtoserver.println(msg);
				pwtoserver.flush();
		//		System.out.println("Client send: "+msg);
				msg = "";
			}
		}
	}
}
