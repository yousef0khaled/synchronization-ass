// import java.io.BufferedWriter;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;

// public class Network {
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         System.out.println("What is the number of WI-FI Connections?");
//         int maxConnections = scanner.nextInt();
//         scanner.nextLine(); // Consume newline

//         System.out.println("What is the number of devices Clients want to connect?");
//         int totalDevices = scanner.nextInt();
//         scanner.nextLine(); // Consume newline

//         Router router = new Router(maxConnections);
//         List<Device> devices = new ArrayList<>();

//         for (int i = 0; i < totalDevices; i++) {
//             String name = scanner.next();
//             String type = scanner.next();
//             Device device = new Device(name, type, router);
//             devices.add(device);
//         }

//         try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
//             for (Device device : devices) {
//                 Thread thread = new Thread(device);
//                 thread.start();
//                 // writer.write(device.getName() + "(" + device.getType() + ") arrived\n");
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         scanner.close();
//     }
// }
