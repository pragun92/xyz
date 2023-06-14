package application.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gluonhq.charm.glisten.control.TextField;

import application.Main;
import application.User;
import application.handlers.DatabaseHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@FXML
	private Button btn_close;
	
	@FXML
	private Label loginerrormessage;
	
	@FXML
	private Button btn_login;
	
	@FXML
	private TextField txtusername;
	
	@FXML
	private PasswordField txtpassword;
		
	
	public void loginApplicationAction(ActionEvent e) throws IOException {
		Main m = new Main();
		DatabaseHandler databaseHandler = new DatabaseHandler();
		User user = null;
		
		String currentUser = txtusername.getText();
		String password = txtpassword.getText();
		
		if(currentUser.trim().length()==0 && password.trim().length()==0) {
			LOGGER.info("No username or password was set");
			loginerrormessage.setText("Please enter username and password");
		}else {
			LOGGER.info("Retrieving record from database for user: {}", currentUser);
			user = databaseHandler.retrieveUserRecord(currentUser);
			
			if(null == user) {
				loginerrormessage.setText("Username wrong");
			}else {
				LOGGER.info("Verifying password");
				if(user.getPassword().equals(txtpassword.getText())) {
					LOGGER.info("Password is valid");
					Node  source = (Node)  e.getSource(); 
					Stage stage1  = (Stage) source.getScene().getWindow();
					stage1.close();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/MainMenuScreen.fxml"));
				    Parent root1 = (Parent) fxmlLoader.load();
				    Stage stage2 = new Stage();
				    stage2.setUserData(user);
				    stage2.setScene(new Scene(root1, 1100, 500)); 
				    MainMenuController controller = fxmlLoader.getController();
				    controller.shareData(user);

				    stage2.show();
				    
				}else {
					LOGGER.info("Password is not valid");
				}
			}
			
			
		}
	}
	
	
	
	public void closeApplicationAction(ActionEvent e) {
		Stage stage = (Stage) btn_close.getScene().getWindow();
		stage.close();
	}
	

}
