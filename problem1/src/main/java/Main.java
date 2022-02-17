import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main implements Runnable {

    ElevatorRequestProcess requestProcess;
    static int nFloor = 5;
    static int mElevator = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Elevator> elevatorList = Stream.generate(Elevator::new).limit(mElevator).collect(Collectors.toList());

        ElevatorRequestProcess requestProcess = new ElevatorRequestProcess(elevatorList);

        List<Main> MainList = Stream.generate(() -> new Main(requestProcess)).limit(nFloor).collect(Collectors.toList());

        for (Main Main : MainList) {
            new Thread(Main).start();
        }

    }

    public Main() {
    }

    @Override
    public void run() {
        while (true) {
            try {
                int randomEleIndex = (int) (Math.random() * ((mElevator)));
                int destFloor = 1 + (int) (Math.random() * ((nFloor + 1)));
                int currentFloor = 1 + (int) (Math.random() * ((nFloor + 1)));
                while (currentFloor == destFloor) currentFloor = 1 + (int) (Math.random() * ((nFloor + 1)));
                requestProcess.pushRequest(randomEleIndex, destFloor, currentFloor);
                Thread.sleep(100000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Main(ElevatorRequestProcess requestProcess) {
        this.requestProcess = requestProcess;
    }
}
