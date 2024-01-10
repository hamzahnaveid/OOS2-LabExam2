/*
 * Author: Hamzah Naveid (C22428264)
 */

public class Product {
	protected String code;
	protected String name;
	protected String category;
	protected double price;
	
	public Product(String co, String n, String ca, double p) {
		this.code = co;
		this.name = n;
		this.category = ca;
		this.price = p;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String toString() {
		return "Code: " + this.code + " | Name: " + this.name + " | Category: " + this.category + " | Price: " + this.price;
	}
	
	
	

}
