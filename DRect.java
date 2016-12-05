package WhiteBoard;
import javafx.scene.paint.Color;

public class DRect extends DShape {

	public DRect()
	{
		this.model = new DRectModel(0,0,0,0, Color.GRAY);
	}
	/*(
	public DRect(int x, int y, int width, int height){
		this.model = new DRectModel(x, y, width, height, Color.GRAY);
	}*/
	
	public int getX()
	{
		return model.getX();
	}

	public int getY()
	{
		return model.getY();
	}
	
	public int getWidth()
	{
		return model.getWidth();
	}
	
	public int getHeight()
	{
		return model.getHeight();
	}
	
	public Color getColor()
	{
		return model.getColor();
	}
	public void setX(int xPosition)
	{
		this.model.setX(xPosition);
	}

	public void setY(int yPosition)
	{
		this.model.setY(yPosition);
	}
	
	public void setWidth(int width)
	{
		this.model.setWidth(width);
	}
	
	public void setHeight(int height)
	{
		this.model.setHeight(height);
	}
	
	public void setColor(Color color)
	{
		this.model.setColor(color);
	}
}
