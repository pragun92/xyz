package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.handlers.DatabaseHandler;
import application.model.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCarController implements Initializable{
	

	private static final Logger LOGGER = LoggerFactory.getLogger(AddCarController.class);
	
	
	@FXML
	private TextField txt_make;
	
	@FXML
	private TextField txt_model;
	
	@FXML
	private TextField txt_year;
	
	@FXML
	private TextField txt_kms;

	@FXML
	private TextField txt_price;
	
	@FXML
	private TextField txt_status;
	
	@FXML
	private TextField txt_imagePath;
	
	@FXML
	private Button btn_add;

	@FXML
	private Button btn_clear;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

	public void addNewCar(ActionEvent e) throws IOException {
		
		LOGGER.info("Adding new car {}", txt_model.getText());
		
		DatabaseHandler databaseHandler = new DatabaseHandler();
		
		Car newCar = new Car();
		
		newCar.setKms(txt_kms.getText());
		newCar.setMake(txt_make.getText());
		newCar.setModel(txt_model.getText());
		newCar.setPrice(txt_price.getText());
		newCar.setStatus(txt_status.getText());
		newCar.setYear(txt_year.getText());
		
		databaseHandler.insertCar(newCar);
		

	}
	
	public void clearTextBoxes(ActionEvent e) throws IOException {
		
		LOGGER.info("Clearing text");
		
		txt_imagePath.clear();
		txt_kms.clear();
		txt_make.clear();
		txt_model.clear();
		txt_price.clear();
		txt_status.clear();
		txt_year.clear();
		
	
	}
}