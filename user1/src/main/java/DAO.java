import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
    private Statement statement;
    public DAO() throws SQLException {
        Connection connection = DriverManager.getConnection("");
        statement = connection.createStatement();
    }
}
