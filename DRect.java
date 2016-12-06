package WhiteBoard;
import java.awt.Graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DRect extends DShape {

	public DRect(DRectModel rect)
	{
		super(rect);
	}
	
	@Override
	public void draw()
	{
		Rectangle drawn = new Rectangle(super.getModel().getX(),super.getModel().getY(), super.getModel().getWidth(), super.getModel().getHeight());
		drawn.setFill(super.getModel().getColor());
	}
}
