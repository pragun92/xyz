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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CarShowUpdateDetailsController implements Initializable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CarShowUpdateDetailsController.class);
	
	@FXML
	private TextField txt_make;

	@FXML
	private TextField txt_model;
	
	@FXML
	private TextField txt_price;
	
	@FXML
	private TextField txt_year;
	
	@FXML
	private TextField txt_kms;
	
	@FXML
	private TextField txt_status;
	
	@FXML
	private ImageView img_v_car;
	
	@FXML
	private Button btn_back;
	
	@FXML
	private Button btn_update;
	
	
	private Car selectedCar;
	
	public void parseData(Car car) {
		selectedCar = car;
		txt_make.setText(car.getMake());
		txt_model.setText(car.getModel());
		txt_price.setText(car.getPrice());
		txt_year.setText(car.getYear());
		txt_kms.setText(car.getKms());
		txt_status.setText(car.getStatus());
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void backButton(ActionEvent e) throws IOException {
		LOGGER.info("Back press");
		Node  source = (Node)  e.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
		
	}
	
	public void updateCar(ActionEvent e) throws IOException {

		LOGGER.info("Updating car {}", selectedCar.getId());
		
		DatabaseHandler databaseHandler = new DatabaseHandler();
		
		databaseHandler.updateCar(selectedCar);
		
	}
	
	

	public Car getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}

}
