// import java.util.ArrayList;
// import java.util.List;
// import java.util.Queue;
// import java.util.concurrent.ConcurrentLinkedQueue;

// public class Router {
//     private List<Semaphore> connections;
//     private Queue<Device> waitingDevices;

//     public Router(int maxConnections) {
//         connections = new ArrayList<>();
//         waitingDevices = new ConcurrentLinkedQueue<>();
//         for (int i = 0; i < maxConnections; i++) {
//             connections.add(new Semaphore(1));
//         }
//     }

//     public void connect(Device device) throws InterruptedException {
//         for (Semaphore connection : connections) {
//             if (connection.tryAcquire()) {
//                 device.setConnection(connections.indexOf(connection) + 1);
//                 Logger.log("- Connection " + device.getConnection() + ": " +
//                         device.getName() + " Occupied");
//                 return;
//             }
//         }
//         if (!waitingDevices.contains(device)) {
//             Logger.log("- " + device.getName() + " (" + device.getType() + ") arrived and waiting");

//             waitingDevices.add(device);
//         }

//         device.waitToConnect();
//         connect(device); // Retry connecting once a connection is released
//     }

//     public void releaseConnection(Device device) {
//         Logger.log("- Connection " + device.getConnection() + ": " +
//                 device.getName() + " Logged out");

//         connections.get(device.getConnection() - 1).release();
//         synchronized (this) {
//             notifyAll(); // Notify all waiting devices
//         }
//     }
// }