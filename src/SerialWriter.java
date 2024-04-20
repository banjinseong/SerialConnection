import java.io.IOException;
import java.io.OutputStream;

/**
 * rtu한테 데이터 달라고 요청하기
 */
public class SerialWriter implements Runnable{

    OutputStream out;

    public SerialWriter(OutputStream out) {
        this.out = out;
    }

    public void run() {
        try {
            // 시리얼 포트로 데이터 요청 전송
            String requestData = "Your request data"; // 요청할 데이터
            byte[] requestDataBytes = requestData.getBytes(); // 요청 데이터를 바이트 배열로 변환
            this.out.write(requestDataBytes); // 시리얼 포트로 요청 데이터 전송
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
