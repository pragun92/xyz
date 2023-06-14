package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.handlers.DatabaseHandler;
import application.model.Car;
import application.model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SellCarController implements Initializable{
	
	private Car selectedCar;
	
	@FXML
	private TextField txt_make;

	@FXML
	private TextField txt_model;
	
	@FXML
	private TextField txt_firstname;
	
	@FXML
	private TextField txt_lastname;
	
	@FXML
	private TextField txt_address;
	
	@FXML
	private TextField txt_phone;
	
	@FXML
	private TextField txt_id;
	
	@FXML
	private Button btn_searchCustomerID;
	
	@FXML
	private Button btn_sellCar;
	
	@FXML
	private Button btn_back;
	
	@FXML
	private Button btn_clear;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	void shareData(Car car) {
		selectedCar = car;
		txt_make.setText(selectedCar.getMake());
		txt_model.setText(selectedCar.getModel());
		
	}
	
	
	@FXML
	public void searchCustomerID(MouseEvent e) {
		
		DatabaseHandler databaseHandler = new DatabaseHandler();
		Customer customer = databaseHandler.retrieveCustomerRecord(txt_id.getText());
		
		txt_firstname.setText(customer.getFirstName());
		txt_lastname.setText(customer.getLastName());
		txt_address.setText(customer.getAddress());
		txt_phone.setText(customer.getPhone());
	}
	
	@FXML
	public void sellCar(MouseEvent e) {
		
		
		
		
	}
	
	@FXML
	public void backButton(MouseEvent e) {
		Node  source = (Node)  e.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	public void clearTextBoxCustomer(MouseEvent e) {
		txt_firstname.clear();
		txt_lastname.clear();
		txt_address.clear();
		txt_phone.clear();
	}
	
	
	

}
