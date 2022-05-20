public class App {

    private static final Object monitor = new Object();
    private static int counter = 1;
    private static final int THREAD_AMOUNT = 3;

    public static void main(String[] args) {
        createThreadWithLetter('A', 1);
        createThreadWithLetter('B', 2);
        createThreadWithLetter('C', 3);
    }

    private static void createThreadWithLetter(char letter, int priority) {
        new Thread(() -> {
            synchronized (monitor) {
                int reminder = priority % THREAD_AMOUNT;

                for (int i = 0; i < THREAD_AMOUNT; i++) {
                    while (counter % THREAD_AMOUNT != reminder) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    counter++;
                    System.out.print(letter);
                    monitor.notifyAll();
                }
            }
        }).start();
    }
}
