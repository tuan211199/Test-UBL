import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ElevatorRequestProcess {

    private final List<Elevator> elevatorsTask;

    private final ExecutorService elevatorConsume;

    public ElevatorRequestProcess(List<Elevator> elevators) {
        elevatorsTask = Collections.synchronizedList(elevators);
        elevatorConsume = Executors.newFixedThreadPool(elevatorsTask.size());
    }

    public void startElevator(int index, int floor, int currentFloor) {
        System.out.println("Elevator " + index + " start...");
        elevatorsTask.get(index).setRunning(true);
        elevatorsTask.get(index).setIndex(index);
        elevatorsTask.get(index).pushFloor(floor, currentFloor);
        elevatorConsume.submit(elevatorsTask.get(index));
    }

    public void pushRequest(int index, int floor, int currentFloor) {
        synchronized (this) {
            boolean isRunningUp = currentFloor < floor;
            List<Elevator> availableElevator = elevatorsTask.stream()
                    .filter(t -> {
                        if (t.isRunning()) {
                            if (isRunningUp && t.isUpDirection()) {
                                return t.getCurrentFloor() <= currentFloor;
                            } else if (!isRunningUp && !t.isUpDirection()) {
                                return t.getCurrentFloor() >= currentFloor;
                            }
                        }
                        return false;
                    }).collect(Collectors.toList());
            if (availableElevator.isEmpty()) {
                System.out.println("Person in floor " + currentFloor + " request to floor " + floor + " in elevator: " + index);
                if (!elevatorsTask.get(index).isRunning())
                    startElevator(index, floor, currentFloor);
                else elevatorsTask.get(index).pushFloor(floor, currentFloor);
            } else {
                System.out.println("Person in floor " + currentFloor + " request to floor " + floor + " in elevator: "
                        + elevatorsTask.indexOf(availableElevator.get(0)));
                availableElevator.get(0).pushFloor(floor, currentFloor);
            }
        }
    }
}
