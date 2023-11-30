// import java.io.BufferedWriter;
// import java.io.FileWriter;
// import java.io.IOException;

// public class Logger {
//     private static final String LOG_FILE = "output.txt";

//     public static synchronized void log(String message) {
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
//             writer.write(message + "\n");
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }