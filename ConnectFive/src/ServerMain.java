import java.io.*;
import java.net.*;

public class ServerMain
{
	public static void main(String[] args)
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(8003);
		
			while(true)
			{
		
				Socket connectionToClient = serverSocket.accept();
				ObjectOutputStream os = new 
					ObjectOutputStream(connectionToClient.getOutputStream());
				ObjectInputStream is = new 
					ObjectInputStream(connectionToClient.getInputStream());
				
				Thread t = new Thread(new Servers_Listener(os,is));
				t.start();
			}
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
}