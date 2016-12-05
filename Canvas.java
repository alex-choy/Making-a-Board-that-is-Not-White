package WhiteBoard;

import java.awt.Graphics;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Canvas extends Pane {
	public Canvas(){
		super();
		super.setPrefSize(500, 500);
		super.setStyle("-fx-border-color: black");
	}
	
	public void addShape(DShapeModel model, String shape){
		if (shape.equals("rectangle")){
			Rectangle rect = new Rectangle(model.getX(), model.getY() ,model.getWidth() ,model.getHeight() );
		}
		else if (shape.equals("oval")){
		}
		else if (shape.equals("line")){
			
		}
		else if (shape.equals("text")){
			
		}
	}
	
	public void draw(Graphics g){
		g.drawRect(20, 20, 40, 20);
	}
	
}
