package WhiteBoard;
import java.util.ArrayList;

import WhiteBoard.Whiteboard.TableInfo;
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
		Whiteboard.TableInfo info = new TableInfo("rect", model.getX(),model.getY(), model.getWidth(), model.getHeight());
		table.getItems().add(info);
		table.refresh();		
		list.add(rect);
		canvas.draw(list);
		System.out.println("Added Rectangle");
	}
	public void addEllipse(TableView<TableInfo> table, Canvas canvas, DOval oval)
	{
		Ellipse ellipse = new Ellipse(100, 100, 100, 100);
		ellipse.setFill(Color.GREY);
		
		DShapeModel model = oval.getModel();
		
		
		
		Whiteboard.TableInfo info = new TableInfo("ellipse", model.getX(), model.getY(), model.getWidth(), model.getHeight());
		
		table.getItems().add(info);
		//table.refresh();	
		list.add(oval);
		canvas.draw(list);
		System.out.println("Added Ellipse");
	}
	public void addText(String text, String f, TableView<TableInfo> table, Canvas canvas )
	{
		Text t = new Text(text);
		t.setFont(Font.font(f));
		Whiteboard.TableInfo info = new TableInfo("text", t.getX(), t.getY(), t.getScaleX(), t.getScaleY());
		table.getItems().add(info);
		table.refresh();
		//list.add(t);
		System.out.println("Added Text: " + text );
	}
	public ArrayList<DShape> getObjects()
	{
		return list;
	}
	
	public void addLine(TableView<TableInfo> table, Canvas canvas)
	{
		Line line = new Line(100, 100, 100, 100);
		line.setFill(Color.GREY);
		Whiteboard.TableInfo info = new TableInfo("line", line.getStartX(), line.getStartY(), line.getEndX() - line.getStartX(), line.getEndY() - line.getStartY());
		table.getItems().add(info);
		table.refresh();
		///list.add(line);
		System.out.println("Added Line");
	}
	
}
