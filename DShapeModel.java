package WhiteBoard;
import javafx.scene.paint.Color;

public class DShapeModel {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	private String type;
	public DShapeModel(int xPosition, int yPosition, int width, int height, Color color, String type)
	{
		this.x = xPosition;		
		this.y = yPosition;
		this.width = width;
		this.height = height;
		this.color = color;
		this.type = type;
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
	public String getType(){
		return type;
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
}
