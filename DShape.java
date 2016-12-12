package WhiteBoard;

import java.io.Serializable;

import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

public class DShape implements Serializable {
	private DShapeModel model;

	public DShape(DShapeModel model, String type) {

		this.model = model;
		model.setType(type);
	}

	public DShape(DShapeModel model, String type, String text, Font font) {
		this.model = model;
		model.setType(type);
		model.setText(text);
		model.setFont(font);
	}

	public DShapeModel getModel() {
		return model;
	}

	public void setModel(DShapeModel d) {
		model = d;
	}

	public Shape draw() {
		return null;
	}

}