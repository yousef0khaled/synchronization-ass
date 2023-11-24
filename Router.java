import java.util.ArrayList;
import java.util.List;

public class Router {
    private List<Semaphore> connections;

    public Router(int maxConnections) {
        connections = new ArrayList<>();
        for (int i = 0; i < maxConnections; i++) {
            connections.add(new Semaphore(1));
        }
    }

    public void connect(Device device) throws InterruptedException {
        for (Semaphore connection : connections) {
            if (connection.tryAcquire()) {
                System.out.println("- Connection " + (connections.indexOf(connection) + 1) + ": " +
                        device.getName() + " Occupied");
                return;
            }
        }
        System.out.println("- " + device.getName() + "(" + device.getType() + ") arrived and waiting");
        // If all connections are occupied, device waits
        device.waitToConnect();
        connect(device); // Retry connecting once a connection is released
    }

    public void releaseConnection(Device device) {
        System.out.println("- Connection " + device.getConnection() + ": " +
                device.getName() + " Logged out");
        connections.get(device.getConnection() - 1).release();
        device.finish(); // Signal the device that it finished
    }
}
