package WhiteBoard;

import java.util.ArrayList;

import WhiteBoard.Whiteboard.TableInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CilentWhiteboard {
	Menu file;
	MenuBar menu;
	MenuItem open;
	MenuItem save;
	ObservableList<TableInfo> data = FXCollections.observableArrayList();
	TableColumn<TableInfo, Double> xColumn;
	TableColumn<TableInfo, Double> yColumn;
	TableColumn<TableInfo, Double> widthColumn;
	TableColumn<TableInfo, Double> heightColumn;
	TableColumn<TableInfo, SimpleStringProperty> nameColumn;
	Canvas canvas = null;
	VBox vbox = null; //Saaj's change

	@SuppressWarnings("unchecked")
	public void start(Stage stage)
	{
		Scene scene = new Scene(new Group());
		stage.setTitle("Whiteboard");
		stage.setHeight(610);
		stage.setWidth(1100);
		
		BorderPane pane = new BorderPane();
		canvas = new Canvas();
		file = new Menu("FILE");
		menu = new MenuBar();
		open = new MenuItem("OPEN");
		save  = new MenuItem("SAVE"); 
		
		menu.getMenus().add(file);
		file.getItems().addAll(open,save);
		//	CREATING BUTTONS AND OTHER OBJECTS
		
		vbox = new VBox();
		
		HBox objectInfo = new HBox();  //saaj's code
		
		
		objectInfo.setSpacing(5);		
		objectInfo.setPadding(new Insets(10, 10, 10, 10)); //saaj's code			
		
	
		TableView<TableInfo> table = new TableView<>();
		//Controller controller = new Controller(canvas, table); //Saaj's code
		table.setPrefWidth(stage.getWidth());
		table.setPrefHeight(365);  //saaj's code
		
		nameColumn = new TableColumn<>("Name");
		nameColumn.setPrefWidth(stage.getWidth()/10);
		nameColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, SimpleStringProperty>("name"));

		
		xColumn = new TableColumn<>("X");
		xColumn.setPrefWidth(stage.getWidth()/8.05); //saaj's code
		xColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("x"));
		
		yColumn = new TableColumn<>("Y");
		yColumn.setPrefWidth(stage.getWidth()/8.05);
		yColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("y"));
		
		widthColumn = new TableColumn<>("Width");
		widthColumn.setPrefWidth(stage.getWidth()/8.05);
		widthColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("width"));
		
		heightColumn = new TableColumn<>("Height");
		heightColumn.setPrefWidth(stage.getWidth()/8.05);
		heightColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("height"));
		
		table.getColumns().addAll(xColumn, yColumn, widthColumn, heightColumn);
		table.setMaxWidth(stage.getWidth()/2);

        
		ArrayList<String> list = new ArrayList<>();
		list.add("Times New Roman");
		list.add("Calibri");
		list.add( "Arial");
		
		//BufferedImage image = (BufferedImage) createImage(3,3);
		
		vbox.getChildren().add(table);
		pane.setCenter(canvas);
		pane.setLeft(vbox);
		
		((Group) scene.getRoot()).getChildren().add(pane);
		
		stage.setScene(scene);
		stage.show();
	}
}
