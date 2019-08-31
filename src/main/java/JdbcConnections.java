import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public enum JdbcConnections {

    H2("org.h2.Driver", "jdbc:h2:mem:testdb", "sa", "");

    private String url;
    private String user;
    private String password;
    private Connection connection;

    JdbcConnections(String driver, String url, String user, String password) {

        this.url = url;
        this.user = user;
        this.password = password;

        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public Connection get() throws SQLException {

        if (connection == null) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }

    public void init() throws SQLException {
        String createTable = "CREATE TABLE user(" +
                "id int primary key, " +
                "login varchar(20), " +
                "pass varchar(20)" +
                ")";

        Statement st = get().createStatement();
        st.execute(createTable);
        int count = 0;

        count += st.executeUpdate("INSERT INTO user VALUES (1, 'Kazik', '1234')");
        count += st.executeUpdate("INSERT INTO user VALUES (2, 'Wanda', '1233')");
        count += st.executeUpdate("INSERT INTO user VALUES (3, 'Aleks', '1232')");
        count += st.executeUpdate("INSERT INTO user VALUES (4, 'Leonard', '1231')");
        count += st.executeUpdate("INSERT INTO user VALUES (5, 'Aleksandra', '1221')");

        if (count == 5) {
            System.out.println("Wszystkie wiersze zostały dodane. ");
        }
    }
}
