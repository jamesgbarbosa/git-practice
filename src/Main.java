import com.sun.corba.se.impl.orbutil.concurrent.Sync;

public class Main {

    public static void main(String[] args) {
        Thread t = (new Thread(new HelloRunnable()));
        Thread t2 = (new Thread(new HelloRunnable2()));
        t.start();
        t2.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            t.stop();
            t2.stop();
        }
    }
}

class HelloRunnable implements Runnable {

    public void run() {
        SynchronizedCounter sc = SynchronizedCounter.getInstance();
        while(true) {
            try {
                Thread.sleep(50);
                sc.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Runnable1 " + sc.value());
            }
        }

    }
}


class HelloRunnable2 implements Runnable {

    public void run() {
        SynchronizedCounter sc = SynchronizedCounter.getInstance();
        while(true) {
            try {
                Thread.sleep(10);
                sc.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Runnable2 " + sc.value());
            }
        }

    }
}

class SynchronizedCounter {
    private int c = 0;
    private static SynchronizedCounter INSTANCE = new SynchronizedCounter();

    public static SynchronizedCounter getInstance() {
        return INSTANCE;
    }

    private SynchronizedCounter() {}

    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}