package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        List<Future<String>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            for (int i = 0; i < 20; i++) {
                Future<String> future = executor.submit(new MyThread());
                futures.add(future);
            }

            for (Future<String> future : futures) {
                try {
                    String result = future.get();
                    logger.info(result);
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Error while executing task", e);
                }
            }
        } finally {
            executor.shutdown();
        }
    }
}
