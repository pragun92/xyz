package application.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.model.Car;
import application.model.Customer;
import application.model.Sale;
import application.model.SalesRecord;
import application.model.User;
import application.utils.DatabaseConnection;

public class DatabaseHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHandler.class);
	
	private static final String SELECT_USER_RECORD = "SELECT * FROM Users WHERE username=?";
	
	private static final String SELECT_SALES_USER_RECORD = "SELECT * FROM Users WHERE role != 1";
	
	private static final String SELECT_CAR_RECORD = "SELECT * FROM Cars";
	
	private static final String INSERT_NEW_CAR_RECORD = "INSERT INTO cars (Make, Model, Year, kms, price, Status, sold) VALUES (?, ?, ?, ?, ?, ?, ?);";
	
	private static final String SEARCH_CUSTOMER_RECORD = "SELECT * FROM CUSTOMERS WHERE firstname = ? and lastname = ? and phone = ?";
	
	private static final String INSERT_NEW_CUSTOMER_RECORD = "INSERT INTO customer (firstname, lastname, address, phone) VALUES (?, ?, ?, ?);";
	
	private static final String INSERT_NEW_SALE_RECORD = "INSERT INTO sales (CustomerID, UserID, CarID, Date) VALUES (?, ?, ?, ?);";
	
	private static final String DELETE_CAR_RECORD = "DELETE from cars where id = ?";
	
	private static final String SELECT_CUSTOMER_RECORD = "SELECT * FROM Customers WHERE customerID=?";
	
	private static final String UPDATE_CAR_RECORD = "UPDATE CARS SET MAKE = ?, MODEL = ?, YEAR = ?, KMS = ?, PRICE = ?, STATUS = ? WHERE ID = ?";
	
	public User retrieveUserRecord(String username) {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		User user = new User();
		
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("SELECT_USER_RECORD query {} ", SELECT_USER_RECORD);
			}

			conn = connection.connect();
			preparedStatement = conn.prepareStatement(SELECT_USER_RECORD);

			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				LOGGER.info("User is registered");
				user.setUserId(resultSet.getInt("Id"));
				user.setAddress(resultSet.getString("Address"));
				user.setEmail(resultSet.getString("Email"));
				user.setFirstName(resultSet.getString("FirstName"));
				user.setLastName(resultSet.getString("LastName"));
				user.setPhone1(resultSet.getString("Phone1"));
				user.setUsername(resultSet.getString("Username"));
				user.setPhone2(resultSet.getString("Phone2"));
				user.setRole(resultSet.getInt("Role"));
				user.setStatus(resultSet.getInt("Status"));
				user.setPassword(resultSet.getString("Password"));
				return user;
			}else {
				LOGGER.info("Username does not exist");
				return null;
			}

		}catch (Exception e) {
			LOGGER.error("Error in verifyLoginCredentials", e);
			return null;
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (resultSet != null)
					resultSet.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}

	}
	
	public ArrayList<User> retrieveSalesUserRecord() {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		ArrayList<User> userList = new ArrayList<User>();
		
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("SELECT_SALES_USER_RECORD query {} ", SELECT_SALES_USER_RECORD);
			}

			conn = connection.connect();
			preparedStatement = conn.prepareStatement(SELECT_SALES_USER_RECORD);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("Id"));
				user.setAddress(resultSet.getString("Address"));
				user.setEmail(resultSet.getString("Email"));
				user.setFirstName(resultSet.getString("FirstName"));
				user.setLastName(resultSet.getString("LastName"));
				user.setPhone1(resultSet.getString("Phone1"));
				user.setUsername(resultSet.getString("Username"));
				user.setPhone2(resultSet.getString("Phone2"));
				user.setRole(resultSet.getInt("Role"));
				user.setStatus(resultSet.getInt("Status"));
				user.setPassword(resultSet.getString("Password"));
				userList.add(user);
			}
			return userList;
		}catch (Exception e) {
			LOGGER.error("Error in retrieveSalesUserRecord", e);
			return null;
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (resultSet != null)
					resultSet.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}

	}
	
	public ArrayList<Car> retrieveAllCars() {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		

		ArrayList<Car> carList = new ArrayList<Car>();
		
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("SELECT_CAR_RECORD query {} ", SELECT_CAR_RECORD);
			}

			conn = connection.connect();
			preparedStatement = conn.prepareStatement(SELECT_CAR_RECORD);

			resultSet = preparedStatement.executeQuery();

			if(null != resultSet) {
				while (resultSet.next()) {
					LOGGER.info("Retrieving all cars");
					Car car = new Car();
					car.setId(resultSet.getInt("ID"));
					car.setKms(resultSet.getString("Kms"));
					car.setMake(resultSet.getString("Make"));
					car.setModel(resultSet.getString("Model"));
					car.setPrice(resultSet.getString("Price"));
					car.setStatus(resultSet.getString("Status"));
					car.setYear(resultSet.getString("Year"));
					car.setSold(resultSet.getInt("Sold"));
					carList.add(car);
					LOGGER.info("Size of list: {}", carList.size());
				}
			}else {
				return null;
			}
			

		}catch (Exception e) {
			LOGGER.error("Error in retrieveAllCars", e);
			return null;
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (resultSet != null)
					resultSet.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}
		return carList;

	}
	
	
	public boolean insertCar(Car car) {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("INSERT_NEW_CAR_RECORD query {} ", INSERT_NEW_CAR_RECORD);
			}

			conn = connection.connect();
			preparedStatement = conn.prepareStatement(INSERT_NEW_CAR_RECORD);
			preparedStatement.setString(1, car.getMake());
			preparedStatement.setString(2, car.getModel());
			preparedStatement.setString(3, car.getYear());
			preparedStatement.setString(4, car.getKms());
			preparedStatement.setString(5, car.getPrice());
			preparedStatement.setString(6, car.getStatus());
			preparedStatement.setInt(7, 0);
			boolean added = preparedStatement.execute();
			
			if(added) {
				LOGGER.info("Car {} {}added", car.getMake(), car.getModel());
				return true;
			}

		}catch (Exception e) {
			LOGGER.error("Error in insertCar", e);
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}
		return false;

	}
	
	public boolean deleteCar(Car car) {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("DELETE_CAR_RECORD query {} ", DELETE_CAR_RECORD);
			}

			conn = connection.connect();
			preparedStatement = conn.prepareStatement(DELETE_CAR_RECORD);
			preparedStatement.setInt(1, car.getId());
			boolean added = preparedStatement.execute();
			
			if(added) {
				LOGGER.info("Car {} {} deleted", car.getMake(), car.getModel());
				return true;
			}

		}catch (Exception e) {
			LOGGER.error("Error in deleteCar", e);
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}
		return false;

	}
	public boolean updateCar(Car car) {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		//
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("UPDATE_CAR_RECORD query {} ", UPDATE_CAR_RECORD);
			}

			conn = connection.connect();
			preparedStatement = conn.prepareStatement(UPDATE_CAR_RECORD);
			preparedStatement.setString(1, car.getMake());
			preparedStatement.setString(2, car.getModel());
			preparedStatement.setString(3, car.getYear());
			preparedStatement.setString(4, car.getKms());
			preparedStatement.setString(5, car.getPrice());
			preparedStatement.setString(6, car.getStatus());
			preparedStatement.setInt(7, car.getId());
			boolean updated = preparedStatement.execute();
			
			if(updated) {
				LOGGER.info("Car {} {} updated", car.getMake(), car.getModel());
				return true;
			}
			
		}catch (Exception e) {
			LOGGER.error("Error in updateCar", e);
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}
		return false;

	}
	
	public Customer retrieveCustomerRecord(String id) {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Customer customer = new Customer();
		
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("SELECT_CUSTOMER_RECORD query {} ", SELECT_CUSTOMER_RECORD);
			}

			conn = connection.connect();
			preparedStatement = conn.prepareStatement(SELECT_CUSTOMER_RECORD);

			preparedStatement.setString(1, id);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				LOGGER.info("Customer found");
				
				customer.setFirstName(resultSet.getString("firstname"));
				customer.setId(resultSet.getInt("customerID"));
				customer.setLastName(resultSet.getString("lastname"));
				customer.setPhone(resultSet.getString("phone"));
				customer.setAddress(resultSet.getString("address"));
				return customer;
			}else {
				LOGGER.info("Customer does not exist");
				return null;
			}

		}catch (Exception e) {
			LOGGER.error("Error in retrieveCustomerRecord", e);
			return null;
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (resultSet != null)
					resultSet.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}

	}
	
	public boolean insertSales(Sale sale) {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("INSERT_NEW_SALE_RECORD query {} ", INSERT_NEW_SALE_RECORD);
			}
			
			conn = connection.connect();
			preparedStatement = conn.prepareStatement(INSERT_NEW_SALE_RECORD);
			preparedStatement.setInt(1, sale.getCustomerId());
			preparedStatement.setInt(2, sale.getUserId());
			preparedStatement.setInt(3, sale.getCarId());
			preparedStatement.setDate(4, sale.getDate());
			boolean added = preparedStatement.execute();
			
			if(added) {
				LOGGER.info("Car {} sold to {}", sale.getCarId(), sale.getCustomerId());
				return true;
			}

		}catch (Exception e) {
			LOGGER.error("Error in insertSales", e);
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}
		return false;

	}
	
	
	/*public boolean insertCustomer(Customer customer) {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("INSERT_NEW_SALE_RECORD query {} ", INSERT_NEW_SALE_RECORD);
			}
			
			conn = connection.connect();
			preparedStatement = conn.prepareStatement(INSERT_NEW_SALE_RECORD);
			preparedStatement.setInt(1, sale.getCustomerId());
			preparedStatement.setInt(2, sale.getUserId());
			preparedStatement.setInt(3, sale.getCarId());
			preparedStatement.setDate(4, sale.getDate());
			boolean added = preparedStatement.execute();
			
			if(added) {
				LOGGER.info("Car {} sold to {}", sale.getCarId(), sale.getCustomerId());
				return true;
			}

		}catch (Exception e) {
			LOGGER.error("Error in insertSales", e);
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}
		return false;

	}*/
	
	
	public ArrayList<SalesRecord> retrieveSaleRecords() {
		DatabaseConnection connection = DatabaseConnection.getInstance();

		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		 
		StringBuilder salesQuery = new StringBuilder();
		ArrayList<SalesRecord> saleRecordList = new ArrayList<SalesRecord>();
		
		salesQuery.append("SELECT saleId, sales.customerID as custID, sales.userId as usrID, carId, date, customers.firstname as custFN, customers.lastname as custLN, ");
		salesQuery.append("cars.make as cmk, cars.model as cmd, cars.price as price, users.firstname as usrfn, users.username as usrn, users.lastname as usrln ");
		salesQuery.append("FROM sales ");
		salesQuery.append("INNER JOIN customers ON sales.customerID = customers.customerid ");
		salesQuery.append("INNER JOIN cars ON sales.carId = cars.id ");
		salesQuery.append("INNER JOIN users ON sales.userId = users.id ");
		salesQuery.append("ORDER BY saleId ");
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("SELECT_CUSTOMER_RECORD query {} ", salesQuery.toString());
			}

			conn = connection.connect();
			preparedStatement = conn.prepareStatement(salesQuery.toString());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				SalesRecord salesRecord = new SalesRecord();
				
				salesRecord.setCar_make(resultSet.getString("cmk"));
				salesRecord.setCar_model(resultSet.getString("cmd"));
				salesRecord.setCarId(resultSet.getString("carId"));
				salesRecord.setCustomerFirstName(resultSet.getString("custFN"));
				salesRecord.setCustomerId(resultSet.getString("custID"));
				salesRecord.setCustomerLastName(resultSet.getString("custLN"));
				salesRecord.setPrice(resultSet.getString("price"));
				salesRecord.setSale_date(null);
				salesRecord.setSaleId(resultSet.getString("saleId"));
				salesRecord.setUserFirstName(resultSet.getString("usrfn"));
				salesRecord.setUserId(resultSet.getString("usrID"));
				salesRecord.setUserLastName(resultSet.getString("usrln"));
				salesRecord.setUsername(resultSet.getString("usrn"));

				saleRecordList.add(salesRecord);
			}

		}catch (Exception e) {
			LOGGER.error("Error in retrieveCustomerRecord", e);
			
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (resultSet != null)
					resultSet.close();
				if (connection != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.error("Exception", e);
			}
		}
		LOGGER.info("Size of list {}", saleRecordList.size());
		return saleRecordList;	
	}
	
	


	public static void main(String[] args) {
		DatabaseHandler databaseHandler = new DatabaseHandler();
		
		databaseHandler.retrieveSaleRecords();
	}

}
