import java.sql.*;
import java.util.Scanner;

public class SqlInjectionDemo {

    public static void main(String[] args) throws SQLException {
        JdbcConnections.H2.init();
        Connection con = JdbcConnections.H2.get();

        Statement st = con.createStatement();
        printAll(st);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz id usera ");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            print(con, id);
        } else {
            System.out.println("Wpisz poprawną wartość całkowitą! ");
        }
        System.out.println("Wpisz login usera ");
        if (scanner.hasNext()) {
            String login = scanner.next();
            printByLogin(con, login);
        } else {
            System.out.println("Podaj poprawny login");
        }

    }

    private static void printAll(Statement st) throws SQLException {
        ResultSet set = st.executeQuery("SELECT * FROM user");
        while (set.next()) {
            System.out.println(set.getString("login"));

        }
    }

    private static void print(Connection con, int id) throws SQLException {
        //w miejscu znaku zapytania
        PreparedStatement st = con.prepareStatement("SELECT * FROM user WHERE id= ?");
        st.setInt(1, id);
        ResultSet set = st.executeQuery();
        while (set.next()) {
            System.out.println(set.getString("login"));

        }


    }

    private static void printByLogin(Connection con, String login) throws SQLException {
        PreparedStatement st = con.prepareStatement("SELECT * FROM user WHERE login like ?");
        st.setString(1, login);

        ResultSet set = st.executeQuery();
        while (set.next()) {
            System.out.println(set.getString("login"));

        }


    }


}
