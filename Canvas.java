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
	
	public void draw(ArrayList<DShape> list) {
		for (Object o : list) {
			if (o instanceof DRect) {
				super.getChildren().add(((DRect) o).draw());
			} else if (o instanceof DOval) {
				super.getChildren().add(((DOval) o).draw());
			} else if (o instanceof DLine) {
				super.getChildren().add(((DLine) o).draw());
			} else if (o instanceof DText) {
				super.getChildren().add(((DText) o).draw());
			}
		}
	}

}
