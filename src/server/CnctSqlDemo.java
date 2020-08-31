package server;

public class CnctSqlDemo {
	public static boolean login(String u_id, String u_password) {
		return true;
	}

	public static boolean register(String u_name, String u_email, String u_phonenum, int u_gender, String u_username,
			String u_password) {
		return true;
	}

	public static boolean storeMsg(String sender, String msg, String receiver) {
		return true;
	}

	/**
	 * The cleanOnlineList method is used to remove all online user from the
	 * onlineUserTable. This method will be called when the server starts.
	 * 
	 * @return Clean successfully or not.
	 */
	public static boolean cleanOnlineUser() {
		return true;
	}

	/**
	 * The insertOnlineUser method is used to insert a new online user into the
	 * onlineUserTable. This method will be called when a new client connects to the
	 * server.
	 * 
	 * @return Insert successfully or not.
	 */
	public static boolean insertOnlineUser(int socket_index, String u_loginid) {
		return true;
	}

	/**
	 * The deleteOnlineUser method is used to delete a new online user from the
	 * onlineUserTable. This method will be called when a client logout.
	 * 
	 * @return Delete successfully or not.
	 */
	public static boolean deleteOnlineUser(int socket_index, String u_loginid) {
		return true;
	}
}
