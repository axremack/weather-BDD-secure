package Database;

import java.sql.*;
import java.util.Date;
import java.util.List;

import static java.lang.String.valueOf;

public final class DBManager {
    private static String url;

    public DBManager(String url_given) {
        DBManager.url = url_given;
    }

    public static void createWeatherTable() throws SQLException, ClassNotFoundException {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS weather (\n")
                .append("fetched_at INTEGER NOT NULL,\n")
                .append("city VARCHAR(100) PRIMARY KEY NOT NULL,\n")
                .append("current_temperature DOUBLE NOT NULL,\n")
                .append("wind_speed DOUBLE\n")
                .append(");\n");

        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(url);
        if (conn != null) {
            Statement s = conn.createStatement();
            s.execute(query.toString());

            s.close();
        }
        conn.close();
    }

    public static void insertValues(List<Object> list_values) throws SQLException, ClassNotFoundException {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO weather(\n")
                .append("fetched_at,\n")
                .append("city,\n")
                .append("current_temperature,\n")
                .append("wind_speed\n")
                .append(")\n")
                .append("VALUES(?,?,?,?);");

        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(url);
        if (conn != null) {
            PreparedStatement s = conn.prepareStatement(query.toString());
            s.setInt(1, (int) list_values.get(0));
            s.setString(2, (String) list_values.get(1));
            s.setDouble(3, (double) list_values.get(2));
            s.setDouble(4, (double) list_values.get(3));
            s.executeUpdate();
            System.out.println("Values have been added to database");
            s.close();
        }
        conn.close();
    }

    public static void dropTable() throws SQLException {
        String query = "DROP TABLE IF EXISTS weather;";
        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
        s.execute(query);

        conn.close();
        s.close();
    }

    public static void displayDB() throws SQLException {
        String query = "SELECT fetched_at, city, current_temperature, wind_speed FROM weather;";
        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(query);

        while (rs.next()) {
            System.out.println(rs.getInt("fetched_at") + " - " +
                    rs.getString("city") + " - " +
                    rs.getDouble("current_temperature") + " - " +
                    rs.getDouble("wind_speed"));
        }

        conn.close();
        s.close();
        rs.close();
    }

    public static void displayDBOrderedBy(String param) throws SQLException {
        String query = "SELECT fetched_at, city, current_temperature, wind_speed FROM weather ORDER BY " + param + ";";
        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(query);

        while (rs.next()) {
            System.out.println(rs.getInt("fetched_at") + " - " +
                    rs.getString("city") + " - " +
                    rs.getDouble("current_temperature") + " - " +
                    rs.getDouble("wind_speed"));
        }

        conn.close();
        s.close();
        rs.close();
    }

    public static void deleteOldData() throws SQLException {
        long epochDate = System.currentTimeMillis()/1000 - 86400; // 1 day before today
        String query = "DELETE FROM weather WHERE fetched_at < " + epochDate + ";";
        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
        s.execute(query);

        conn.close();
        s.close();

    }

    public static boolean findInDB(String city) throws SQLException {
        String query = "SELECT fetched_at, city, current_temperature, wind_speed FROM weather WHERE city LIKE \"%" + city + "%\";";
        boolean found = false;

        Connection conn = DriverManager.getConnection(url);
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(query);

        while (rs.next()) {
            StringBuilder summary = new StringBuilder();
            summary.append("Weather fetched at : ").append(new Date(Long.parseLong(valueOf(rs.getInt("fetched_at"))) * 1000)).append("\n")
                    .append("Weather for city : ").append(rs.getString("city")).append("\n")
                    .append("\tCurrent temperature : ").append(rs.getDouble("current_temperature")).append("°C\n")
                    .append("\tWind speed : ").append(rs.getDouble("wind_speed")).append(" m/s\n");
            System.out.println(summary);

            found = true;
        }

        conn.close();
        s.close();
        rs.close();


        return found;
    }
}
