package WhiteBoard;
import java.util.ArrayList;

import WhiteBoard.Whiteboard.TableInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Controller {
	//ArrayList<DShape> list;
	Canvas canvas;
	ConnectionStuff server;
	TableView<TableInfo> table; // Saaj's Code
	public Controller(Canvas canvas, TableView<TableInfo> t, ConnectionStuff server) //Saaj's Code
	{
		//list = new ArrayList<>();
		this.canvas = canvas;
		this.server = server;
		table = t; //Saaj's Code
	}
	
	public void setColor(Color value, DShape shape)
	{
		DShapeModel model = shape.getModel();
		model.setColor(value);
		int position = canvas.getIndexOfObject(shape);
		canvas.getList().set(position, shape);
		canvas.draw();
		//canvas.getChildren().remove(shape);
		//canvas.getChildren().add(shape.draw());
		
		//System.out.println(value);
	}
	public void addRectangle( DRect rect){	
		//list.add(0, rect);
		canvas.draw();
	}
	public void addEllipse(DOval oval)
	{		
		//list.add(0, oval);
		canvas.draw();
	}
	public void addText(DText t) //Saaj's Code
	{
		DShapeModel model = t.getModel();
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(model.getType()), model.getX(), model.getY(), model.getWidth(), model.getHeight());
		//list.add(0, t);
		canvas.draw();
	}
	/*
	public ArrayList<DShape> getObjects()
	{
		return list;
	}*/
	
	public void addLine(DLine line)
	{
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty("line"), line.getModel().getX(), line.getModel().getY(), line.getModel().getWidth(), line.getModel().getHeight());
		table.getItems().add(info);
		table.refresh();
		//list.add(0, line);
		canvas.draw();
	}

	/**
	public void addEllipse(DShapeModel oval)
	{		
		DShapeModel model = oval;
		DOval who = new DOval((DOvalModel)model);
		
		
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty("ellipse"), model.getX(), model.getY(), model.getWidth(), model.getHeight());
		
		table.getItems().add(info);
		//table.refresh();	
		list.add(0, who);
		canvas.draw(list, table);
		System.out.println("Added Ellipse");
	}
	
	*/
	
	
	
	
	
	public void changeText(DText text, Font font, TextField textBox)
	{
		//list.remove(text);
		text.setText(textBox.getText());
		text.setFont(font);
		//list.add(0, text);
		canvas.draw();
	}
	
	
	public void refresh( DShape shaped)
	{		
		//list.remove(shaped);
		//list.add(0, shaped);
		System.out.println("refreshed");
		Whiteboard.TableInfo info = null;
		canvas.draw();
		/*list.add(shaped.draw());
		for(int i = 0; i<list.size();i++)
		{

				if (shaped instanceof DRectModel) 
				{
					DRect shape = new DRect((DRectModel)shaped);
					list.set(i, (DRect)shape);
					info = new TableInfo(new SimpleStringProperty("Rectangle"), shape.getModel().getX(), shape.getModel().getY(), shape.getModel().getWidth(), shape.getModel().getHeight());
					table.getItems().set(i, info);
				} 
				else if (shaped instanceof DOvalModel) 
				{
					DOval shape = new DOval((DOvalModel)shaped);
					list.set(i, (DOval)shape);
					info = new TableInfo(new SimpleStringProperty("Ellipse"), shape.getModel().getX(), shape.getModel().getY(), shape.getModel().getWidth(), shape.getModel().getHeight());
					table.getItems().set(i, info);
				} 
				else if (shaped instanceof DLineModel) 
				{
					DLine shape = new DLine((DLineModel)shaped);
					list.set(i, (DLine)shape);
					info = new TableInfo(new SimpleStringProperty("Line"), shape.getModel().getX(), shape.getModel().getY(), shape.getModel().getWidth(), shape.getModel().getHeight());
					table.getItems().set(i, info);
				} 
				else if (shaped instanceof DTextModel) 
				{
					DText shape = new DText((DTextModel)shaped);
						list.set(i, (DText)shape);
						info = new TableInfo(new SimpleStringProperty("Text"), shape.getModel().getX(), shape.getModel().getY(), shape.getModel().getWidth(), shape.getModel().getHeight());
						table.getItems().set(i, info);
				}
		}*/
		//table.refresh();
	}
	
	
}
