import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * rtu 데이터 받기
 */
public class SerialReader implements Runnable{

    InputStream in;
    DBCon dbCon;

    public SerialReader(InputStream in) {
        this.in = in;
        this.dbCon = new DBCon();
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = this.in.read(buffer)) > -1) {
                System.out.print(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToDatabase(String data){
        try {
            // DB 연결
            dbCon.dbOpen();

            // 데이터 삽입 쿼리 작성
            String query = "INSERT INTO 테이블명 (컬럼명) VALUES (?)";

            // 쿼리 실행을 위한 PreparedStatement 생성
            PreparedStatement pstmt = dbCon.DB_con.prepareStatement(query);
            pstmt.setString(1, data);

            // 쿼리 실행
            pstmt.executeUpdate();

            // 자원 해제
            pstmt.close();
            dbCon.dbClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
