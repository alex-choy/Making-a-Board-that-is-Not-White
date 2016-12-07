package WhiteBoard;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javafx.scene.Node;
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
	
	//Saaj's Code start--------------------------------------------------------------------------
	public void draw(ArrayList<DShape> list) {
		System.out.println("In the canvas class " + list.size());
		for(int i = list.size()-1; i >=0; i--)
		{
			DShape o = list.get(i);
			getChildren().add(o.draw());
			
		}
		
	}
	//Saaj's Code end---------------------------------------------------------------------------------------
}
