public class Device implements Runnable {
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
            router.notify();
        }
    }

    @Override
    public void run() {
        try {
            router.connect(this);
            System.out.println("- Connection " + connection + ": " + name + " login");
            System.out.println("- Connection " + connection + ": " + name + " performs online activity");
            Thread.sleep((long) (Math.random() * 3000)); // Simulate online activity
            router.releaseConnection(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
