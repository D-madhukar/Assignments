import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConnector {
	public static final String  DRIVER="com.mysql.jdbc.Driver";
	public static final String  URL="jdbc:mysql://localhost:3306/emp_db";
	public static final String  USERNAME="root";
	public static final String  PASSWORD="root";
	public Connection getConnection()throws Exception {
		Class.forName(DRIVER);
		Connection connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
		return connection;
	}
}
