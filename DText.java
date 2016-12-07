package WhiteBoard;

import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DText extends DShape {
	String theText;
	Font font;
	public DText(DTextModel text)
	{
		super(text);
		theText = "Hello";
		font = new Font("Dialog", 12);
	}
	
	@Override
	public Shape draw()
	{
		Text drawn = new Text(super.getModel().getX(), super.getModel().getY(),theText);
		drawn.setFill(super.getModel().getColor());
		return drawn;
	}
	
	public void setText(String s){
		theText = s;
	}
	
	public void setFont(Font f){
		font = f;
	}
}
