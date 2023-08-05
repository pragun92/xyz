package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.handlers.DatabaseHandler;
import application.model.Car;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenuController implements Initializable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuController.class);
	
	public User user;
	
	@FXML
	private BorderPane bp;
	
	@FXML
	private AnchorPane master;
	
	@FXML
	private Label lbl_Username;
	
	@FXML
	private Button btn_showCars;
	
	@FXML
	private Button btn_showSales;
	

	void shareData(User currentUser) {
		user = currentUser;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		user = new User();
		lbl_Username.setText(user.getUsername());
		bp.setCenter(null);
		loadCenterScene("/application/screens/CarFunctions.fxml");
		
	}
	
	@FXML
	public void showCar(MouseEvent e) {
		bp.setCenter(null);
		loadCenterScene("/application/screens/CarFunctions.fxml");
		
		
	}
	
	@FXML
	public void showCustomers(MouseEvent e) {
		
	}
	
	@FXML
	public void showSales(MouseEvent e) {
		/*bp.setCenter(null);
		loadCenterScene("/application/screens/SalesScreen.fxml");*/
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/screens/SalesScreen.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage2 = new Stage();
		    stage2.setScene(new Scene(root1, 900, 400)); 
		    stage2.show();
		}catch (Exception es) {
			es.printStackTrace();
		}
		
		
	}
	
	@FXML
	public void showUsers(MouseEvent e) {
		
	}
	
	@FXML
	public void logout(MouseEvent e) {
		
	}
	
	private void loadCenterScene(String scene) {

		try {
			master =FXMLLoader.load(getClass().getResource(scene));
		}catch (Exception e) {
			e.printStackTrace();
		}
		bp.setCenter(null);
		bp.setCenter(master);
		
		
		
	}
	
}
