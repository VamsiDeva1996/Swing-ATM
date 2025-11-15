import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static  String URL = "jdbc:mysql://localhost:3306/atm_db1";
    static  String USER = "root";
    static  String PASSWORD = "Vamsi9398@";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return
                    DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

