package WhiteBoard;

//TickerExample.java
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import javax.swing.*;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;
import java.io.*;
import java.net.*;

public class ConnectionStuff {

	private ClientHandler clientHandler;
	private ServerAccepter serverAccepter;
	// List of object streams to which we send data
	private java.util.List<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();
	boolean server = false;
	Canvas canvas;
	boolean pageOn = true;
	final String MOVE_TO_FRONT = "move to front";
	final String MOVE_TO_BACK = "move to back";
	final String ADD = "add";
	final String REMOVE = "remove";
	final String TRANSLATE = "move";
	final String COLOR = "color";
	final String CHANGE_TEXT = "change text";

	public ConnectionStuff(String type, String port, Canvas can) {
		canvas = can;

		if (type.equals("server")) {
			server = true;
		}
		if (server) {
			doServer(Integer.parseInt(port.trim()));
		} else {
			String[] connection = port.split(":");
			doClient(connection[0].trim(), Integer.parseInt(connection[1].trim()));
		}

	}

	// #############################################################################
	// Client Stuff
	// ############################################################################
	public void doClient(String host, int port) {
		clientHandler = new ClientHandler(host, port);
		clientHandler.start();
	}

	// Client runs this to handle incoming messages
	// (our client only uses the inputstream of the connection)
	private class ClientHandler extends Thread {
		private String name;
		private int port;

		ClientHandler(String name, int port) {
			this.name = name;
			this.port = port;
		}

