import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentCounter {
    private static final int ITERATIONS = 10;

    public static void main(String[] args) throws InterruptedException {
        ConcurrentCounter myCounter = new ConcurrentCounter();
        myCounter.start();
    }

    private void start() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Counter counter1 = new Counter(lock);
        Counter counter2 = new Counter(lock);
        Counter counter3 = new Counter(lock);

        Thread thread1 = new Thread(counter1);
        Thread thread2 = new Thread(counter2);
        Thread thread3 = new Thread(counter3);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    private class Counter implements Runnable{
        private int value;
        private Lock lock;

        public Counter(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < ITERATIONS; i++) {
                incrementAndGet();
            }
        }

        public void incrementAndGet() {
            try {
                lock.lock();
                this.value += 1;
                System.out.println(this.value);
            } finally {
                lock.unlock();
            }
        }
    }
}
