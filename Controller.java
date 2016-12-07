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
	public Controller(Canvas canvas)
	{
		list = new ArrayList<>();
		w = new Whiteboard();
		this.canvas = canvas;
	}
	
	public void setColor(Color value) {
		// TODO Auto-generated method stub
		System.out.println(value);
	}
	public void addRectangle(TableView<TableInfo> table, Canvas canvas, DRect rect){
		DShapeModel model = rect.getModel();
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(model.getType(new DRect(new DRectModel()) )), model.getX(),model.getY(), model.getWidth(), model.getHeight());
		table.getItems().add(info);
		table.refresh();		
		list.add(0, rect);
		canvas.draw(list);
		System.out.println("Added Rectangle");
	}
	public void addEllipse(TableView<TableInfo> table, Canvas canvas, DOval oval)
	{		
		DShapeModel model = oval.getModel();
		
		
		
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty("ellipse"), model.getX(), model.getY(), model.getWidth(), model.getHeight());
		
		table.getItems().add(info);
		//table.refresh();	
		list.add(0, oval);
		canvas.draw(list);
		System.out.println("Added Ellipse");
	}
	public void addText(String text, String f, TableView<TableInfo> table, Canvas canvas, DText t)
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
	
	public void addLine(TableView<TableInfo> table, Canvas canvas, DLine line)
	{
		Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty("line"), line.getModel().getX(), line.getModel().getY(), line.getModel().getWidth(), line.getModel().getHeight());
		table.getItems().add(info);
		table.refresh();
		list.add(0, line);
		canvas.draw(list);
		System.out.println("Added Line");
	}
	
}
