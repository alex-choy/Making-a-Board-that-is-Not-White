package WhiteBoard;
import java.util.ArrayList;

import WhiteBoard.Whiteboard.TableInfo;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Controller {
	ArrayList<Object> list;
	Whiteboard w;
	public Controller()
	{
		list = new ArrayList<>();
		w = new Whiteboard();
	}
	
	public void setColor(Color value) {
		// TODO Auto-generated method stub
		System.out.println(value);
	}
	public void addRectangle(TableView<TableInfo> table){
		Rectangle rect = new Rectangle(100, 100, 100, 100);
		rect.setFill(Color.GREY);
		Whiteboard.TableInfo info = new TableInfo("rect", rect.getX(),rect.getY(), rect.getWidth(), rect.getHeight());
		table.getItems().add(info);
		table.refresh();		
		list.add(rect);
		System.out.println("Added Rectangle");
	}
	public void addEllipse(TableView<TableInfo> table)
	{
		Ellipse ellipse = new Ellipse(100, 100, 100, 100);
		ellipse.setFill(Color.GREY);
		Whiteboard.TableInfo info = new TableInfo("ellipse", ellipse.getCenterX() - ellipse.getRadiusX()/2,ellipse.getCenterY() - ellipse.getRadiusY()/2, ellipse.getRadiusX(), ellipse.getRadiusY());
		table.getItems().add(info);
		//table.refresh();	
		list.add(ellipse);
		System.out.println("Added Ellipse");
	}
	public void addText(String text, String f, TableView<TableInfo> table )
	{
		Text t = new Text(text);
		t.setFont(Font.font(f));
		Whiteboard.TableInfo info = new TableInfo("text", t.getX(), t.getY(), t.getScaleX(), t.getScaleY());
		table.getItems().add(info);
		table.refresh();
		list.add(t);
		System.out.println("Added Text: " + text );
	}
	public ArrayList getObjects()
	{
		return list;
	}
	
	public void addLine(TableView<TableInfo> table)
	{
		Line line = new Line(100, 100, 100, 100);
		line.setFill(Color.GREY);
		Whiteboard.TableInfo info = new TableInfo("line", line.getStartX(), line.getStartY(), line.getEndX() - line.getStartX(), line.getEndY() - line.getStartY());
		table.getItems().add(info);
		table.refresh();
		list.add(line);
		System.out.println("Added Line");
	}
	
}
