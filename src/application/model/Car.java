package application.model;

public class Car {
	
	private String make;
	
	private String model;
	
	private String year;
	
	private String kms;
	
	private String price;
	
	private String status;
	
	private int sold;

	private int id;
	
	public Car() {
		
	}
	
	public Car(String make, String model, String year, String kms, String price, String status ) {
		this.make = make;
		
		this.model = model;
		
		this.year = year;
		
		this.kms = kms;
		
		this.price = price;
		
		this.status = status;
		
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getKms() {
		return kms;
	}

	public void setKms(String kms) {
		this.kms = kms;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}


}
