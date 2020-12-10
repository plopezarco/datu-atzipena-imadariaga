import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author aritz
 */
public class Connect {
     /**
     * Connect to a sample database
     */
    static String url = "";
    static  String db_izena = "datubaseak/db1.db";
    public static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            // jdbc:sqlite:C:/sqlite/db/chinook.db
            //String url = "";
            url = "jdbc:sqlite:" + db_izena;
            
            // create a connection to the database
            
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }/* finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        */
        return conn;
    }
    
     public static void createNewDatabase() {

        //String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        
        connect();

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " ==> " + e.getMessage());
        }
    }
     
      public static void createNewTable() {
        // SQLite connection string
        //String url = "jdbc:sqlite:C://sqlite/db/tests.db";
        
         connect();
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS tabla1 (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	eremu1 text NOT NULL\n"       
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " ==> " + e.getMessage());
        }
    }
      //INSERT
      //Error 19  datos duplicados  
      //Error 1 no existe la columna,tabla etc
      public static void insert(int id, String eremu1 ) {
        String sql = "INSERT INTO tabla1(id,eremu1) VALUES(?,?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, eremu1);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            if(e.getErrorCode() == 19)
            {
                System.out.println("Constraint errorea");
            }
            else
            {
                System.out.println(e.getErrorCode() + " ==> " + e.getMessage());
            }
            
            //System.out.println(e.getMessage());
        }
    }
      //UPDATE 
      public static void update(String eremu1 ,int id) {
        String sql = "UPDATE tabla1 SET eremu1 = ?" + " WHERE id = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, eremu1);
            pstmt.setInt(2, id);
     
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            if(e.getErrorCode() == 19)
            {
                System.out.println("Constraint errorea");
            }
            else
            {
                System.out.println(e.getErrorCode() + " ==> " + e.getMessage());
                System.out.println(sql);
            }
            
            //System.out.println(e.getMessage());
        }
    }
      
      public static void selectAll(){
        String sql = "SELECT id, eremu1 FROM tabla1";
        
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("eremu1"));
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " ==> " + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        createNewDatabase();
        createNewTable();
        insert(1, "Eneko");
        insert(2, "Jorge");
        insert(3, "Mikel");
        selectAll();
        update("Anatoli" ,1);
        selectAll();
        
    }
}