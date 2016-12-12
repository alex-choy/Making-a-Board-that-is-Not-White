package WhiteBoard;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CilentWhiteboard {
	Menu file;
	MenuBar menu;
	MenuItem open;
	MenuItem save;
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	ObservableList<TableInfo> data = FXCollections.observableArrayList();
	TableColumn<TableInfo, Double> xColumn;
	TableColumn<TableInfo, Double> yColumn;
	TableColumn<TableInfo, Double> widthColumn;
	TableColumn<TableInfo, Double> heightColumn;
	TableColumn<TableInfo, SimpleStringProperty> nameColumn;
	Canvas canvas = null;
	VBox vbox = null;
	DShape focusedObject = null;
	Rectangle[] knobs = null;
	public String port;

	public CilentWhiteboard(String name) {
		this.port = name;
	}

	@SuppressWarnings("unchecked")
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Whiteboard");
		stage.setHeight(610);
		stage.setWidth(1100);

		BorderPane pane = new BorderPane();
		TableView<TableInfo> table = new TableView<>();
		canvas = new Canvas(table);
		file = new Menu("FILE");
		menu = new MenuBar();
		open = new MenuItem("OPEN");
		save = new MenuItem("SAVE");

		menu.getMenus().add(file);
		file.getItems().addAll(open, save);
		// CREATING BUTTONS AND OTHER OBJECTS

		vbox = new VBox();

		HBox objectInfo = new HBox();

		objectInfo.setSpacing(5);
		objectInfo.setPadding(new Insets(10, 10, 10, 10));
		table.setPrefWidth(stage.getWidth());
		table.setPrefHeight(365);

		nameColumn = new TableColumn<>("Name");
		nameColumn.setPrefWidth(stage.getWidth() / 10);
		nameColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, SimpleStringProperty>("name"));

		xColumn = new TableColumn<>("X");
		xColumn.setPrefWidth(stage.getWidth() / 8.05);
		xColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("x"));

		yColumn = new TableColumn<>("Y");
		yColumn.setPrefWidth(stage.getWidth() / 8.05);
		yColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("y"));

		widthColumn = new TableColumn<>("Width");
		widthColumn.setPrefWidth(stage.getWidth() / 8.05);
		widthColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("width"));

		heightColumn = new TableColumn<>("Height");
		heightColumn.setPrefWidth(stage.getWidth() / 8.05);
		heightColumn.setCellValueFactory(new PropertyValueFactory<TableInfo, Double>("height"));

		table.getColumns().addAll(xColumn, yColumn, widthColumn, heightColumn);
		table.setMaxWidth(stage.getWidth() / 2);

		// Server stuff
		// #########################################################################
		// #######################################################################

		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				orgSceneX = event.getSceneX();
				orgSceneY = event.getSceneY();
				double middleX = event.getX();
				double middleY = event.getY();
				boolean obj = false;
				for (DShape d : canvas.getList()) {
					DShapeModel shape = d.getModel();
					if (middleX >= shape.getX() && middleX <= shape.getX() + shape.getWidth()) {
						if (middleY >= shape.getY() && middleY <= shape.getY() + shape.getHeight()) {
							if (d != focusedObject) {
								makeUnfocused();
								focusedObject = d;
								obj = true;

								break;
							} else {
								obj = false;
								break;
							}
						}
					}
				}
				if (!obj) {
					makeUnfocused();
				}
				if (focusedObject != null) {
					makeFocused();
				}

			}

		});

		VBox container = new VBox();
		container.setStyle("-fx-background-color: orange");

		vbox.getChildren().add(table);
		pane.setLeft(vbox);
		pane.setCenter(canvas);

		Text welcome = new Text("You are in the Client View");
		Font f = new Font("Times New Roman", 40);
		welcome.setFont(f);

		HBox hbox = new HBox();
		hbox.getChildren().add(welcome);
		pane.setTop(hbox);
		welcome.setFill(Color.BLACK);

		container.getChildren().add(pane);

		((Group) scene.getRoot()).getChildren().add(container);

		stage.setScene(scene);
		stage.show();

	}

	public void makeUnfocused() {
		if (knobs != null) {
			canvas.getChildren().removeAll(knobs);
			knobs = null;
		}
		focusedObject = null;
	}

	public void makeFocused() {
		DShapeModel model = focusedObject.getModel();

		if (focusedObject instanceof DLine) {
			knobs = model.drawKnobs();
		} else {
			knobs = model.drawKnobs();
		}
		canvas.getChildren().addAll(knobs);
	}

}
