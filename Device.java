// public class Device implements Runnable {
//     private String name;
//     private String type;
//     private int connection;
//     private Router router;

//     public Device(String name, String type, Router router) {
//         this.name = name;
//         this.type = type;
//         this.router = router;
//     }

//     public String getName() {
//         return name;
//     }

//     public String getType() {
//         return type;
//     }

//     public int getConnection() {
//         return connection;
//     }

//     public void setConnection(int num) {
//         connection = num;
//     }

//     public void waitToConnect() {
//         // Wait until a connection is released
//         synchronized (router) {
//             try {
//                 router.wait();
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

//     public void finish() {
//         synchronized (router) {
//             router.notifyAll();
//         }
//     }

//     @Override
//     public void run() {
//         try {
//             router.connect(this);
//             Logger.log("- Connection " + connection + ": " + name + " login");
//             Logger.log("- Connection " + connection + ": " + name + " performs online activity");
//             Thread.sleep((long) (Math.random() * 3000)); // Simulate online activity
//             router.releaseConnection(this);

//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }
// }
