import java.util.*;

public class ConcurrencyTest{
    public static void compute(long n) {
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result += i*i;
        }
    }

    public static void runThreads(int taskNumber, long n ) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < taskNumber; i++) {
            Thread thread = new Thread(()->{compute(n);});
            thread.start()

        }
    }
}