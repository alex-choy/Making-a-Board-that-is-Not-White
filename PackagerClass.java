package WhiteBoard;

import java.io.Serializable;

public class PackagerClass implements Serializable
{
	DShapeModel[] shapes;
	String command;
	public PackagerClass(DShapeModel[] shapes, String string)
	{
		this.shapes = shapes;
		this.command = string;
	}
	
	public DShapeModel[] getShapes()
	{
		return shapes;
	}

	public String getCommand()
	{
		return command;
	}
}
