package a_Zadania.a_Dzien_2.f_java_i_mysql;

import java.math.BigDecimal;
import java.sql.*;

public class DBUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password!2";

    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";

    private static String avgQuery = "Select AVG(rating) as avg from cinemas_ex.movies";


    public static Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        return conn;
    }

    public static void insert(Connection conn, String query, String... params) {
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printData(Connection conn, String query, String... columnNames) throws SQLException {

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                for (String param : columnNames) {
                    System.out.println(resultSet.getString(param));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printMovies(Connection conn, String query, String param, String... columnNames) throws SQLException {

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, param);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                for (String column : columnNames) {
                    System.out.println(resultSet.getString(column));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BigDecimal getMoviesAverage(Connection conn) {
        try (PreparedStatement statement =
                     conn.prepareStatement(avgQuery)) {

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("avg");
            }
            return new BigDecimal("-1");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
