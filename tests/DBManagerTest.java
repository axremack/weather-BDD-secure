import Database.DBManager;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManagerTest extends TestCase {

    private String url = "jdbc:sqlite:tests/test.db";
    private DBManager d;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private List<String> listColumns = new ArrayList<>(){{
        add("fetched_at");
        add("city");
        add("current_temperature");
        add("wind_speed");
    }};

    private List<String> listTypes = new ArrayList<>(){{
        add("INTEGER");
        add("VARCHAR(100)");
        add("DOUBLE");
        add("DOUBLE");
    }};

    private List<Object> listValues = new ArrayList<>(){{
        add(3);
        add("city");
        add(20.0);
        add(3.0);
    }};


    // Les deux méthodes suivantes servent à empêcher les effets de bord
    @Override
    protected void setUp () throws Exception {
        super.setUp();
        System.setOut(new PrintStream(outContent));
        d = new DBManager(url);
        d.createWeatherTable();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        d.dropTable();
    }

    @Test
    public void testTableCreation() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet rs_debug = metaData.getTables(null, null, "weather", null);
                String nomTable = rs_debug.getString (3);
                assertEquals("weather", nomTable);

                ResultSet columns = metaData.getColumns(null, null, "weather", "%");
                int i = 0;
                while (columns.next()){
                    String column_name = columns.getString("COLUMN_NAME");
                    String column_type = columns.getString("TYPE_NAME");
                    assertEquals(listColumns.get(i), column_name);
                    assertEquals(listTypes.get(i), column_type);
                    i++;
                }
                rs_debug.close();
                columns.close();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testValueInsertion() {
        try {
            d.insertValues(listValues);
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery ("SELECT * FROM weather");

                while (rs.next()) {
                    assertEquals(listValues.get(0), rs.getInt(1));
                    assertEquals(listValues.get(1), rs.getString(2));
                    assertEquals(listValues.get(2), rs.getDouble(3));
                    assertEquals(listValues.get(3), rs.getDouble(4));
                }

                s.close();
                rs.close();
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Exception thrown");
        }
    }


    @Test
    public void testInvalidValueInsertion() {
        List<Object> listInvalidValues = new ArrayList<>(){{
            add("Not a number");
            add("city2");
            add(21.0);
            add(0.0);
        }};

        try {
            d.insertValues(listInvalidValues);
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery ("SELECT * FROM weather");

                while (rs.next()) {
                    assertEquals(listValues.get(0), rs.getInt(1));
                    assertEquals(listValues.get(1), rs.getString(2));
                    assertEquals(listValues.get(2), rs.getDouble(3));
                    assertEquals(listValues.get(3), rs.getDouble(4));
                }

                s.close();
                rs.close();
            }
            conn.close();
        } catch (Exception e) {
            assertTrue(true);
            System.err.println("Exception thrown");
        }
    }


    @Test
    public void testNullValueInsertion() {
        List<Object> listInvalidValues = new ArrayList<>(){{
            add(null);
            add("city2");
            add(21.0);
            add(0.0);
        }};

        try {
            d.insertValues(listInvalidValues);
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery ("SELECT * FROM weather");

                while (rs.next()) {
                    assertEquals(listValues.get(0), rs.getInt(1));
                    assertEquals(listValues.get(1), rs.getString(2));
                    assertEquals(listValues.get(2), rs.getDouble(3));
                    assertEquals(listValues.get(3), rs.getDouble(4));
                }

                s.close();
                rs.close();
            }
            conn.close();
        } catch (Exception e) {
            assertTrue(true);
            System.err.println("Exception thrown");
        }
    }

    @Test
    public void testTableDisplay() {
        List<Object> listValues2 = new ArrayList<>(){{
            add(4);
            add("city2");
            add(21.0);
            add(0.0);
        }};

        try {
            d.insertValues(listValues);
            d.insertValues(listValues2);

            d.displayDB();
            assertEquals("Values have been added to database\n" +
                            "Values have been added to database\n" +
                            "3 - city - 20.0 - 3.0\n" +
                            "4 - city2 - 21.0 - 0.0\n",
                    outContent.toString());
        } catch (Exception e) {
            System.err.println("Exception thrown");
        }
    }

    @Test
    public void testTableDisplayOrderedByCity() {
        List<Object> listValues2 = new ArrayList<>(){{
            add(4);
            add("a");
            add(21.0);
            add(0.0);
        }};

        try {
            d.insertValues(listValues);
            d.insertValues(listValues2);

            d.displayDBOrderedBy("city");
            assertEquals("Values have been added to database\n" +
                            "Values have been added to database\n" +
                            "4 - a - 21.0 - 0.0\n" +
                            "3 - city - 20.0 - 3.0\n",
                    outContent.toString());
        } catch (Exception e) {
            System.err.println("Exception thrown");
        }
    }

    @Test
    public void testTableDisplayOrderedByTemperature() {
        List<Object> listValues2 = new ArrayList<>(){{
            add(4);
            add("city2");
            add(6.0);
            add(0.0);
        }};

        try {
            d.insertValues(listValues);
            d.insertValues(listValues2);

            d.displayDBOrderedBy("current_temperature");
            assertEquals("Values have been added to database\n" +
                            "Values have been added to database\n" +
                            "4 - city2 - 6.0 - 0.0\n" +
                            "3 - city - 20.0 - 3.0\n",
                    outContent.toString());
        } catch (Exception e) {
            System.err.println("Exception thrown");
        }
    }

    @Test
    public void testTableDisplayOrderedByInvalid() {
        List<Object> listValues2 = new ArrayList<>(){{
            add(4);
            add("city2");
            add(6.0);
            add(0.0);
        }};

        try {
            d.insertValues(listValues);
            d.insertValues(listValues2);

            d.displayDBOrderedBy("inexistant_column");
            assertEquals("Values have been added to database\n" +
                            "Values have been added to database\n",
                    outContent.toString());
        } catch (Exception e) {
            System.err.println("Exception thrown");
        }

    }

    @Test
    public void testFinding() {
        try {
            d.insertValues(listValues);

            assertTrue(d.findInDB("city"));
            assertFalse(d.findInDB("Mexico"));
        } catch (Exception e) {
            System.err.println("Exception thrown");
        }
    }


}
