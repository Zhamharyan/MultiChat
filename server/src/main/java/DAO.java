import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
    private Statement statement;
    public DAO(){
        try {
            Connection root = DriverManager.getConnection("jdbc:mariadb://localhost:3306/chat_db", "root", "123456");
            statement = root.createStatement();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public Statement getStatement(){
        return statement;
    }
}
