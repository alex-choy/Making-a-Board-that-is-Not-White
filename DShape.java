package WhiteBoard;
import java.awt.Graphics;

import javafx.scene.shape.Shape;

public class DShape {
	private DShapeModel model;
  
  public DShape(DShapeModel model, String type)
  {
	  
	  this.model = model;
	  model.setType(type);
  }
  public DShapeModel getModel()
  {
	  return model;
  }

  public void setModel(DShapeModel d)
  {
	  model = d;
  }
  
  public Shape draw()
  {
	  return null;
  }
  
}