		// Connect to the server, loop getting messages
		public void run() {
			try {
				// make connection to the server name/port
				Socket toServer = new Socket(name, port);
				// get input stream to read from server and wrap in object input
				// stream
				ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());
				boolean firstTime = true;
				// we could do this if we wanted to write to server in addition
				// to reading
				while (true) {
					// Get the xml string, decode to a Message object.
					// Blocks in readObject(), waiting for server to send
					// something.
					String xmlString = (String) in.readObject();
					XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
					PackagerClass objects = (PackagerClass) decoder.readObject();

					DShapeModel[] shape = objects.getShapes();
					String command = objects.getCommand();
					String color = objects.getColor();

					color = color.substring(2, 8);

					Color c = Color.web("#" + color);

					DShapeModel[] temp = new DShapeModel[1];

					temp[0] = shape[shape.length - 1];

					if (firstTime) {
						invokeToGUI(shape, ADD, objects.getIndex(), c);
						firstTime = false;
					} else {
						invokeToGUI(temp, command, objects.getIndex(), c);
					}
					decoder.close();
				}
			} catch (Exception ex) { // IOException and ClassNotFoundException
				ex.printStackTrace();
			}
			// Could null out client ptr.
			// Note that exception breaks out of the while loop,
			// thus ending the thread.
		}
	}

	// Given a message, puts that message in the local GUI.
	// Can be called by any thread.
	public void invokeToGUI(DShapeModel[] list, String command, int index, Color color) {

		Platform.runLater(new Runnable() {
			public void run() {

				for (DShapeModel d : list) {
					if (command.equals(ADD)) {
						canvas.addShape(d);
						canvas.draw();
					}

					else if (command.equals(MOVE_TO_FRONT)) {
						DShape shape;
						if (d instanceof DRectModel) {
							shape = new DRect((DRectModel) d);
						} else if (d instanceof DOvalModel) {
							shape = new DOval((DOvalModel) d);
						} else if (d instanceof DLineModel) {
							shape = new DLine((DLineModel) d);
						} else {
							shape = new DText((DTextModel) d);
						}
						canvas.move2Front(shape);
					} else if (command.equals((MOVE_TO_BACK))) {
						DShape shape;
						if (d instanceof DRectModel) {
							shape = new DRect((DRectModel) d);
						} else if (d instanceof DOvalModel) {
							shape = new DOval((DOvalModel) d);
						} else if (d instanceof DLineModel) {
							shape = new DLine((DLineModel) d);
						} else {
							shape = new DText((DTextModel) d);
						}
						canvas.move2Back(shape);
					} else if (command.equals(REMOVE)) {
						DShape shape = null;
						if (d instanceof DRectModel) {
							shape = new DRect((DRectModel) d);
						} else if (d instanceof DOvalModel) {
							shape = new DOval((DOvalModel) d);
						} else if (d instanceof DLineModel) {
							shape = new DLine((DLineModel) d);
						} else if (d instanceof DTextModel) {
							shape = new DText((DTextModel) d);
						}
						canvas.removeObject(shape);
					} else if (command.equals(COLOR)) {
						DShape shape = null;
						if (d instanceof DRectModel) {
							shape = new DRect((DRectModel) d);
						} else if (d instanceof DOvalModel) {
							shape = new DOval((DOvalModel) d);
						} else if (d instanceof DLineModel) {
							shape = new DLine((DLineModel) d);
						} else if (d instanceof DTextModel) {
							shape = new DText((DTextModel) d);
						}
						shape.getModel().setColor(color);
						canvas.removeObject(shape);
						canvas.addShape(shape.getModel());
						canvas.draw();
					} else if (command.equals(CHANGE_TEXT)) {
						DText text = null;
						if (d instanceof DTextModel) {
							boolean found = false;
							text = new DText((DTextModel) d);
							for (int i = 0; !found && i < canvas.getList().size(); i++) {
								Text listText = canvas.getThisText(text.getModel().getText().toString());
								if (text.equals(listText)) {
									found = true;
									text.getModel().setText(listText.toString());
									canvas.removeObject(text);
									canvas.addShape(text.getModel());
									canvas.draw();
								}
							}

						}
					}
				}
				canvas.draw();
			}
		});
	}
	// Sends a message to all of the outgoing streams.
	// Writing rarely blocks, so doing this on the swing thread is ok,
	// although could fork off a worker to do it.

	// #####################################################################
	// Server Stuff
	// #####################################################################

	// Starts the sever accepter to catch incoming client connections.
	// Wired to Server button.
	public void doServer(int port) {
		serverAccepter = new ServerAccepter(port);
		serverAccepter.start();
	}

	class ServerAccepter extends Thread {
		private int port;

		ServerAccepter(int port) {
			this.port = port;
		}

		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				while (pageOn) {
					Socket toClient = null;
					// this blocks, waiting for a Socket to the client
					toClient = serverSocket.accept();
					// Get an output stream to the client, and add it to
					// the list of outputs
					// (our server only uses the output stream of the
					// connection)
					addOutput(new ObjectOutputStream(toClient.getOutputStream()));

				}
				serverSocket.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public synchronized void sendRemote(DShapeModel[] shape, String command, int index, String color) {
		PackagerClass p = new PackagerClass();
		p.setCommand(command);
		p.setShapes(shape);
		p.setIndex(index);
		p.setColor(color);
		// Convert the message object into an xml string.
		OutputStream memStream = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(memStream);
		encoder.writeObject(p);
		encoder.close();
		String xmlString = memStream.toString();
		// Now write that xml string to all the clients.
		Iterator<ObjectOutputStream> it = outputs.iterator();
		while (it.hasNext()) {
			ObjectOutputStream out = it.next();
			try {
				out.writeObject(xmlString);
				out.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
				it.remove();
				// Cute use of iterator and exceptions --
				// drop that socket from list if have probs with it
			}
		}
	}

	// Adds an object stream to the list of outputs
	// (this and sendToOutputs() are synchronzied to avoid conflicts)
	public synchronized void addOutput(ObjectOutputStream out) {
		outputs.add(out);
	}

	public void setPage(boolean b) {
		pageOn = b;
	}
	// Server thread accepts incoming client connections

	// Runs a client handler to connect to a server.
	// Wired to Client button.

}