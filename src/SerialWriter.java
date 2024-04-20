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
            int c = 0;
            while ((c = System.in.read()) > -1) {
                this.out.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
