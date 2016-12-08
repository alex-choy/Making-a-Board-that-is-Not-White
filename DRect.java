package WhiteBoard;
import java.awt.Graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DRect extends DShape {

	public DRect(DRectModel rect)
	{
		super(rect);
	}
	
	@Override
	public Shape draw()
	{
		DShapeModel model = super.getModel();
		Shape drawn = new Rectangle(model.getX(),model.getY(),model.getWidth(), model.getHeight());
		drawn.setFill(model.getColor());
		return drawn;
	}
}
