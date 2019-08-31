import java.sql.*;


public class JdbcDemo {


    public static void main(String[] args) throws
            ClassNotFoundException,
            SQLException,
            IllegalAccessException,
            InstantiationException {

        //ładowanie sterownika bazy
        Class.forName("org.h2.Driver").newInstance();
        //tworzenie połączenia  bazy przy pomocy Driver Menagera
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        //wyłączamy autocommit
        connection.setAutoCommit(false);

        System.out.println(connection.getAutoCommit());
        //Tworzenie obiektu polecenia SQL
        Statement statement = connection.createStatement();

        String createTable = "CREATE TABLE user(" +
                "id int primary key, " +
                "login varchar(20), " +
                "pass varchar(20)" +
                ")";

        statement.executeUpdate(createTable);

        //początek statementu

        statement.executeUpdate("INSERT INTO user VALUES (1, 'Kazik', '1234')");
        //odwołujeemy zmiany po savepont
        connection.rollback();
        ResultSet set = statement.executeQuery("SELECT * FROM user");

        while (set.next()) {

            System.out.println(set.getString("login"));
        }

        connection.close();
    }
}
