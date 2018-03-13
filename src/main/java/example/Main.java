package example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main
{
    private static final Logger log = LogManager.getLogger(Main.class);

    private static final Logger log2 = LogManager.getLogger("Special Logger");


    public static void main(String[] args) {
        //TODO
        log.info("Hello");
        log2.info("Test");
        try {
            // Weird bug in Async Loggers if the program does not run long enough
            // TODO
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO
        }
    }

}
