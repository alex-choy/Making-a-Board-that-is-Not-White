package WhiteBoard;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class DOval extends DShape {

	public DOval(DOvalModel oval)
	{
		super(oval);
	}	
	
	@Override
	public void draw()
	{
		Ellipse drawn = new Ellipse(super.getModel().getX(),super.getModel().getY(), super.getModel().getWidth(), super.getModel().getHeight());
		drawn.setFill(super.getModel().getColor());	}
	}
