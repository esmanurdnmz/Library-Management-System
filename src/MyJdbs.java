// MyJdbs.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyJdbs {
    public static Connection veritabanıBaglan() {
        Connection cnc = null;
        try {
            cnc = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library",
                    "root",
                    ""
            );
            System.out.println("Bağlandı");
        } catch (SQLException e) {
            System.out.println("Bağlantı hatası:");
            e.printStackTrace();
        }
        if (cnc == null) {
            System.out.println("Connection nesnesi null döndü.");
        }
        return cnc;
    }
}
