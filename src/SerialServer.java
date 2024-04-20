import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SerialServer {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final String portName;

    public SerialServer(String portName) {
        this.portName = portName;
    }

    public void start() {
        //scheduler.scheduleAtFixedRate(() -> manageSerialCommunication(), 0, 10, TimeUnit.SECONDS);
        //scheduler.scheduleAtFixedRate(this::manageSerialCommunication, 0, 10, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(() -> {
            ScheduledFuture<?> future = scheduler.schedule(this::manageSerialCommunication, 0, TimeUnit.SECONDS);
            // 작업이 일정 시간 내에 완료되지 않으면 취소
            scheduler.schedule(() -> {
                if (!future.isDone()) {
                    future.cancel(true);
                }
            }, 5, TimeUnit.SECONDS); // 예를 들어 5초 후에 취소하도록 설정
        }, 0, 10, TimeUnit.SECONDS);// 10 초 마다 반복.

    }

    private void manageSerialCommunication() {
        try {
            // SerialTest의 manageSerialCommunication 메서드 호출
            SerialTest serialTest = new SerialTest();
            serialTest.manageSerialCommunication(portName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String portName = "COM1"; // 시리얼 포트 이름에 맞게 수정
        SerialServer serialServer = new SerialServer(portName);
        serialServer.start();
    }
}