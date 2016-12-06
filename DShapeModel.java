package WhiteBoard;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DShapeModel {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	public DShapeModel()
	{
		this.x = 0;		
		this.y = 0;
		this.width = 50;
		this.height = 50;
		this.color = Color.GRAY;
	}

	

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setX(int xPosition)
	{
		this.x = xPosition;
	}

	public void setY(int yPosition)
	{
		this.y = yPosition;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	public String getType(DShape d)
	{
		if (d instanceof DRect)
		return "rectangle";
		if (d instanceof DOval)
			return "oval";
		if(d instanceof DText)
			return "text";
		if(d instanceof DLine)
			return "line";
		else 
			return null;
	}
	
	public Rectangle[] drawKnobs()
	{
		Rectangle knob1 = new Rectangle(x, y, 5, 5);
		Rectangle knob2 = new Rectangle(x + width, y, 5, 5);
		Rectangle knob3 = new Rectangle(x, y + height, 5, 5);
		Rectangle knob4 = new Rectangle(x + width, y + height, 5, 5);
		Rectangle[] knobs = new Rectangle[4];
		knobs[0] = knob1;
		knobs[1] = knob2;
		knobs[2] = knob3;
		knobs[3] = knob4;
		return knobs;
	}
}
