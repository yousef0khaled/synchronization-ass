import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Scanner;

public class Network {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the number of WI-FI Connections?");
        int maxConnections = scanner.nextInt();
        scanner.nextLine();

        System.out.println("What is the number of devices Clients want to connect?");
        int totalDevices = scanner.nextInt();
        scanner.nextLine();

        Router router = new Router(maxConnections);
        List<Device> devices = new ArrayList<>();

        for (int i = 0; i < totalDevices; i++) {
            String name = scanner.next();
            String type = scanner.next();
            Device device = new Device(name, type, router);
            devices.add(device);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (Device device : devices) {
                Thread thread = new Thread(device);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    public static class Semaphore {
        private int permits;

        public Semaphore(int initialPermits) {
            this.permits = initialPermits;
        }

        public synchronized void acquire() throws InterruptedException {
            while (permits == 0) {
                wait();
            }
            permits--;
        }

        public synchronized void release() {
            permits++;
            notify();
        }

        public synchronized boolean tryAcquire() {
            if (permits > 0) {
                permits--;
                return true;
            }
            return false;
        }
    }

    public static class Router {
        private List<Semaphore> connections;
        private Queue<Device> waitingDevices;

        public Router(int maxConnections) {
            connections = new ArrayList<>();
            waitingDevices = new ConcurrentLinkedQueue<>();
            for (int i = 0; i < maxConnections; i++) {
                connections.add(new Semaphore(1));
            }
        }

        public void connect(Device device) throws InterruptedException {
            for (Semaphore connection : connections) {
                if (connection.tryAcquire()) {
                    device.setConnection(connections.indexOf(connection) + 1);
                    Logger.log("- Connection " + device.getConnection() + ": " +
                            device.getName() + " Occupied");
                    return;
                }
            }
            if (!waitingDevices.contains(device)) {
                Logger.log("- " + device.getName() + " (" + device.getType() + ") arrived and waiting");

                waitingDevices.add(device);
            }

            device.waitToConnect();
            connect(device);
        }

        public void releaseConnection(Device device) {
            Logger.log("- Connection " + device.getConnection() + ": " +
                    device.getName() + " Logged out");

            connections.get(device.getConnection() - 1).release();
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public static class Device implements Runnable {
        private String name;
        private String type;
        private int connection;
        private Router router;

        public Device(String name, String type, Router router) {
            this.name = name;
            this.type = type;
            this.router = router;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getConnection() {
            return connection;
        }

        public void setConnection(int num) {
            connection = num;
        }

        public void waitToConnect() {
            // Wait until a connection is released
            synchronized (router) {
                try {
                    router.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void finish() {
            synchronized (router) {
                router.notifyAll();
            }
        }

        @Override
        public void run() {
            try {
                router.connect(this);
                Logger.log("- Connection " + connection + ": " + name + " login");
                Logger.log("- Connection " + connection + ": " + name + " performs online activity");
                Thread.sleep((long) (Math.random() * 3000));
                router.releaseConnection(this);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static class Logger {
        private static final String LOG_FILE = "output.txt";

        public static synchronized void log(String message) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
                writer.write(message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
