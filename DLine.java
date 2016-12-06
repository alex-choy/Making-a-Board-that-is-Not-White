package WhiteBoard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DLine extends DShape {

	public DLine(DLineModel line)
	{
		super(line);
	}
	
	@Override
	public Shape draw()
	{
		Line drawn = new Line(super.getModel().getX(),super.getModel().getY(), super.getModel().getWidth(), super.getModel().getHeight());
		drawn.setFill(super.getModel().getColor());
		return drawn;
	}
	
}
