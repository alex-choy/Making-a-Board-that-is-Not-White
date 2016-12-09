package WhiteBoard;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import WhiteBoard.Whiteboard.TableInfo;
import javafx.scene.control.TableView;

// Client runs this to handle incoming messages   
// (our client only uses the inputstream of the connection)  
class ClientHandler extends Thread 
{ 
	Canvas c = null;
	private String name;
	private int port;
	TableView<TableInfo> table;
	Controller controller;
	 ClientHandler(String name, int port, Canvas canvas, TableView<TableInfo> t, Controller con)
	 {
		 this.name = name; 
		 this.port = port;
		 c = canvas;
		 table = t;
		 controller = con;
	 }
	// Connect to the server, loop getting messages         
	 public void run() 
	 {     
	 try
	 {              
		 // make connection to the server name/port              
		 Socket toServer = new Socket(name, port);                
		 // get input stream to read from server and wrap in object input stream
		 ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());              
		 System.out.println("client: connected!");            
		 // we could do this if we wanted to write to server in addition           
		 // to reading         
		 // out = new ObjectOutputStream(toServer.getOutputStream());            
		 while (true) 
	 	{                  
		 // Get the xml string, decode to a Message object.                   
		 // Blocks in readObject(), waiting for server to send something.
			 //String xmlString = (String) in.readObject(); 
			 byte[] input = (byte[]) in.readObject();
			 XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(input));                     
			 //Message message = (Message) decoder.readObject();
	         DShapeModel[] objects = (DShapeModel[]) decoder.readObject();
			 System.out.println("client: reads the objects. List size: " + objects.length);                  
	         invokeToGUI(objects);              
	         }              
		 }
		 catch (Exception ex) 
		 { // IOException and ClassNotFoundException          
			 ex.printStackTrace();   
			 
		 }          
		 // Could null out client ptr.           
		 // Note that exception breaks out of the while loop,          
		 // thus ending the thread.         
	}
	 public void invokeToGUI(DShapeModel[] d)
	 {
		 for(DShapeModel shape: d)
			{
				if(shape.getType().equals("rectangle"))
				{
					controller.getObjects().add(0, new DRect((DRectModel) shape));
				}
				else if(shape.getType().equals("oval"))
				{
					controller.getObjects().add(0, new DOval((DOvalModel) shape));
				}
				else if(shape.getType().equals("line"))
				{
					controller.getObjects().add(0, new DLine((DLineModel) shape));
				}
				else if(shape.getType().equals("text"))
				{
					controller.getObjects().add(0, new DText((DTextModel) shape));
				}
			}
		 c.draw(controller.getObjects(), table);
	 }
}
	 
