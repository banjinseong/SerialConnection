import java.io.IOException;
import java.sql.*;

/**
 * db연결
 * jdbc 라이브러리 다운받아야함
 */
public class DBCon {
    String strDriver = "com.mysql.cj.jdbc.Driver";
    String strURL = "jdbc:mysql://???????:3306/USER?useUnicode=true&serverTimezone=UTC";
    //String strURL = "jdbc:oracle:thin:localhost1521:";
    String strUser = "";
    String strPWD = "";

    Connection DB_con;
    Statement DB_stmt;
    ResultSet DB_rs;

    public void dbOpen() throws IOException {
        try{
            Class.forName(strDriver);

            DB_con = DriverManager.getConnection(strURL, strUser, strPWD);
            DB_stmt = DB_con.createStatement();
        }catch(Exception e){
            System.out.println("SQLExceptionopen : " + e.getMessage());
        }
    }
    public void dbClose() throws IOException {
        try{
            DB_stmt.close();
            DB_con.close();
        }catch(SQLException e){
            System.out.println("SQLException :sss " + e.getMessage());
        }
    }
}
