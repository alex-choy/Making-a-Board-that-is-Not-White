package WhiteBoard;

import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class DShapeModel implements Serializable {

	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	public String type;
	private String text;
	private Font font;

	public DShapeModel() {
		this.x = 20;
		this.y = 20;
		this.width = 50;
		this.height = 50;
		this.color = Color.GRAY;
	}

	public DShapeModel(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}

	public void setX(int xPosition) {
		x = xPosition;
	}

	public void setY(int yPosition) {
		y = yPosition;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setText(String t) {
		text = t;
	}

	public String getText() {
		return text;
	}

	public void setFont(Font f) {
		font = f;
	}

	public Font getFont() {
		return font;
	}

	public Rectangle[] drawKnobs() {

		Rectangle knob1 = new Rectangle(x, y, 9, 9);

		Rectangle knob2 = new Rectangle(x, y + height - 9, 9, 9);

		Rectangle knob3 = new Rectangle(x + width - 9, y, 9, 9);

		Rectangle knob4 = new Rectangle(x + width - 9, y + height - 9, 9, 9);

		Rectangle[] knobs = new Rectangle[4];
		knobs[0] = knob1;
		knobs[1] = knob2;
		knobs[2] = knob3;
		knobs[3] = knob4;
		return knobs;
	}
}
