package WhiteBoard;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartMenu extends Application {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage stage) {
		stage.setTitle("Whiteboard");
		stage.setHeight(200);
		stage.setWidth(400);

		Scene scene = new Scene(new Group());

		BorderPane pane = new BorderPane();

		Text welcome = new Text("Welcome to Whiteboard");
		Font f = new Font("Times New Roman", 39);
		welcome.setFont(f);
		welcome.autosize();
		VBox vbox = new VBox();
		vbox.getChildren().add(welcome);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setStyle("-fx-background-color: WHITE;");

		pane.setTop(vbox);

		Button server = new Button("Server");
		Button offline = new Button("Offline");
		Button client = new Button("Client");

		server.setPrefSize(100, 30);
		offline.setPrefSize(100, 30);
		client.setPrefSize(100, 30);

		HBox online = new HBox();
		online.setSpacing(10);
		online.setPadding(new Insets(20, 10, 10, 10));
		online.setAlignment(Pos.CENTER);
		online.setPrefWidth(pane.getWidth());
		online.getChildren().addAll(server, client);

		pane.setCenter(online);

		HBox notOnline = new HBox();
		notOnline.setSpacing(5);
		notOnline.setPadding(new Insets(10, 10, 20, 10));
		notOnline.setAlignment(Pos.CENTER);
		notOnline.getChildren().add(offline);
		notOnline.setPrefWidth(pane.getWidth());
		notOnline.setAlignment(Pos.CENTER);
		pane.setBottom(notOnline);

		pane.setStyle("-fx-background-color: #336523;");

		server.setOnAction(new EventHandler() {
			public void handle(Event e) {
				TextInputDialog window = new TextInputDialog("8001");
				window.setTitle("Server Port");
				window.setHeaderText("Please enter a port number");
				Optional<String> result = window.showAndWait();
				if (result.isPresent()) {
					System.out.println("Server number: " + result.get());
				}
				Whiteboard wb = new Whiteboard(Integer.parseInt(result.get().trim()), true);
				wb.start(stage);
			}
		});

		offline.setOnAction(new EventHandler() {

			@Override
			public void handle(Event event) {
				Whiteboard wb = new Whiteboard(0, false);
				wb.start(stage);
			}
		});

		client.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				TextInputDialog window = new TextInputDialog("127.0.0.1:8001");
				window.setTitle("Server Port");
				window.setHeaderText("Please enter a port number");
				Optional<String> result = window.showAndWait();
				if (result.isPresent()) {
					System.out.println("Server number: " + result.get());
				}
				String r = result.get();
				CilentWhiteboard cwb = new CilentWhiteboard(r);
				cwb.start(stage);
			}

		});

		((Group) scene.getRoot()).getChildren().add(pane);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
