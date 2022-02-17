import java.util.Arrays;
import java.util.LinkedList;

public class Elevator implements Runnable{

    private int index;
    private boolean isUpDirection;
    private LinkedList<Integer> floorTask;
    private int currentFloor;
    private volatile boolean isRunning;

    public void pushFloor(Integer floorDest, Integer personFloor) {
        synchronized (this) {
            isRunning = true;
            floorTask.add(personFloor);
            floorTask.add(floorDest);
            System.out.println("Elevator " + index + " receive request to floor: " + floorDest + ", taskList: " + Arrays.toString(floorTask.toArray()));
            isUpDirection = floorTask.getFirst() > currentFloor;
        }
    }

    @Override
    public void run() {
        // elevator start running
        while (true) {
            try {
                Thread.sleep(1000);
                currentFloor += (isUpDirection ? 1 : -1);
                System.out.println("Elevator " + index + " in floor " + currentFloor + " is running up: " + isUpDirection + " - remain task: " + Arrays.toString(floorTask.toArray()));

                if (currentFloor == floorTask.getFirst()) {
                    floorTask.removeFirst();
                    if (floorTask.isEmpty()) {
                        System.out.println("Elevator " + index + " has stopped!!!");
                        currentFloor = 1;
                        isRunning = false;
                        break;
                    }
                    if (currentFloor == floorTask.getFirst()) floorTask.removeFirst();
                    System.out.println("Elevator " + index + " in floor " + currentFloor);
                    if (currentFloor > floorTask.getFirst())
                        isUpDirection = false;
                    else if (currentFloor < floorTask.getFirst())
                        isUpDirection = true;
                    else {
                        isUpDirection = !isUpDirection;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Elevator() {
        floorTask = new LinkedList<>();
        currentFloor = 1;
        isUpDirection = true;
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Elevator(boolean isUpDirection, LinkedList<Integer> floorTask, int currentFloor) {
        this.isUpDirection = isUpDirection;
        this.floorTask = floorTask;
        this.currentFloor = currentFloor;
    }

    public boolean isUpDirection() {
        return isUpDirection;
    }

    public void setUpDirection(boolean upDirection) {
        isUpDirection = upDirection;
    }

    public LinkedList<Integer> getFloorTask() {
        return floorTask;
    }

    public void setFloorTask(LinkedList<Integer> floorTask) {
        this.floorTask = floorTask;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}
