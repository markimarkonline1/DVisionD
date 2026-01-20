package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect
{
    private Connection con;
    private static final String HOST = "jdbc:mysql://127.0.0.1:3306/db_filme";
    private static final String USERNAME = "DVisionD";
    private static final String PASSWORD = "";

    private static DbConnect instance = null;
    private DbConnect()
    {

        try
        {
            con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

        public static DbConnect getInstance() {
            if (instance == null)
            {
                instance = new DbConnect();
            }
            return instance;
        }

    public Connection connection(){return con;}
}
