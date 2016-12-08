package WhiteBoard;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DShapeModel {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	Canvas c = new Canvas();
	public DShapeModel()
	{
		this.x = 20;		
		this.y = 20;
		this.width = 50;
		this.height = 50;
		this.color = Color.GRAY;
	}
	
	public DShapeModel(int x, int y, int width, int height, Color color)
	{
		this.x = x;		
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
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
		else if (d instanceof DOval)
			return "oval";
		else if(d instanceof DText)
			return "text";
		else if(d instanceof DLine)
			return "line";
		else 
			return null;
	}
	
	public Rectangle[] drawKnobs()
	{
		Rectangle knob1 = null;
		Rectangle knob2 = null;
		Rectangle knob3 = null;
		Rectangle knob4 = null;
		if(x - 5 < 0 || y - 5 < 0)
		{
			knob1 = new Rectangle(x-9, y-9, 9, 9);
		}
		else
		{
			knob1 = new Rectangle(x, y, 9, 9);
		}
		if (x - 5 < 0 || y + 5 > c.getHeight() )
		{
			knob2 = new Rectangle(x, y + height - 9, 9, 9);
		}
		else
		{
			knob2 = new Rectangle(x - 9, y + height + 9, 9, 9);
		}
		if (x + width + 5 > c.getWidth() || y - 5 < 0)
		{
			knob3 = new Rectangle(x + width - 9, y, 9, 9);
		}
		else
		{
			knob3 = new Rectangle(x + width, y - 9, 9, 9);
		}
		if (x + width + 5 > c.getWidth() || y + height + 5 > c.getHeight())
		{
			knob4 = new Rectangle(x + width - 9, y + height - 9, 9, 9);
		}
		else
		{
			knob4 = new Rectangle(x + width, y + height+9, 9, 9);
		}
		Rectangle[] knobs = new Rectangle[4];
		knobs[0] = knob1;
		knobs[1] = knob2;
		knobs[2] = knob3;
		knobs[3] = knob4;
		return knobs;
	}
}
