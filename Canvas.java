import java.awt.Panel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Canvas extends Application {
	public void start(Stage stage){
		Scene scene = new Scene(new Group());
		stage.setTitle("Whiteoard");
		stage.setHeight(500);
		stage.setWidth(600);
		
		BorderPane pane = new BorderPane();
		
		
		
		
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(5);
		buttonBox.setPadding(new Insets(10, 0, 0, 10));
		Text add = new Text("Add:");
		Button addRect = new Button("Rect");
		Button addOval = new Button("Oval");
		Button addLine = new Button("Line");
		Button addText = new Button("Text");
		
		
		HBox colorBox = new HBox();
		colorBox.setSpacing(5);
		colorBox.setPadding(new Insets(10, 0, 0, 10));
		Button colorPicker = new Button("Color");
		
		
		
		buttonBox.getChildren().add(add);
		buttonBox.getChildren().add(addRect);
		buttonBox.getChildren().add(addOval);
		buttonBox.getChildren().add(addLine);
		buttonBox.getChildren().add(addText);
		
		colorBox.getChildren().add(colorPicker);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(buttonBox, colorBox);
		
		pane.setLeft(vbox);
		
		((Group) scene.getRoot()).getChildren().add(pane);
		
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args){
		launch(args);
	}
	
}
