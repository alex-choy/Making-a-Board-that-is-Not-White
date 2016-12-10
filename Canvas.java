package WhiteBoard;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import WhiteBoard.Whiteboard.TableInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Canvas extends Pane implements Serializable
{
	ArrayList<DShape> list;
	TableView<TableInfo> table;
	public Canvas(TableView<TableInfo> t) 
	{
		super.setPrefSize(500, 500);
		super.setStyle("-fx-border-color: black");
		table = t;
		list = new ArrayList<>();
	}

	public void addShape(DShapeModel d) 
	{
		System.out.println(d);
		if(d instanceof DRectModel)
		{
			System.out.println("this is a rectangle");
			DRect rect = new DRect((DRectModel) d);
			list.add(0, rect);
			getChildren().add(rect.draw());
			Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(d.getType()), d.getX(),d.getY(), d.getWidth(), d.getHeight());
			table.getItems().add(info);
			table.refresh();	
		}
		else if(d instanceof DOvalModel)
		{
			DOval oval = new DOval((DOvalModel) d);
			list.add(0, oval);
			getChildren().add(oval.draw());
			Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(d.getType()), d.getX(),d.getY(), d.getWidth(), d.getHeight());
			table.getItems().add(info);
			table.refresh();	
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
	public int getIndexOfObject(DShape d)
	{
		int position = 0;
 	   for(DShape shape: list)
 	   {
 		   if(!d.equals(d))
 		   {
 			   position++;
 		   }
 		   else
 		   {
 			   break;
 		   }
 	   }
 	   if(position == list.size())
 	   {
 		   return -1;
 	   }
 	   return position;
	}
	
	public void move2Back(DShape d)
	{
		DShape now = d;
		list.remove(d);
		list.add(now);
		draw();
		//updateTable();
		
	}
	public void move2Front(DShape d)
	{
		DShape now = d;
		list.remove(d);
		list.add(0, now);
		draw();
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
	
	public void updateServer(ConnectionStuff server, DShapeModel[] model, String command)
	{
		
		server.sendRemote(model, command);
		//server.sendRemote(this);
		System.out.println("Added from Canvas");
	}
	
	
	public void removeObject(DShape d)
	{
		
		//System.out.println("In the controller class:" + d +"\nlist size:" + list.size());
		list.remove(d);
		draw();
		//canvas.getChildren().remove(d);
	}
	
	public void setColor(Color value, DShape shape)
	{
		DShapeModel model = shape.getModel();
		model.setColor(value);
		int position = getIndexOfObject(shape);
		list.set(position, shape);
		draw();
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
