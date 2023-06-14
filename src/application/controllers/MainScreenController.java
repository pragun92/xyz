package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.User;
import application.handlers.DatabaseHandler;
import application.model.Car;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainScreenController implements Initializable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainScreenController.class);
	
	public User user;
	
	@FXML
	private Label lbl_Username;

	@FXML
	private Button btn_showCars;

	@FXML
	private Button show_Customers;
	
	@FXML
	private Button btn_showSales;
	
	@FXML
	private Button btn_showUsers;
	
	@FXML
	private Button btn_logout;
	
	@FXML
	private Button btn_addCar;
	
	@FXML 
	private Button btn_deleteCar;
	
	@FXML
	private Button btn_resfresh;
	
	@FXML
	private Button btn_editview;
	
	@FXML
	private AnchorPane anchor_car_actions;
	
	@FXML
	private TextField txt_searchCar;
	
	@FXML
	private TableView<Car> tableView;
	
	@FXML
	private TableColumn<Car, String> make;
	
	@FXML
	private TableColumn<Car, String> model;
	
	@FXML
	private TableColumn<Car, String> year;
	
	@FXML
	private TableColumn<Car, String> kms;
	
	@FXML
	private TableColumn<Car, String> price;
	
	@FXML
	private TableColumn<Car, String> status;
	
	private final ObservableList<Car> obs_car_list = FXCollections.observableArrayList();
	
	
	void shareData(User currentUser) {
		user = currentUser;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		user = new User();
		lbl_Username.setText(user.getUsername());
		if(user.getRole()==1 || user.getRole()==6) {
			btn_showUsers.setVisible(true);
		}else {
			btn_showUsers.setVisible(false);
		}
		displayCarData();
	}
	
	
	public void displayCarData() {
		obs_car_list.clear();
		DatabaseHandler databaseHandler = new DatabaseHandler();
		
		make.setCellValueFactory(new PropertyValueFactory<>("Make"));
		model.setCellValueFactory(new PropertyValueFactory<>("Model"));
		year.setCellValueFactory(new PropertyValueFactory<>("Year"));
		kms.setCellValueFactory(new PropertyValueFactory<>("Kms"));
		price.setCellValueFactory(new PropertyValueFactory<>("Price"));
		status.setCellValueFactory(new PropertyValueFactory<>("Status"));
		
		ArrayList<Car> carList = databaseHandler.retrieveAllCars();
		obs_car_list.addAll(carList);
		
		FilteredList<Car> filteredData = new FilteredList<>(obs_car_list, b -> true);
		
		if(null!=txt_searchCar.getText()) {
			txt_searchCar.textProperty().addListener((observable, oldValue, newValue) ->{
				filteredData.setPredicate(car -> {
					if(null == newValue || newValue.isEmpty()) {
						return true;
					}
					
					String lowerCaseFilter = newValue.toLowerCase();
					
					if(car.getMake().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					}else if(car.getModel().toLowerCase().indexOf(lowerCaseFilter) != -1){
						return true;
					}else if(car.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1){
						return true;
					}else {
						return false;
					}
				});
			});
		}
		
		SortedList<Car> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedData);
	}

	
	public void openAddMenu(ActionEvent e) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/AddCar.fxml"));
	    Parent root1 = (Parent) fxmlLoader.load();
	    Stage stage2 = new Stage();
	    stage2.setScene(new Scene(root1, 400, 400)); 
	    stage2.show();
		
	}
	
	public void deleteCar(ActionEvent e) throws IOException {
		LOGGER.info("Deleting Car");
		DatabaseHandler databaseHandler = new DatabaseHandler();
		
		Car carToDelete = tableView.getSelectionModel().getSelectedItem();
		
		databaseHandler.deleteCar(carToDelete);
		displayCarData();
		
		
	}
	
	public void onViewUpdate(ActionEvent e) throws IOException {
		LOGGER.info("EditView window Car");
		
		Car selectedCar = tableView.getSelectionModel().getSelectedItem();
		
		if(null!=selectedCar) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/CarDetails.fxml"));
		    Parent root1 = (Parent) fxmlLoader.load();
		    Stage stage2 = new Stage();
		    stage2.setUserData(selectedCar);
		    stage2.setScene(new Scene(root1, 600, 400));
		    CarShowUpdateDetailsController carShowUpdateDetailsController = fxmlLoader.getController();
		    carShowUpdateDetailsController.parseData(selectedCar);
		    stage2.show();
		}

		
	    
	}
	
	public void refreshTable(ActionEvent e) throws IOException {
		displayCarData();
	}
	
	public void showSales(ActionEvent e) throws IOException {
		LOGGER.info("Show sales");
		anchor_car_actions.setVisible(false);
		anchor_car_actions.getChildren().clear();
		
	}
	

}
