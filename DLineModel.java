package WhiteBoard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DLineModel extends DShapeModel {
	Canvas c;

	public DLineModel(Canvas canvas) {
		super();
		setType("line");
		c = canvas;
	}

	public DLineModel getModel() {
		return this;
	}

	public Rectangle[] drawKnobs() {
		Rectangle knob1 = new Rectangle(this.getX(), this.getY(), 9, 9);
		Rectangle knob2 = new Rectangle(this.getWidth(), this.getHeight(), 9, 9);
		Rectangle[] knobs = new Rectangle[2];
		knobs[0] = knob1;
		knobs[1] = knob2;
		return knobs;
	}

}
