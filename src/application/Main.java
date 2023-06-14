package application;
	
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.config.LogbackConfigLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			new LogbackConfigLoader();
			LOGGER.info("Starting application");
			Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(new Scene(root, 520, 400));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
