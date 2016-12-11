package WhiteBoard;

import java.io.Serializable;
import java.util.ArrayList;
import WhiteBoard.Whiteboard.TableInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Canvas extends Pane implements Serializable
{
	ArrayList<DShape> list;
	TableView<TableInfo> table;
	
	
	public Canvas(TableView<TableInfo> t) 
	{
		super.setPrefSize(500, 500);
		super.setStyle("-fx-border-color: black; -fx-background-color: white");
		table = t;
		list = new ArrayList<>();
	}

	public void addShape(DShapeModel d) 
	{
		//System.out.println(d);

		
		
		if(d instanceof DRectModel)
		{
			//System.out.println("this is a rectangle");
			DRect rect = new DRect((DRectModel) d);
			list.add(0, rect);
			getChildren().add(rect.draw());
			Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(d.getType()), d.getX(),d.getY(), d.getWidth(), d.getHeight());
			table.getItems().add(info);
			table.refresh();	
			
			System.out.println(getIndexOfObject(rect));
		}
		else if(d instanceof DOvalModel)
		{
			DOval oval = new DOval((DOvalModel) d);
			list.add(0, oval);
			getChildren().add(oval.draw());
			Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(d.getType()), d.getX(),d.getY(), d.getWidth(), d.getHeight());
			table.getItems().add(info);
			table.refresh();	
			
			System.out.println(getIndexOfObject(oval));

		}
		else if(d instanceof DLineModel)
		{
			DLine line = new DLine((DLineModel) d);
			list.add(0, line);
			getChildren().add(line.draw());
			Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(d.getType()), d.getX(),d.getY(), d.getWidth(), d.getHeight());
			table.getItems().add(info);
			table.refresh();	
		}
		else if(d instanceof DTextModel)
		{
			DText text = new DText((DTextModel) d);
			list.add(0, text);
			getChildren().add(text.draw());
			Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(d.getType()), d.getX(),d.getY(), d.getWidth(), d.getHeight());
			table.getItems().add(info);
			table.refresh();	
		}
		draw();
		Rectangle r = new Rectangle(super.getWidth(), super.getHeight());
		super.setClip(r);
	}
	/*
	public void dragMouse() 
	{
		super.setOnMouseDragged(new EventHandler()
				{
					public void handle(Event e)
					{
						
					}
				});
	}
	
	*/
	public void changeText(DText text, Font font, TextField textBox)
	{
		//list.remove(text);
		text.getModel().setText(textBox.getText());
		text.getModel().setFont(font);
		//list.add(0, text);
		draw();
	}
	
	public int getIndexOfObject(DShape d)
	{
		boolean found = false;
		int position = 0;
 	 
		int dX = d.getModel().getX();
		int dY = d.getModel().getY();
		int dWidth = d.getModel().getWidth();
		int dHeight = d.getModel().getWidth();
		
		for(int i = 0; !found && i < list.size(); i++){
			DShape current = list.get(i);
			int x = current.getModel().getX();
			int y = current.getModel().getY();
			int width = current.getModel().getWidth();
			int height = current.getModel().getWidth();
			
			if( d instanceof DRect && current instanceof DRect && dX == x && dY == y && dWidth == width && dHeight == height){
				position = i;
				found = true;
			}
			else if( d instanceof DOval && current instanceof DOval && dX == x && dY == y && dWidth == width && dHeight == height){
				position = i;
				found = true;
			}
			else if( d instanceof DLine && current instanceof DLine && dX == x && dY == y && dWidth == width && dHeight == height){
				position = i;
				found = true;
			}
			else if( d instanceof DText && current instanceof DText && dX == x && dY == y && dWidth == width && dHeight == height){
				position = i;
				found = true;
			}
		}
 	   
 	   if(found == false)
 	   {
 		   return -1;
 	   }
 	   return position;
	}
	
	public void move2Back(DShape d)
	{
		DShape now = d;
		removeObject(d);
		list.add(now);
		draw();
		//updateTable();
		
	}
	public void move2Front(DShape d)
	{
		DShape now = d;
		removeObject(d);
		list.add(0, now);
		draw();
	}
	
	public Text getThisText(String theText){
		Text t = null;
		
		for(int i = 0; i < list.size(); i++){
			DShape shape = list.get(i);
			if(shape instanceof DText){
			}
		}
		
		return t;
	}
	
	public void drawKnobs(Rectangle[] knobs){
		this.getChildren().addAll(knobs);
	}
	
	public ArrayList<DShape> getList()
	{
		return list;
	}
	
	public DShapeModel[] getListAsArray()
	{
		int index = 0;
		DShapeModel[] shapes = new DShapeModel[list.size()];
		for(int i = list.size() - 1; i >= 0; i--)
		{
			shapes[index] = list.get(i).getModel();
			index++;
		}
		return shapes;
	}
	
	public void updateServer(ConnectionStuff server, DShapeModel[] model, String command, int indexu)
	{
		if(server != null)
		{
			server.sendRemote(model, command, 0,list.get(0).getModel().getColor().toString()); //last param = color (string)
			//server.sendRemote(this);
			System.out.println("Added from Canvas");
		}
	}
	
	
	public void removeObject(DShape d)
	{
		boolean found = false;
		//System.out.println("In the controller class:" + d +"\nlist size:" + list.size());
		//list.remove(d);
		int dX = d.getModel().getX();
		int dY = d.getModel().getY();
		int dWidth = d.getModel().getWidth();
		int dHeight = d.getModel().getWidth();
		
		for(int i = 0; !found && i < list.size(); i++){
			DShape current = list.get(i);
			int x = current.getModel().getX();
			int y = current.getModel().getY();
			int width = current.getModel().getWidth();
			int height = current.getModel().getWidth();
			
			if( d instanceof DRect && current instanceof DRect && dX == x && dY == y && dWidth == width && dHeight == height){
				list.remove(current);
				found = true;
				System.out.print("Rectangle deleted");
			}
			else if( d instanceof DOval && current instanceof DOval && dX == x && dY == y && dWidth == width && dHeight == height){
				list.remove(current);
				found = true;
				System.out.print("Oval deleted");
			}
			else if( d instanceof DLine && current instanceof DLine && dX == x && dY == y && dWidth == width && dHeight == height){
				list.remove(current);
				found = true;
				System.out.print("Line deleted");
			}
			else if( d instanceof DText && current instanceof DText && dX == x && dY == y && dWidth == width && dHeight == height){
				list.remove(current);
				found = true;
				System.out.print("Text deleted");
			}
		}
		
		
		draw();
		//canvas.getChildren().remove(d);
	}
	
	public void setColor(Color value, DShape shape)
	{
		System.out.println("Inside setColor: " + value.toString());
		DShapeModel model = shape.getModel();
		model.setColor(value);
		removeObject(shape);
		list.add(0, shape);
		draw();
		//int position = getIndexOfObject(shape);
		//list.set(position, shape);
		//canvas.getChildren().remove(shape);
		//canvas.getChildren().add(shape.draw());
		
		//System.out.println(value);
	}
	
	public synchronized void draw()//ConnectionStuff server) 
	{		
		//System.out.println("In the canvas class " + list.size());
		//System.out.println(server);
		getChildren().clear();
		table.getItems().clear();
		//table.refresh();
		int position = 0;
		Shape[] shapes = new Shape[list.size()];
		for(int i = list.size()-1; i >=0; i--)
		{
			//System.out.println("Going through loop");
			DShape other = list.get(list.size()-i-1);
			DShapeModel model = other.getModel();
			shapes[i] = other.draw();
			position++;
			Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(model.getType()), model.getX(),model.getY(), model.getWidth(), model.getHeight());
			table.getItems().add(info);
			table.refresh();		
			DShape o = list.get(i);
			try{
				if(!getChildren().contains(o))
				{
					getChildren().add(o.draw());	
				}
			}
			catch(Exception e)
			{
				getChildren().remove(o);
				getChildren().add(o.draw());
				System.out.println("There is a problem here");
				//System.out.println(o.getModel().getColor());
				//System.out.println(Color.GRAY);
			}
			
			
		}
		
		//getChildren().clear();
		//getChildren().addAll(shapes);
		
		/*
		if(server != null)
		{
			//System.out.println("going into loop");
			DShapeModel[] shapes = getListAsArray();
			//updateServer(server, shapes);
		}
		
		*/
		Rectangle r = new Rectangle(super.getWidth(), super.getHeight());
		super.setClip(r);
		
	}

}
