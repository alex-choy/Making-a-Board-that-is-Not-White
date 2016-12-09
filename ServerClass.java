package WhiteBoard;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

// Client runs this to handle incoming messages   
// (our client only uses the inputstream of the connection)  
class ClientHandler extends Thread 
{ 
	private String name;
	private int port;
	 ClientHandler(String name, int port)
	 {
		 this.name = name; 
		 this.port = port;
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
			 String xmlString = (String) in.readObject();                
			 XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));                     
			 //Message message = (Message) decoder.readObject();
	         DShapeModel[] objects = (DShapeModel[]) decoder.readObject();
			 System.out.println("client: reads the objects. List size: " + objects.length);                  
	         invokeToGUI(message);              
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
}
	 
