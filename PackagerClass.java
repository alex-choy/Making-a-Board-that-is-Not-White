package WhiteBoard;

import java.io.Serializable;

public class PackagerClass implements Serializable {
	DShapeModel[] shapes;
	String command;
	public int index = 0;
	public String color;

	public PackagerClass() {

	}

	public DShapeModel[] getShapes() {
		return shapes;
	}

	public String getCommand() {
		return command;
	}

	public void setShapes(DShapeModel[] model) {
		shapes = model;
	}

	public void setCommand(String com) {
		command = com;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int i) {
		index = i;
	}

	public void setColor(String c) {
		color = c;
	}

	public String getColor() {
		return color;
	}
}
