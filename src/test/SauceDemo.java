package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//import DbConnection;

public class SauceDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.setProperty("webdriver.gecko.driver", "geckodriver");
//		System.setProperty("webdriver.chrome.driver", "chromedriver");
//		WebDriver driver = new FirefoxDriver();
		WebDriver driver = new ChromeDriver();
		
//		getting driver handle 
		driver.get("https://www.saucedemo.com/");
		
//		managing window size 
		driver.manage().window().maximize();
//		implicit wait
		driver.manage().timeouts().implicitlyWait(4000, TimeUnit.MILLISECONDS);
		
		DbConnection conn;
		String username = "" ;
		String password = "";
		String product ="";
		float price = 0 ;
		try {
			conn = new DbConnection("jdbc:mysql://localhost:3306/ecomm","root","root");
			Statement stmt1 = conn.getConnection().createStatement();
			Statement stmt2 = conn.getConnection().createStatement();
			
		       //stmt.executeUpdate("insert into eproduct (name, price, date_added) values ('IPad', 17800.00, now())");
		       ResultSet rst = stmt1.executeQuery("select * from eproduct");
		       ResultSet creds = stmt2.executeQuery("select uname,password from login where id = 1 ");
		       
		      
		       
		       while (creds.next()) {
		    	   username = creds.getString("uname");
		    	   password = creds.getString("password");
		    	   
		    	   
		       }
		       while(rst.next()) {
		    	   product = rst.getString("name");
		    	   price = rst.getFloat("price");
		       }
		       
		       stmt1.close();
		       stmt2.close();

		       
		       conn.closeConnection();
		       
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		getting element by different locator
		
		WebElement UserName = driver.findElement(By.xpath("//input[@id='user-name']"));
		UserName.sendKeys(username);
//		
		WebElement Password = driver.findElement(By.xpath("//input[@id='password']"));
		Password.sendKeys(password);
//		
		WebElement loginlink = driver.findElement(By.xpath("//input[@id='login-button']"));
		loginlink.click();
		
		WebElement captured_product = driver.findElement(By.xpath("//a/div[starts-with(text(),'"+ product +"')]"));
		
		captured_product.click();
		
		
		
		WebElement add = driver.findElement(By.xpath("//button[text()='Add to cart']"));
		add.click();
		
		WebElement  cart_summ = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
		cart_summ.click();
		//div[@class = 'inventory_item_name']
		WebElement cart_product  = driver.findElement(By.xpath("//div[@class = 'inventory_item_name']"));
		WebElement cart_price = driver.findElement(By.xpath("//div[@class = 'inventory_item_price']"));
//		System.out.println(cart_price.getText().substring(1));
		Float cartprice = Float.parseFloat(cart_price.getText().substring(1));

		
		if(cart_product.getText().equals(product) && cartprice.equals(price)) {
			System.out.println("Verified");
			
		}
		
		
		
		
		

	}

}
