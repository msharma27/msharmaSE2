/**
 * 
 */
package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * @author mshar
 *
 */
@ManagedBean(name="loginController")
@RequestScoped
public class LoginController {
	private String fname;
	private String username;
	private String password;
	private String sessionFname;
	public String getSessionFname() {
		return sessionFname;
	}
	public void setSessionFname(String sessionFname) {
		this.sessionFname = sessionFname;
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
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public LoginController() {
		
	}
	
	public String loginUser() {
		
		Connection con=null;
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
			// Setup the DataSource object
//			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
//			ds.setServerName("localhost");
//			ds.setPortNumber(3306);
//			ds.setDatabaseName("assignment2");
//			ds.setUser("root");
//			ds.setPassword("root");

			// Get a connection object
			con = ds.getConnection();

			// Get a prepared SQL statement
			String sql = "SELECT fname from user where username = ? and password = ?";
			PreparedStatement st = con.prepareStatement(sql);
//			st.setInt(1, this.id);
			System.out.println(this.username+" is the username");
			
			st.setString(1, this.username);
			st.setString(2, this.password);

			// Execute the statement
			ResultSet rs = st.executeQuery();

			// Iterate through results
			if (rs.next()) {
				//this.sessionFname=this.username;
				System.out.println("Valid Details");
				this.sessionFname=rs.getString("fname");
				//System.out.println(this.fname);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessionFname",sessionFname);
				//System.out.println(sessionUsername);
				//System.out.println(this.sessionUsername);
				return "mainpage?faces-redirect=true";
			}
			else {
				return "login";
			}

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return "login";
	}

	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login";
	}
}
