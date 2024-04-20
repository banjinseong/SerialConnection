import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class SerialTest {

    void manageSerialCommunication(String portName) {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

            if (portIdentifier.isCurrentlyOwned()) {
                System.out.println("Error: Port is currently in use");
                return;
            }

            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                // 요청을 보내는 쓰레드
                Thread writerThread = new Thread(new SerialWriter(out));
                writerThread.start();

                // 응답을 받는 쓰레드
                Thread readerThread = new Thread(new SerialReader(in));
                readerThread.start();

                // 스레드가 모두 종료될 때까지 대기
                writerThread.join();
                readerThread.join();

                // 작업 완료 후 리소스 닫기
                in.close();
                out.close();
                serialPort.close();
            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
