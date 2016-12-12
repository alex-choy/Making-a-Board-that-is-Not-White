package WhiteBoard;
import java.awt.Graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DRect extends DShape {

	Rectangle r = null;
	DShapeModel model = null;
	public DRect(DRectModel rect)
	{
		super(rect, "rectangle");
		model = super.getModel();
		r = new Rectangle(model.getX(),model.getY(),model.getWidth(), model.getHeight());
		r.setFill(model.getColor());
	}
	
	public void changeX(int x)
	{
		model.setX(x);
	}
	
	public void changeY(int y)
	{
		model.setY(y);
	}
	
	
	@Override
	public Shape draw()
	{
		r.setTranslateX(model.getX() - r.getX());
		r.setTranslateY(model.getY() - r.getY());
		r.setWidth(model.getWidth());
		r.setHeight(model.getHeight());
		r.setFill(model.getColor());
		return r;
	}
}
