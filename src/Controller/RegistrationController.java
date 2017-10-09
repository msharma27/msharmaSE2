/**
 * 
 */
package Controller;
import java.lang.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import POJO.User;

/**
 * @author mshar
 *
 */
@ManagedBean(name="registrationController")
@SessionScoped
public class RegistrationController {
	private String fname;
	private String lname;
	private String email;
	private String username;
	private String password;
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public RegistrationController() {
		
	}
	
	public String registerUser() {

		Connection con = null;
		try {
			String servername=System.getenv("ICSI518_SERVER");
			int portnumber = Integer.parseInt(System.getenv("ICSI518_PORT"));
			String dbname=System.getenv("ICSI518_DB");
			String serverusername=System.getenv("ICSI518_USER");
			String serverpassword=System.getenv("ICSI518_PASSWORD");

			// Setup the DataSource object
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName(servername);
			ds.setPortNumber(portnumber);
			ds.setDatabaseName(dbname);
			ds.setUser(serverusername);
			ds.setPassword(serverpassword);

			// Get a connection object
			con = ds.getConnection();

			// Get a prepared SQL statement
			String sql = "SELECT fname from user where username = ?";
			PreparedStatement st = con.prepareStatement(sql);
//			st.setInt(1, this.id);
			System.out.println(this.username+" is the username");
			
			st.setString(1, this.username);

			// Execute the statement
			ResultSet rs = st.executeQuery();

			// Iterate through results
			if (rs.next()) {
				System.out.println("User already exists. Please Register with different details! ");
				return "userexistregister";
			}
			else {
				String sql2="insert into user(fname, lname, email, username, password) values (?,?,?,?,?)";
				PreparedStatement st1 = con.prepareStatement(sql2);
//				st.setInt(1, this.id);
				st1.setString(1, this.fname);
				st1.setString(2, this.lname);
				st1.setString(3, this.email);
				st1.setString(4, this.username);
				st1.setString(5, this.password);
				st1.executeUpdate();
				st1.close();	

			}
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return "slogin";
	}
	
	

	
}
