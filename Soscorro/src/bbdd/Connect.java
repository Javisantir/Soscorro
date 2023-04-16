package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.naming.NamingContext;

public class Connect {
    private static Connect instance;
    private DataSource ds;
	private Connection connection;
    
    private Connect () 
    {
    	InitialContext ctx;
		try 
		{
			ctx = new InitialContext();
			NamingContext envCtx = (NamingContext) ctx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/Soscorro");
			connection = ds.getConnection();
		} 
		catch (NamingException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
    }
    
    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }
    
    public Connection getConnection() 
    {
        return connection;
    }
}
