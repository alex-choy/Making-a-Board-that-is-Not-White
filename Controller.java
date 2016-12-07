package WhiteBoard;
import java.util.ArrayList;

import WhiteBoard.Whiteboard.TableInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Controller {
	ArrayList<DShape> list;
	Whiteboard w;
	Canvas canvas;
	TableView<TableInfo> table; // Saaj's Code
	public Controller(Canvas canvas, TableView<TableInfo> t) //Saaj's Code
	{
		list = new ArrayList<>();
		w = new Whiteboard();
		this.canvas = canvas;
		table = t; //Saaj's Code
	}
	
	//Saaj's Code start----------------------------------------------------------------------------------------------------------------------------
	public void setColor(Color value, DShape shape)
	{
		DShapeModel model = shape.getModel();
		model.setColor(value);
		canvas.getChildren().remove(shape);
		canvas.getChildren().add(shape.draw());
		System.out.println(value);
	}
	//Saaj's code end--------------------------------------------------------------------------------------------------------------------------------------
	public void addRectangle(Canvas canvas, DRect rect) //Saaj's code
	{
		DShapeModel model = rect.getModel();
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(model.getType(new DRect(new DRectModel()) )), model.getX(),model.getY(), model.getWidth(), model.getHeight());
		table.getItems().add(info);
		table.refresh();		
		list.add(0, rect);
		canvas.draw(list);
		System.out.println("Added Rectangle");
	}
	public void addEllipse(Canvas canvas, DOval oval) //Saaj's code
	{		
		DShapeModel model = oval.getModel();
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty("ellipse"), model.getX(), model.getY(), model.getWidth(), model.getHeight());
		table.getItems().add(info);
		//table.refresh();	
		list.add(0, oval);
		canvas.draw(list);
		System.out.println("Added Ellipse");
	}
	public void addText(String text, String f, Canvas canvas, DText t) //Saaj's Code
	{
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(t.getModel().getType(new DText(new DTextModel()) )), t.getModel().getX(), t.getModel().getY(), t.getModel().getWidth(), t.getModel().getHeight());
		table.getItems().add(info);
		table.refresh();
		list.add(0, t);
		System.out.println("Added Text: " + text );
	}
	public ArrayList<DShape> getObjects()
	{
		return list;
	}
	
	//Saaj's code-----------------------------------------------------
	public void removeObject(DShape d)
	{
		
		System.out.println("In the controller class:" + d +"\nlist size:" + list.size());
		list.remove(d);
		canvas.getChildren().remove(d);
		canvas.draw(list);
	}
	public void move2Back(DShape d)
	{
		DShape now = d;
		list.remove(d);
		list.add(now);
		canvas.draw(list);
		updateTable();
		
	}
	public void move2Front(DShape d)
	{
		DShape now = d;
		list.remove(d);
		list.add(0, now);
		canvas.draw(list);
		updateTable();
	}
	
	public void updateTable()
	{
		table.getItems().clear();
		for(DShape shape: list)
		{
			DShapeModel model = shape.getModel();
			String type = model.getType(shape);
			Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(type), model.getX(), model.getY(), model.getWidth(), model.getHeight());
			table.getItems().add(info);
		}
	}
	//Saaj's code end--------------------------------------------------
	public void addLine(Canvas canvas, DLine line)  //Saaj's code
	{
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty("line"), line.getModel().getX(), line.getModel().getY(), line.getModel().getWidth(), line.getModel().getHeight());
		table.getItems().add(info);
		table.refresh();
		list.add(0, line);
		canvas.draw(list);
		System.out.println("Added Line");
	}
	
	
}
