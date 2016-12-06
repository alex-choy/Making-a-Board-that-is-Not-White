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

	public void draw(ArrayList<Object> list) {
		for (Object o : list) {
			if (o instanceof DRect) {
				Rectangle rect = new Rectangle(((DRect) o).getX(), ((DRect) o).getY(), ((DRect) o).getWidth(), ((DRect) o).getHeight());
				super.getChildren().add(rect);
			} else if (o instanceof DOval) {
				// DOval oval = new DOval();
			} else if (o instanceof DLine) {
				// DLine line = new DLine();
			} else if (o instanceof DText) {
				// DText text = new DText();
			}
		}
	}

}
