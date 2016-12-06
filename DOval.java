package WhiteBoard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class DOval extends DShape {

	public DOval(DOvalModel oval) {
		super(oval);
	}

	@Override
	public Shape draw() {
		DShapeModel model = super.getModel();
		Ellipse drawn = new Ellipse(model.getX() + model.getWidth(), model.getY() + model.getHeight(), model.getWidth(),
				model.getHeight());
		drawn.setFill(model.getColor());
		return drawn;
	}

}
