package WhiteBoard;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DText extends DShape {
	String theText;
	Font font;
	public DText(DTextModel text)
	{
		super(text, "text");
		theText = "Hello";
		font = new Font("Dialog", 25);
	}
	
	@Override
	public Shape draw()
	{
		DShapeModel model = super.getModel();
		Text text = new Text(model.getX() - model.getHeight()/10, model.getY() + model.getHeight(), theText);
		
		double fontSize = super.getModel().getHeight() * 1.4;
		font = new Font(font.getName(),fontSize);
		text.setFont(font);
		text.setFill(super.getModel().getColor());

		super.getModel().setWidth((int)text.getLayoutBounds().getWidth());
		return text;
	}
	
	public void setText(String s){
		theText = s;
	}
	
	public void setFont(Font f){
		font = f;
	}
}
