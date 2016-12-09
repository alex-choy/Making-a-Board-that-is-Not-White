package WhiteBoard;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class DLine extends DShape {

	public DLine(DLineModel line)
	{
		super(line, "line");
	}
	
	@Override
	public Shape draw()
	{
		DShapeModel model = super.getModel();
		Line drawn = new Line(model.getX(),model.getY(), model.getWidth(), model.getHeight());
		drawn.setFill(model.getColor());
		return drawn;
	}
	
}
