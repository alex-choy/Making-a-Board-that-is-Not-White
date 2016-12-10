package WhiteBoard;
import javafx.scene.paint.Color;

public class DRectModel extends DShapeModel {
	
	public DRectModel() 
	{
		super();
		super.setType("retangle");
	}
	
	public DRectModel(int x, int y, int w, int h, Color c)
	{
		super(x, y, w, h, c);
	}
	
	public DRectModel getModel()
	{
		return this;
	}

}
