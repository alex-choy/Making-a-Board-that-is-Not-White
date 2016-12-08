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
	
	public void setColor(Color value, DShape shape)
	{
		DShapeModel model = shape.getModel();
		model.setColor(value);
		canvas.getChildren().remove(shape);
		canvas.getChildren().add(shape.draw());
		System.out.println(value);
	}
	public void addRectangle( DRect rect){	
		list.add(0, rect);
		canvas.draw(list, table);
		System.out.println("Added Rectangle");
	}
	public void addEllipse(DOval oval)
	{		
		list.add(0, oval);
		canvas.draw(list, table);
		System.out.println("Added Ellipse");
	}
	public void addText(DText t) //Saaj's Code
	{
		DShapeModel model = t.getModel();
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(model.getType(new DText(new DTextModel()) )), model.getX(), model.getY(), model.getWidth(), model.getHeight());
		list.add(0, t);
		canvas.draw(list, table);
	}
	public ArrayList<DShape> getObjects()
	{
		return list;
	}
	
	public void addLine(DLine line)
	{
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty("line"), line.getModel().getX(), line.getModel().getY(), line.getModel().getWidth(), line.getModel().getHeight());
		table.getItems().add(info);
		table.refresh();
		list.add(0, line);
		canvas.draw(list, table);
		System.out.println("Added Line");
	}

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
	
	public void refresh( DShape shaped)
	{		
		list.remove(shaped);
		list.add(0, shaped);
		System.out.println("refreshed");
		Whiteboard.TableInfo info = null;
		canvas.draw(list, table);
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
	public void removeObject(DShape d)
	{
		
		System.out.println("In the controller class:" + d +"\nlist size:" + list.size());
		list.remove(d);
		canvas.getChildren().remove(d);
		canvas.draw(list, table);
	}
	public void move2Back(DShape d)
	{
		DShape now = d;
		list.remove(d);
		list.add(now);
		canvas.draw(list, table);
		//updateTable();
		
	}
	public void move2Front(DShape d)
	{
		DShape now = d;
		list.remove(d);
		list.add(0, now);
		canvas.draw(list, table);
		//updateTable();
	}
}
