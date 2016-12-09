package WhiteBoard;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DLineModel extends DShapeModel {

	public DLineModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DLineModel getModel()
	{
		return this;
	}
	
	public Rectangle[] drawKnobs(){
		Rectangle[] knobs = new Rectangle[2];
		
		Rectangle topRight = null;
		Rectangle botLeft = null;
		
		if(getX() - 5 < 0 || getY() - 5 < 0)
		{
			topRight = new Rectangle(getX()-4, getY()-4, 9, 9);
		}
		else
		{
			topRight = new Rectangle(getX()-5, getY()-5, 9, 9);
		}
		if (getX() + getWidth() + 5 > c.getWidth() || getY() + getHeight() + 5 > c.getHeight())
		{
			botLeft = new Rectangle( getWidth() - 4,  getHeight() - 4, 9, 9);
		}
		else
		{
			botLeft = new Rectangle(getX() + getWidth() - 5, getY() + getHeight() - 5, 9, 9);
		}
		
		
		
		knobs[0] = topRight;
		knobs[1] = botLeft;
		
		return knobs;
	}

}
