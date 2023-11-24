import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Network {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the number of WI-FI Connections?");
        int maxConnections = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("What is the number of devices Clients want to connect?");
        int totalDevices = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Router router = new Router(maxConnections);
        List<Device> devices = new ArrayList<>();

        for (int i = 0; i < totalDevices; i++) {
            System.out.println("Enter the name and type of device " + (i + 1) + ":");
            String name = scanner.next();
            String type = scanner.next();
            Device device = new Device(name, type, router);
            devices.add(device);
        }

        for (Device device : devices) {
            Thread thread = new Thread(device);
            thread.start();
        }
    }
}
