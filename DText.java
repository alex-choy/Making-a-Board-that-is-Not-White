package WhiteBoard;

import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DText extends DShape {
	public DText(DTextModel text) {
		super(text, "text", "Hello", new Font("Dialog", 25));
	}

	@Override
	public Shape draw() {
		DShapeModel model = super.getModel();
		Text text = new Text(model.getX() - model.getHeight() / 10, model.getY() + model.getHeight(),
				this.getModel().getText());

		double fontSize = super.getModel().getHeight() * 1.4;
		this.getModel().setFont(new Font(this.getModel().getFont().getName(), fontSize));
		text.setFont(this.getModel().getFont());
		text.setFill(super.getModel().getColor());

		super.getModel().setWidth((int) text.getLayoutBounds().getWidth());
		return text;
	}

}
