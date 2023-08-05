package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.handlers.DatabaseHandler;
import application.model.Car;
import application.model.SalesRecord;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SalesScreenController implements Initializable{
	
	@FXML
	private TextField btn_searchSales;
	
	@FXML
	private TextField txt_user;
	
	@FXML
	private TableView<SalesRecord> tbl_sales;
	
	@FXML
	private TableColumn<SalesRecord, String> col_saleId;
	
	@FXML
	private TableColumn<SalesRecord, String> col_custFn;
	
	@FXML
	private TableColumn<SalesRecord, String> col_custLn;
	
	@FXML
	private TableColumn<SalesRecord, String> col_carMake;
	
	@FXML
	private TableColumn<SalesRecord, String> col_carModel;
	
	@FXML
	private TableColumn<SalesRecord, String> col_carPrice;
	
	@FXML
	private TableColumn<SalesRecord, String> col_username;
	
	private final ObservableList<SalesRecord> obs_sales_list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		/*DatabaseHandler databaseHandler = new DatabaseHandler();
		obs_sales_list.clear();
		ArrayList<User> userlist = databaseHandler.retrieveSalesUserRecord();
		int index = 0;
		for(User userData: userlist) {
			chbx_user.getItems().add(index, userData.getUsername());
		}*/
		
		displaySalesData();

	}
	
	
	public void displaySalesData() {
		obs_sales_list.clear();
		DatabaseHandler databaseHandler = new DatabaseHandler();
		col_saleId.setCellValueFactory(new PropertyValueFactory<>("saleId"));
		col_custFn.setCellValueFactory(new PropertyValueFactory<>("customerFirstName"));
		col_custLn.setCellValueFactory(new PropertyValueFactory<>("customerLastName"));
		col_carMake.setCellValueFactory(new PropertyValueFactory<>("car_make"));
		col_carModel.setCellValueFactory(new PropertyValueFactory<>("car_model"));
		col_carPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		col_username.setCellValueFactory(new PropertyValueFactory<>("Username"));
		
		ArrayList<SalesRecord> salesListOfSales = databaseHandler.retrieveSaleRecords();
		
		obs_sales_list.addAll(salesListOfSales);
		
		FilteredList<SalesRecord> filteredData = new FilteredList<>(obs_sales_list, b -> true);
		
		if(null!=txt_user.getText()) {
			
			txt_user.textProperty().addListener((observable, oldValue, newValue) ->{
				filteredData.setPredicate(salesRecord -> {
					if(null == newValue || newValue.isEmpty()) {
						return true;
					}
					
					String lowerCaseFilter = newValue.toLowerCase();
					
					if(salesRecord.getCar_make().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					}else if(salesRecord.getCar_model().toLowerCase().indexOf(lowerCaseFilter) != -1){
						return true;
					}else if(salesRecord.getCustomerFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1){
						return true;
					}else if(salesRecord.getCustomerLastName().toLowerCase().indexOf(lowerCaseFilter) != -1){
						return true;
					}else if(salesRecord.getUserFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1){
						return true;
					}else if(salesRecord.getUserLastName().toLowerCase().indexOf(lowerCaseFilter) != -1){
						return true;
					}else if(salesRecord.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1){
						return true;
					}else {
						return false;
					}
				});
			});
		}
		
		SortedList<SalesRecord> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tbl_sales.comparatorProperty());
		tbl_sales.setItems(sortedData);
	}

}
