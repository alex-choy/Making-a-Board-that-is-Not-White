package WhiteBoard;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerClass extends Thread {
	public ArrayList<ObjectOutputStream> outputs;
	public int port;
	Canvas canvas;
	public ServerClass(int port, Canvas c) {
		this.port = port;
		canvas = c;
	}
	public void run()
	{
		try 
		{
			ServerSocket listener = new ServerSocket(port);
			while(true)
			{
				Socket socket = listener.accept();
				ObjectOutputStream stream = (ObjectOutputStream) socket.getOutputStream();
				
				outputs.add(new ObjectOutputStream(socket.getOutputStream()));
				XMLEncoder xml = new XMLEncoder( socket.getOutputStream());
				for(DShape d : canvas.getList())
				{
					stream.writeObject(d.draw());
				}
				
				//ArrayList<DShape> x = controller.getObjects();
				//DShapeModel[] shapes = new DShapeModel[x.size()]; 
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
