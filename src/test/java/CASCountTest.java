import org.junit.jupiter.api.Test;
import ru.job4j.CASCount;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.*;

public class CASCountTest {

    @Test
    void testSingleThreadIncrement() {
        CASCount counter = new CASCount();
        counter.increment();
        assertEquals(1, counter.get());
    }

    @Test
    void testMultipleThreadsIncrement() throws InterruptedException {
        int numThreads = 10;
        int incrementsPerThread = 1000;
        CASCount counter = new CASCount();
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();
        assertEquals(numThreads * incrementsPerThread, counter.get());
    }

    @Test
    void testGetReturnsCorrectValue(){
        CASCount counter = new CASCount();
        counter.increment();
        assertEquals(1, counter.get());
        counter.increment();
        counter.increment();
        assertEquals(3, counter.get());
    }

    @Test
    void testInitialValue(){
        CASCount counter = new CASCount();
        assertEquals(0, counter.get());
    }

    @Test
    void testConsecutiveIncrements(){
        CASCount counter = new CASCount();
        counter.increment();
        counter.increment();
        counter.increment();
        assertEquals(3, counter.get());
    }

}