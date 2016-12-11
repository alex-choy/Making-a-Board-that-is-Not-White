package WhiteBoard;

import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Whiteboard extends Application 
{
	Menu file;
	MenuBar menu;
	MenuItem open;
	MenuItem save;
	MenuItem savePNG;
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	BorderPane pane;
	boolean drag;
	boolean resize;	
	boolean opened = false;
	ObservableList<TableInfo> data = FXCollections.observableArrayList();
	TableColumn<TableInfo, Double> xColumn;
	TableColumn<TableInfo, Double> yColumn;
	TableColumn<TableInfo, Double> widthColumn;
	TableColumn<TableInfo, Double> heightColumn;
	TableColumn<TableInfo, SimpleStringProperty> nameColumn;
	DShape focusedObject = null;
	Canvas canvas = null;
	Rectangle[] knobs = null;
	public int port;
	boolean isServer;
	VBox vbox = null; //Saaj's change
	ConnectionStuff server;
	
	public Whiteboard(int port, boolean isServer)
	{
		this.port = port;
		this.isServer = isServer;
		drag = false;
		resize = false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start(Stage stage)
	{
		Scene scene = new Scene(new Group());
		stage.setTitle("Whiteboard");
		stage.setHeight(610);
		stage.setWidth(1100);
		
		Stage opener = new Stage();
		opener.setTitle("File Explorer");
		
		BorderPane pane = new BorderPane();
		TableView<TableInfo> table = new TableView();
		canvas = new Canvas(table);
		file = new Menu("FILE");
		menu = new MenuBar();
		open = new MenuItem("OPEN");
		save  = new MenuItem("SAVE"); 
		savePNG = new MenuItem("Save as PNG");
		menu.getMenus().add(file);
		file.getItems().addAll(open,save, savePNG);
		
		
		//	CREATING BUTTONS AND OTHER OBJECTS
		
		HBox buttonBox = new HBox();
		vbox = new VBox();
		HBox colorBox = new HBox();
		HBox textInfo = new HBox();
		
		HBox objectInfo = new HBox();  //saaj's code
		
		buttonBox.setSpacing(5);
		buttonBox.setPadding(new Insets(10, 0, 0, 10));
		
		Text add = new Text("Add:");
		Button addRect = new Button("Rect");
		Button addOval = new Button("Oval");
		Button addLine = new Button("Line");
		Button addText = new Button("Text");
		Button removeShape = new Button("Remove this object"); //Saaj's code		
		Button moveToFront = new Button("Move to Front"); //Saaj's code		
		Button moveToBack = new Button("Move to Back"); //Saaj's code	
		Button changeText = new Button("Change Text");
		Button colorPicker = new Button("Color");
		Button doneColorPicker = new Button("Done choosing color");
		//Button save = new Button("Save");
		//Button open = new Button("Open");
		ColorPicker cp = new ColorPicker();	//create a new ColorPicker
	
		TextField textBox = new TextField();
		textBox.setDisable(true);
		textBox.setVisible(false);
		table.setPrefWidth(stage.getWidth());
		table.setPrefHeight(365);  //saaj's code
		FileChooser fileChooser = new FileChooser();
		//fileChooser.

		/*
		nameColumn = new TableColumn<>("Name");
		nameColumn.setPrefWidth(stage.getWidth()/10);
		nameColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, SimpleStringProperty>("name"));
		 */
		
		xColumn = new TableColumn<>("X");
		xColumn.setPrefWidth(stage.getWidth()/8.05); //saaj's code
		xColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("x"));
		
		yColumn = new TableColumn<>("Y");
		yColumn.setPrefWidth(stage.getWidth()/8.05);
		yColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("y"));
		
		widthColumn = new TableColumn<>("Width");
		widthColumn.setPrefWidth(stage.getWidth()/8.05);
		widthColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("width"));
		
		heightColumn = new TableColumn<>("Height");
		heightColumn.setPrefWidth(stage.getWidth()/8.05);
		heightColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("height"));
		
		table.getColumns().addAll(xColumn, yColumn, widthColumn, heightColumn);
		table.setMaxWidth(stage.getWidth()/2);
		//table.setPadding(new Insets(10, 10, 10, 10));
		
		
		//Server Set Up
		//###########################################################################################
		if(isServer)
		{
			server = new ConnectionStuff("server", port + "", canvas);
			//server.start();
			//ServerClass server = new ServerClass(canvas, table, controller);
		}		
		//#############################################################################################
		
		Controller controller = new Controller(canvas, table, server); //Saaj's code
		/*if(server != null)
		{
			server.setController(controller);
		}
		*/
			
		
		//INITIALIZING OBJECTS
		
		changeText.setVisible(false);
		changeText.setDisable(true);
		objectInfo.setSpacing(5);		
		objectInfo.setPadding(new Insets(10, 10, 10, 10)); //saaj's code		
		objectInfo.getChildren().addAll(removeShape, moveToFront, moveToBack); //saaj's code		
		hideButtons(removeShape, moveToFront, moveToBack);  //Saaj's code
		doneColorPicker.setDisable(true);
        doneColorPicker.setVisible(false);
        cp.setValue(Color.WHITE);				//initialize the color as white
        cp.setMinHeight(30);					//sets the minimum height to make the box bigger
        colorBox.setSpacing(5);
		colorBox.setPadding(new Insets(10, 0, 0, 10));
		textInfo.setSpacing(5);
		textInfo.setPadding(new Insets(10, 0, 0, 10));
       // ObservableList<String> listOfTexts = FXCollections.observableArrayList(,, );
		ArrayList<String> list = new ArrayList<>();
		List<String> fontList = javafx.scene.text.Font.getFamilies();
		for(String f: fontList)
		{
			list.add(f);
		}
		
        ChoiceBox dropDown = new ChoiceBox();
        dropDown.setVisible(false);
        dropDown.setDisable(true);
		dropDown.getItems().addAll(list);
		textBox.setPromptText("Enter text here");
		
		//open Button
		open.setOnAction(new EventHandler(){
			public void handle(Event e)
			{
				File input = fileChooser.showOpenDialog(opener);
				
				try
				{
					InputStream input1 = new FileInputStream(input);
					XMLDecoder xml = new XMLDecoder(input1);
					DShapeModel[] shapes = (DShapeModel[]) xml.readObject();
					canvas.getList().clear();
					for(DShapeModel d: shapes)
					{
						canvas.addShape(d);
						/*if(d.getType().equals("rectangle"))
						{
							controller.getObjects().add(0, new DRect((DRectModel) d));
						}
						else if(d.getType().equals("oval"))
						{
							controller.getObjects().add(0, new DOval((DOvalModel) d));
						}
						else if(d.getType().equals("line"))
						{
							controller.getObjects().add(0, new DLine((DLineModel) d));
						}
						else if(d.getType().equals("text"))
						{
							controller.getObjects().add(0, new DText((DTextModel) d));
						}*/
					}
					input1.close();
					//canvas.draw(controller.getObjects(), table, server);
				}
				catch(Exception exception)
				{
					System.out.println("Can not find file " + exception);
				}
			}
		});
	
		//save button
		save.setOnAction(new EventHandler(){
			public void handle(Event e)
			{ 
				
				File input = fileChooser.showSaveDialog(opener);
				
				try 
				{
					XMLEncoder xml = new XMLEncoder( new BufferedOutputStream(new FileOutputStream(input)));
					ArrayList<DShape> x = canvas.getList();
					DShapeModel[] shapes = new DShapeModel[x.size()];
					int index = 0;
					for(int i = x.size() - 1; i >= 0; i--)
					{
						shapes[index] = x.get(i).getModel();
						index++;
					}
					xml.writeObject(shapes);
					xml.close();
					System.out.println("File has been created");
				} 
				catch (Exception e1) 
				{
					System.out.println("File System did not work");
				}
			}
		});
		
		savePNG.setOnAction(new EventHandler(){

			@Override
			public void handle(Event event) {

				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
				
				File file = fileChooser.showSaveDialog(null);
				if(file != null){
					try{
						WritableImage png = canvas.snapshot(new SnapshotParameters(), null);
						//snapshot(null, png);
						RenderedImage rendered = SwingFXUtils.fromFXImage(png, null);
						ImageIO.write(rendered, "png", file);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			
		});
		
		//rectangle button		
		addRect.setOnAction(new EventHandler()
				{
					public void handle(Event event) 
					{
						DRectModel model = new DRectModel();
						canvas.addShape(model);
						canvas.updateServer(server, canvas.getListAsArray(), "add");
						//DRect rect = new DRect(model);
						//controller.addRectangle( rect);	
						
						/*if(isServer)
						{
							 doSend(model);
						}*/
					}
				});
		
		//oval button
		addOval.setOnAction(new EventHandler()
				{
					public void handle(Event e)
					{
						DOvalModel model = new DOvalModel();
						canvas.addShape(model);
						canvas.updateServer(server, canvas.getListAsArray(), "add");
						//DOval oval = new DOval(model);
						//controller.addEllipse( oval);
						
						/*if (isServer)
						{
							 doSend(model);
						}*/
					}
				});
		
		//line button
		addLine.setOnAction(new EventHandler(){
			public void handle(Event e)
			{
				DLineModel model = new DLineModel(canvas);
				canvas.addShape(model);
				canvas.updateServer(server, canvas.getListAsArray(), "add");
				//DLine line = new DLine(model);
				//controller.addLine(line);
				
				/*if (isServer)
				{
					 doSend(model);
				}*/
			}
		});
		
		//text button
		addText.setOnAction(new EventHandler(){
			public void handle(Event e)
			{
				//try	{
				DTextModel model = new DTextModel();
				canvas.addShape(model);
				canvas.updateServer(server, canvas.getListAsArray(), "add");
				//DText text = new DText(model);
				//controller.addText( text); //Saaj's code
				
				/*if(isServer)
				{
					 doSend(model);
				}	*/
			}
				//catch(Exception exception){
					//System.out.println("Something is wrong. Please check to make sure that you have written something in the text box and have chosen a font");
				//}
				//vbox.getChildren().add(t);
			
		});

		//ColorPicker button
		colorPicker.setOnAction(new EventHandler()
				{
					
					public void handle(Event arg0) {
						
							if (!colorBox.getChildren().contains(cp))
							{
								if(focusedObject!= null)
								{
									colorBox.getChildren().add(cp);  //Saaj's code		
									cp.setValue(focusedObject.getModel().getColor()); //Saaj's Code		
									doneColorPicker.setDisable(false); //Saaj's Code		
									doneColorPicker.setVisible(true); //Saaj's Code	
								}
						}		
						else //Saaj's Code		
						{		
							System.out.println("Please choose an object to the the color of"); //Saaj's Code		
						}		
						
							}
						
					}
					
				);
		
		
		changeText.setOnAction(new EventHandler(){
			@Override
			public void handle(Event arg0) {
				try{
					String f = list.get(dropDown.getSelectionModel().getSelectedIndex());
					Font font = new Font(f, 12);
					((DText) focusedObject).setText(textBox.getText());
					((DText) focusedObject).setFont(font);
					controller.changeText((DText)focusedObject, font, textBox);
					
					
				}
				catch(Exception e){
					System.out.println("Error, fix this");
				}
				
			}
			
		});
		
		
		//done picking color button
		doneColorPicker.setOnAction(new EventHandler()
				{
					public void handle(Event event)
					{
						canvas.setColor(cp.getValue(), focusedObject);
						//canvas.draw();//server);
						if(isServer)
						{
							canvas.updateServer(server, canvas.getListAsArray(), "color");
						}
						colorBox.getChildren().removeAll(cp);
						doneColorPicker.setVisible(false);
						doneColorPicker.setDisable(true);
						
					}
				});
		
		//Adding in the clicking component
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			public void handle(MouseEvent event)
			{
				drag = false;
				resize = false;
				orgSceneX = event.getSceneX();
		        orgSceneY = event.getSceneY();
				double middleX =  event.getX();
				double middleY = event.getY();
				boolean obj = false;
				for (DShape d: canvas.getList())
				{
					DShapeModel shape = d.getModel();
					if(middleX >= shape.getX() +4.5 && middleX <= shape.getX() + shape.getWidth() - 4.5)					
					{
						if (middleY >= shape.getY() +4.5 && middleY <= shape.getY() + shape.getHeight() - 4.5)				
						{
							//if(d != focusedObject)
							//{
								makeUnfocused(); //saaj's code
								focusedObject = d;
								//System.out.println("This is the current model X: " + focusedObject.getModel().getX());
								//System.out.println("This is the current model X: " + focusedObject.getModel().getX());

								//controller.getObjects().remove(focusedObject);
								//controller.getObjects().add(0, focusedObject);
								//canvas.draw(controller.getObjects(), table, server);
								if(focusedObject instanceof DText)
								{
									setUpTextInfo(textBox, dropDown, changeText);
								}
								else if(textBox.isVisible() == true)
								{
									removeTextInfo(textBox, dropDown, changeText);
								}
								obj = true;
								//System.out.println(focusedObject.toString());
								break;
							//}
							//else
							//{
							//	removeTextInfo(textBox, dropDown, changeText);
							//	obj = false;
							//	break;
							//}	
						}
					}
				}
				if (!obj)
				{
					removeTextInfo(textBox, dropDown, changeText);
					makeUnfocused(); //Saaj's Code
					hideButtons(removeShape, moveToFront, moveToBack); //Saaj's code
				}
				if (focusedObject != null)
				{
					makeFocused();
					setUpButtons(removeShape, moveToBack, moveToFront); //saaj's code;
				}
				
			}
			
		});
		
		removeShape.setOnAction(new EventHandler(){
			
			@Override
			public void handle(Event event) 
			{
				//System.out.println(focusedObject);
				//canvas.getChildren().remove(focusedObject);
				canvas.removeObject(focusedObject);
				//canvas.draw();//server);
				if(isServer)
				{
					canvas.updateServer(server, canvas.getListAsArray(), "remove");
				}
				hideButtons(removeShape, moveToFront, moveToBack);
				//focusedObject = null;
				//knobs = null;
			}
		});
		
		moveToBack.setOnAction(new EventHandler(){
			public void handle(Event e)
			{
				canvas.move2Back(focusedObject);
				//canvas.draw();//server);
				if(isServer)
				{
					canvas.updateServer(server, canvas.getListAsArray(), "move to back");
				}
				makeUnfocused();
				hideButtons(removeShape, moveToFront, moveToBack);
				
			}
		});
				
		moveToFront.setOnAction(new EventHandler(){
			public void handle(Event e)
			{
				canvas.move2Front(focusedObject);
				//canvas.draw();//server);
				if(isServer)
				{
					canvas.updateServer(server, canvas.getListAsArray(), "move to front");
				}
				makeUnfocused();
				hideButtons(removeShape, moveToFront, moveToBack);
			}
		});
		
		
		/*  Another Way to drag a object
		canvas.setOnDragDetected(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e)
			{
				 Dragboard db = canvas.startDragAndDrop(TransferMode.ANY);
				 ClipboardContent clip = new ClipboardContent();
				// clip.put
				// db.setContent(content)
				 System.out.println("Drag board");
			}
		});
		*/
		
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent t) 
			{
				drag = false;
				resize = false;

				if(focusedObject != null)
				{
					double middleX =  t.getX();
					double middleY = t.getY();
					DShapeModel model = focusedObject.getModel();
					for (DShape d: canvas.getList())
					{
						
						DShapeModel shape = d.getModel();
						if(middleX >= shape.getX() +4.5 && middleX <= shape.getX() + shape.getWidth() - 4.5)
						{
							if (middleY >= shape.getY() +4.5 && middleY <= shape.getY() + shape.getHeight() - 4.5)
							{
								drag = true;
							}
						}
					}
					for(Rectangle knob : knobs)
					{
						if(middleX >= knob.getX()  && middleX <= knob.getX() + knob.getWidth())
						{
							if(middleY >= knob.getY()  && middleY <= knob.getY() + knob.getHeight())
							{
								resize = true;
							}
						}
					}
					if(resize)
					{

						//knob 0
						if(middleX >= knobs[0].getX()  && middleX <= knobs[0].getX() + knobs[0].getWidth())
						{
							knobs[0].setX(middleX- (knobs[0].getWidth()/2));
							knobs[1].setX(middleX- (knobs[0].getWidth()/2));
							focusedObject.getModel().setWidth((int)(knobs[2].getX() - knobs[0].getX()));
						}
						if(middleY >= knobs[0].getY()  && middleY <= knobs[0].getY() + knobs[0].getHeight())
						{						
							knobs[0].setY(middleY- knobs[0].getHeight()/2);
							knobs[2].setY(middleY- knobs[0].getWidth()/2);
							focusedObject.getModel().setHeight((int)(knobs[1].getY() - knobs[0].getY()));
						}
						//knob 1
						 if(middleX >= knobs[1].getX()  && middleX <= knobs[1].getX() + knobs[1].getWidth())
							{
								knobs[1].setX(middleX- knobs[1].getWidth()/2);
								knobs[0].setX(middleX- knobs[1].getWidth()/2);
								focusedObject.getModel().setWidth((int)(knobs[3].getX() - knobs[1].getX()));
							}
						 if(middleY >= knobs[1].getY()  && middleY <= knobs[1].getY() + knobs[1].getHeight())
							{
								
								knobs[1].setY(middleY- knobs[1].getHeight()/2);
								knobs[3].setY(middleY- knobs[1].getHeight()/2);
								focusedObject.getModel().setHeight((int)(knobs[1].getY() - knobs[0].getY()));
							}
						//knob 2
						 if(middleX >= knobs[2].getX()  && middleX <= knobs[2].getX() + knobs[2].getWidth())
							{
								knobs[2].setX(middleX- knobs[2].getWidth()/2);
								knobs[3].setX(middleX- knobs[2].getWidth()/2);
								focusedObject.getModel().setWidth((int)(knobs[2].getX() - knobs[0].getX()));
							}
						 if(middleY >= knobs[2].getY()  && middleY <= knobs[2].getY() + knobs[2].getHeight())
							{
								
								knobs[2].setY(middleY- knobs[2].getHeight()/2);
								knobs[0].setY(middleY- knobs[0].getHeight()/2);
								focusedObject.getModel().setHeight((int)(knobs[3].getY() - knobs[2].getY()));
							}
						 canvas.draw();//server);
						 if(isServer)
						 {
							 canvas.updateServer(server, canvas.getListAsArray(), "move");
						 }
					}
			           
					if(focusedObject != null)
					{
				        int offsetX = (int) (middleX - (focusedObject.getModel().getWidth()/2) );
				        int offsetY = (int) (middleY - (focusedObject.getModel().getHeight()/2));
				        model.setX(offsetX);
				        model.setY(offsetY);
				        int position = canvas.getIndexOfObject(focusedObject);
				        canvas.removeObject(focusedObject);
				        canvas.getList().add(position, focusedObject);
				        //canvas.getList().set(position, focusedObject);
				        focusedObject.setModel(model);
				        canvas.draw();//server);
				        if(isServer)
				        {
				        	canvas.updateServer(server, canvas.getListAsArray(), "move");
				        }
				        // DShapeModel model = new DShapeModel(focusedObject.getModel().getWidth()/2 + offsetX, focusedObject.getModel().getHeight()/2 + offsetY, focusedObject.getModel().getWidth(), focusedObject.getModel().getHeight(), focusedObject.getModel().getColor());
			          /* if (focusedObject instanceof DRect) 
						{
			        	   //((DRect) focusedObject).changeX(offsetX);
			        	   //((DRect) focusedObject).changeY(offsetY);
			        	   model.setX(offsetX);
			        	   model.setY(offsetY);
			        	   int position = 0;
			        	   for(DShape d: canvas.getList())
			        	   {
			        		   if(!d.equals(focusedObject))
			        		   {
			        			   position++;
			        		   }
			        		   else
			        		   {
			        			   break;
			        		   }
			        	   }
			        	   //System.out.println("position = " + position);
			        	   
			        	   canvas.getList().set(position, focusedObject);
			        	   focusedObject.setModel(model);
			        	   //DRect rect = new DRect((DRectModel)model);
			        	   //DRect rect = new DRect(new DRectModel(focusedObject.getModel().getWidth()/2 + offsetX, focusedObject.getModel().getHeight()/2 + offsetY, focusedObject.getModel().getWidth(), focusedObject.getModel().getHeight(), focusedObject.getModel().getColor()));
			        	   //controller.getObjects().add(0, rect);
			        	   //controller.getObjects().add(0, focusedObject);
			        	   canvas.draw(server);
			        	   //focusedObject = rect;
			        	   //System.out.println("Current X: " +focusedObject.getModel().getX() + " Current Y: " + focusedObject.getModel().getY());
						} 
						else if (focusedObject instanceof DOval) 
						{
							model.setX(offsetX);
				        	model.setY(offsetY);
				        	int position = 0;
				        	   for(DShape d: canvas.getList())
				        	   {
				        		   if(!d.equals(focusedObject))
				        		   {
				        			   position++;
				        		   }
				        		   else
				        		   {
				        			   break;
				        		   }
				        	   }
				        	   canvas.getList().set(position, focusedObject);
				        	   focusedObject.setModel(model);
				        	   canvas.draw(server);
				        	   //System.out.println("Current X: " +focusedObject.getModel().getX() + " Current Y: " + focusedObject.getModel().getY());
							
				        	//old method before move
				        	//DOval oval = new DOval((DOvalModel)model);
				        	//controller.getObjects().add(0, oval);
				        	//canvas.draw(controller.getObjects(), table);
				        	//focusedObject = oval;
						} 
						else if (focusedObject instanceof DLine) 
						{
							model.setX(offsetX);
				        	   model.setY(offsetY);
				        	   int position = 0;
				        	   for(DShape d: canvas.getList())
				        	   {
				        		   if(!d.equals(focusedObject))
				        		   {
				        			   position++;
				        		   }
				        		   else
				        		   {
				        			   break;
				        		   }
				        	   }
				        	   canvas.getList().set(position, focusedObject);
				        	   focusedObject.setModel(model);
=				        	   canvas.draw(server);
				        	   //System.out.println("Current X: " +focusedObject.getModel().getX() + " Current Y: " + focusedObject.getModel().getY());
							
							//old method after this
				        	// DLine line = new DLine((DLineModel)model);
				        	// controller.getObjects().add(0, line);
				        	//   canvas.draw(controller.getObjects(), table);
				        	//   focusedObject = line;
						} 
						else if (focusedObject instanceof DText) 
						{
							model.setX(offsetX);
				        	   model.setY(offsetY);
				        	   int position = 0;
				        	   for(DShape d: canvas.getList())
				        	   {
				        		   if(!d.equals(focusedObject))
				        		   {
				        			   position++;
				        		   }
				        		   else
				        		   {
				        			   break;
				        		   }
				        	   }
				        	   canvas.getList().set(position, focusedObject);
				        	   focusedObject.setModel(model);
				        	   canvas.draw(server);
				        	   //System.out.println("Current X: " +focusedObject.getModel().getX() + " Current Y: " + focusedObject.getModel().getY());
							
							//old method after this
				        	// DText text = new DText((DTextModel)model);
				        	// controller.getObjects().add(0, text);
				        	//   canvas.draw(controller.getObjects(), table);
				        	//   focusedObject = text;
						}
			           */
					}
			           //focusedObject.getModel().setX(offsetX + (focusedObject.getModel().getWidth()/2));
			           //focusedObject.getModel().setY(offsetY+ (focusedObject.getModel().getHeight()/2));
				}
			}
		});
		
		
		
		// FINISHING OFF THE OBJECTS
		buttonBox.getChildren().addAll(add, addRect, addOval, addLine, addText);
		colorBox.getChildren().addAll(colorPicker, doneColorPicker);
		textInfo.getChildren().addAll(textBox, dropDown, changeText);
		
		vbox.setPadding(new Insets(10, 10, 40, 10)); //saaj's code				
		vbox.getChildren().addAll(menu, buttonBox, colorBox, textInfo, objectInfo, table);
		
		pane.setCenter(canvas);
		pane.setLeft(vbox);
		
		((Group) scene.getRoot()).getChildren().add(pane);
		if (server != null)
		{
			stage.setOnCloseRequest(new EventHandler(){
				public void handle(Event e)
				{
					server.setPage(false);
				}
			});
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public void makeFocused()
	{
		
		knobs = focusedObject.getModel().drawKnobs();
		canvas.getChildren().addAll(knobs);
		
	}
	
	//Saaj's Method------------------------------------------------------------------------------------------------------------------------------------------------		
		public void setUpButtons(Button remove, Button back, Button front)		
		{		
			remove.setVisible(true);		
			back.setVisible(true);		
			front.setVisible(true);		
			remove.setDisable(false);		
			back.setDisable(false);		
			front.setDisable(false);		
		}		
				
		public void makeUnfocused()		
		{		
			if (knobs != null)		
			{		
				canvas.getChildren().removeAll(knobs);		
				knobs = null;		
			}		
			focusedObject = null;		
		}		
				
		public void hideButtons(Button remove, Button back, Button front)		
		{		
			remove.setDisable(true);		
			back.setDisable(true);		
			front.setDisable(true);		
			remove.setVisible(false);		
			back.setVisible(false);		
			front.setVisible(false);		
		}		
		//Saaj's Method End--------------------------------------------------------------------------------------------------------------------------------------------
	/*public void updateTable(ArrayList<Object> list, TableView table)
	{
		//table.getItems().addAll(10, 10, 10, 10);
		table.getColumns().clear();
		table.setItems(data);
		table.getColumns().addAll(xColumn, yColumn, widthColumn, heightColumn);
		//table.refresh();
		/*int i = 0;
		for(Object e: list)
		{
			TableColumn x = (TableColumn) table.getColumns().get(0);
			x.getText();
		}
		
	}*/
	

    public static class TableInfo {
        StringProperty value;
        double x;
        double w;
        double y;
        double h;

        TableInfo(SimpleStringProperty v, double d, double e, double f, double g) {
            this.value = v;
            this.x = d;
            this.y = e;
            this.w = f;
            this.h =g;
        }
        public String getValue() {
            return value.get();
        }

        public double getX() {
            return x;
        }
        public void setX(double x)
        {
        	this.x = x;
        }
        public double getY() {
            return y;
        }
        public void setY(double y)
        {
        	this.y = y;
        }
        public double getWidth() {
            return w;
        }
        public void setWidth(double w)
        {
        	this.w = w;
        }
        public double getHeight() {
            return h;
        }
        public void setHeight(double h)
        {
        	this.h = h;
        }
    }
    /*
	public static void main(String[] args){
		launch(args);
	}
     */
	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	}
	
	public void setUpTextInfo(TextField textBox, ChoiceBox dropDown, Button change)
	{
		textBox.setVisible(true);
		textBox.setDisable(false);
		dropDown.setVisible(true);
		dropDown.setDisable(false);
		change.setVisible(true);
		change.setDisable(false);
	}
	public void removeTextInfo(TextField textBox, ChoiceBox dropDown, Button change)
	{
		textBox.setVisible(false);
		textBox.setDisable(true);
		dropDown.setVisible(false);
		dropDown.setDisable(true);
		change.setVisible(false);
		change.setDisable(true);
	}
	public void  doSend(DShapeModel shape)
	{
		//canvas.updateServer(server, shape);
	}
	/*
	public Whiteboard getWhiteboard()
	{
		return this;
	}
	*/
}