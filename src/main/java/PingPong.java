import static java.lang.Thread.sleep;

public class PingPong {
    public static void main(String[] args) {
        PingPong pingPong = new PingPong();
        pingPong.start();
    }

    private void start(){
        Thread ping = new Thread(new Word(), "ping");
        ping.start();
        Thread pong = new Thread(new Word(), "pong");
        pong.start();
    }

    protected class Word implements Runnable{
        int n = 0;
        @Override
        public void run() {
            while (n <= 3){
                try {
                    printWord();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                n++;
            }
        }
    }

    protected synchronized void printWord() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        sleep(500);
        notify();
        wait();
    }
}
