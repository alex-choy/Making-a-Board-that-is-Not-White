package WhiteBoard;

import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Whiteboard extends Application 
{

	   double orgSceneX, orgSceneY;
	   double orgTranslateX, orgTranslateY;
	   BorderPane pane;
	boolean drag = false;
	boolean resize = false;
	
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
	
	VBox vbox = null; //Saaj's change
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start(Stage stage)
	{
		Scene scene = new Scene(new Group());
		stage.setTitle("Whiteboard");
		stage.setHeight(610);
		stage.setWidth(1100);
		
		BorderPane pane = new BorderPane();
		canvas = new Canvas();
		
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
		objectInfo.setSpacing(5);		
		objectInfo.setPadding(new Insets(10, 10, 10, 10)); //saaj's code		
		objectInfo.getChildren().addAll(removeShape, moveToFront, moveToBack); //saaj's code		
		hideButtons(removeShape, moveToFront, moveToBack);  //Saaj's code
		
		Button colorPicker = new Button("Color");
		Button doneColorPicker = new Button("Done choosing color");
		ColorPicker cp = new ColorPicker();	//create a new ColorPicker
	
		TextField textBox = new TextField();
		textBox.setDisable(true);
		textBox.setVisible(false);
		TableView<TableInfo> table = new TableView();
		Controller controller = new Controller(canvas, table); //Saaj's code
		table.setPrefWidth(stage.getWidth());
		table.setPrefHeight(365);  //saaj's code
		
		nameColumn = new TableColumn<>("Name");
		nameColumn.setPrefWidth(stage.getWidth()/10);
		nameColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, SimpleStringProperty>("name"));

		
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
		//INITIALIZING OBJECTS
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
		list.add("Times New Roman");
		list.add("Calibri");
		list.add( "Arial");
        ChoiceBox dropDown = new ChoiceBox();
        dropDown.setVisible(false);
        dropDown.setDisable(true);
		dropDown.getItems().addAll(list);
		textBox.setPromptText("Enter text here");
		//rectangle button
		
		//BufferedImage image = (BufferedImage) createImage(3,3);
		
		addRect.setOnAction(new EventHandler()
				{
					public void handle(Event event) 
					{
						DRectModel model = new DRectModel();
						DRect rect = new DRect(model);
						controller.addRectangle( rect);	
					}
				});
		
		//oval button
		addOval.setOnAction(new EventHandler()
				{
					public void handle(Event e)
					{
						DOvalModel model = new DOvalModel();
						DOval oval = new DOval(model);
						controller.addEllipse( oval);
					}
				});
		
		//line button
		addLine.setOnAction(new EventHandler(){
			public void handle(Event e)
			{
				DLineModel model = new DLineModel();
				DLine line = new DLine(model);
				controller.addLine(line);
			}
		});
		
		//text button
		addText.setOnAction(new EventHandler(){
			public void handle(Event e)
			{
				//try	{
				DTextModel model = new DTextModel();
				DText text = new DText(model);
				controller.addText( text); //Saaj's code
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
							{if(focusedObject!= null)
								colorBox.getChildren().add(cp);  //Saaj's code		
							cp.setValue(focusedObject.getModel().getColor()); //Saaj's Code		
							doneColorPicker.setDisable(false); //Saaj's Code		
							doneColorPicker.setVisible(true); //Saaj's Code		
						}		
						else //Saaj's Code		
						{		
							System.out.println("Please choose an object to the the color of"); //Saaj's Code		
						}		
						
							}
						
					}
					
				);
		
		
		//done picking color button
		doneColorPicker.setOnAction(new EventHandler()
				{
					public void handle(Event event)
					{
						
						controller.setColor(cp.getValue(), focusedObject);
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
				  orgSceneX = event.getSceneX();
		          orgSceneY = event.getSceneY();
				double middleX =  event.getX();
				double middleY = event.getY();
				boolean obj = false;
				for (DShape d: controller.getObjects())
				{
					DShapeModel shape = d.getModel();
					if(middleX >= shape.getX() && middleX <= shape.getX() + shape.getWidth())
					{
						if (middleY >= shape.getY() && middleY <= shape.getY() + shape.getHeight())
						{
							if(d != focusedObject)
							{
								makeUnfocused(); //saaj's code
								focusedObject = d;
								if(focusedObject instanceof DText)
								{
									setUpTextInfo(textBox, dropDown);
								}
								else if(textBox.isVisible() == true)
								{
									removeTextInfo(textBox, dropDown);
								}
								obj = true;
								//System.out.println(focusedObject.toString());
								break;
							}
							else
							{
								removeTextInfo(textBox, dropDown);
								obj = false;
								break;
							}	
						}
					}
				}
				if (!obj)
				{
					removeTextInfo(textBox, dropDown);
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
				System.out.println(focusedObject);
				canvas.getChildren().remove(focusedObject);
				controller.removeObject(focusedObject);
				hideButtons(removeShape, moveToFront, moveToBack);
				//focusedObject = null;
				//knobs = null;
			}
		});
		
		moveToBack.setOnAction(new EventHandler(){
			public void handle(Event e)
			{
				controller.move2Back(focusedObject);
				makeUnfocused();
				hideButtons(removeShape, moveToFront, moveToBack);
				
			}
		});
		
		moveToFront.setOnAction(new EventHandler(){
			public void handle(Event e)
			{
				controller.move2Front(focusedObject);
				makeUnfocused();
				hideButtons(removeShape, moveToFront, moveToBack);
			}
		});
		
		
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent t) {
				if(focusedObject != null)
				{
						//controller.getObjects().remove(focusedObject);
			           int offsetX = (int) (t.getSceneX() - orgSceneX);
			           int offsetY = (int) (t.getSceneY() - orgSceneY);
			           DShapeModel model = focusedObject.getModel();
			           model.setX(model.getWidth()/2 + offsetX);
			           model.setY(model.getHeight()/2 + offsetY);
			           if (focusedObject instanceof DRect) 
						{
			        	   DRect rect = new DRect((DRectModel)model);
			        	   controller.getObjects().add(rect);
			        	   canvas.draw(controller.getObjects(), table);
			        	   focusedObject = rect;
						} 
						else if (focusedObject instanceof DOval) 
						{
				        	 DOval oval = new DOval((DOvalModel)model);
				        	 controller.getObjects().add(oval);
				        	   canvas.draw(controller.getObjects(), table);
				        	   focusedObject = oval;
						} 
						else if (focusedObject instanceof DLine) 
						{
				        	 DLine line = new DLine((DLineModel)model);

				        	 controller.getObjects().add(line);
				        	   canvas.draw(controller.getObjects(), table);
				        	   focusedObject = line;
						} 
						else if (focusedObject instanceof DText) 
						{
				        	 DText text = new DText((DTextModel)model);
				        	 controller.getObjects().add(text);
				        	   canvas.draw(controller.getObjects(), table);
				        	   focusedObject = text;
						}
			           //focusedObject.getModel().setX(offsetX + (focusedObject.getModel().getWidth()/2));
			           //focusedObject.getModel().setY(offsetY+ (focusedObject.getModel().getHeight()/2));
				}
			}
		});
		
		
		
		// FINISHING OFF THE OBJECTS
		buttonBox.getChildren().add(add);
		buttonBox.getChildren().add(addRect);
		buttonBox.getChildren().add(addOval);
		buttonBox.getChildren().add(addLine);
		buttonBox.getChildren().add(addText);
		
		colorBox.getChildren().add(colorPicker);
		colorBox.getChildren().add(doneColorPicker);

		textInfo.getChildren().addAll(textBox, dropDown);
		vbox.setPadding(new Insets(10, 10, 40, 10)); //saaj's code				
		vbox.getChildren().addAll(buttonBox, colorBox, textInfo, objectInfo, table);
		

		pane.setCenter(canvas);
		pane.setLeft(vbox);
		
		((Group) scene.getRoot()).getChildren().add(pane);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void makeFocused()
	{
		DShapeModel model = focusedObject.getModel();
		knobs = model.drawKnobs();
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
    
	public static void main(String[] args){
		launch(args);
	}

	public void modelChanged(DShapeModel model) {
		// TODO Auto-generated method stub
		
	}
	
	public void setUpTextInfo(TextField textBox, ChoiceBox dropDown)
	{
		textBox.setVisible(true);
		textBox.setDisable(false);
		dropDown.setVisible(true);
		dropDown.setDisable(false);
	}
	public void removeTextInfo(TextField textBox, ChoiceBox dropDown)
	{
		textBox.setVisible(false);
		textBox.setDisable(true);
		dropDown.setVisible(false);
		dropDown.setDisable(true);
	}
	
}