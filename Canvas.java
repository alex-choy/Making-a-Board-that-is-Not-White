package WhiteBoard;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import WhiteBoard.Whiteboard.TableInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Canvas extends Pane {
	public Canvas() {
		super();
		super.setPrefSize(500, 500);
		super.setStyle("-fx-border-color: black");
	}

	public void addShape(DShape shape) {
		//DShapeModel model = shape.model;

	}

	public void paintComponent(Graphics g) {

	}
	
	public void draw(ArrayList<DShape> list, TableView<TableInfo> table) 
	{
		System.out.println("In the canvas class " + list.size());
		getChildren().clear();
		table.getItems().clear();
		table.refresh();
		for(int i = list.size()-1; i >=0; i--)
		{
			DShape other = list.get(list.size()-i-1);
			DShapeModel model = other.getModel();
			Whiteboard.TableInfo info = new TableInfo(new SimpleStringProperty(model.getType(new DRect(new DRectModel()) )), model.getX(),model.getY(), model.getWidth(), model.getHeight());
			table.getItems().add(info);
			table.refresh();		
			DShape o = list.get(i);
			getChildren().add(o.draw());
			
		}
		
	}

}
