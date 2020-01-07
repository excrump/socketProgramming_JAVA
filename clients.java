package odevim;

import java.io.*;
import java.net.*;

public class client {
	
	private static final String SERVER_IP = "127.0.0.1";
	private static int SERVER_PORT= 8080;

	public static void main(String[] args) throws Exception {
		
		Socket socket = new Socket(SERVER_IP, SERVER_PORT);		
		
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		while(true) {
		System.out.println("*****************");
		
		String command = keyboard.readLine();
		if (command.equals("cikis")) break;
		
		out.println(command);
		
		String serverResponse = input.readLine();
		
		System.out.println("Gelen cevap: "+ serverResponse);
		}
		
		socket.close();
		System.exit(0);
	}
}
