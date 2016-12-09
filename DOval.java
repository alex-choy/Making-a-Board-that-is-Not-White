package WhiteBoard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class DOval extends DShape {

	public DOval(DOvalModel oval) {
		super(oval,"oval");
	}

	@Override
	public Shape draw() {
		DShapeModel model = super.getModel();
		Ellipse drawn = new Ellipse(model.getX() + model.getWidth()/2, model.getY() + model.getHeight()/2, model.getWidth()/2,
				model.getHeight()/2);
		drawn.setFill(model.getColor());
		return drawn;
	}

}
