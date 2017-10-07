import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class WebServer {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Required arguments: port");
			return;
		}
		int port = Integer.parseInt(args[0]);
		ServerSocket serverSocket = new ServerSocket(port);
		//while(true) {
			Socket connectSocket = serverSocket.accept();
			System.out.println("hey");
			
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectSocket.getInputStream())); 
			String request = inFromClient.readLine();
			String[] requestParam = request.split(" ");
			String path = requestParam[1];
			path = path.substring(1);
			System.out.println(path);
			
			PrintWriter out = new PrintWriter(connectSocket.getOutputStream());
			File file = new File("index.html");
			if (!file.exists()) {
				System.out.println("no file");
			    out.println("HTTP/1.1 404");
			    out.println();// the file does not exists
			} else {
				System.out.println("yes file");
				out.println("HTTP/1.1 200 OK");
				out.println("Content-Type: html");
				out.println();
				FileReader fr = new FileReader(file);
				BufferedReader bfr = new BufferedReader(fr);
				String line;
				while ((line = bfr.readLine()) != null) {
					out.write(line);
					//System.out.println(line);
				}
				
				bfr.close();
				fr.close();
				out.close();
		        connectSocket.close();
		        System.out.println("closed everything");
			}
		//}
	}
}
