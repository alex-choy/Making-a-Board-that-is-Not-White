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
		Shape drawn = new Rectangle(super.getModel().getX(),super.getModel().getY(), super.getModel().getWidth(), super.getModel().getHeight());
		drawn.setFill(super.getModel().getColor());
		return drawn;
	}
}
