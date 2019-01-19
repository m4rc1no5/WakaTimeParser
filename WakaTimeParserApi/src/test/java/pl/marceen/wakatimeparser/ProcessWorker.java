package pl.marceen.wakatimeparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Marcin Zaremba
 */
class ProcessWorker {
    private static final Logger logger = LoggerFactory.getLogger(ProcessWorker.class);

    @Test
    void process() {
        logger.info("kabooom!");

        Assertions.assertEquals("aaaa", "aaaa", "kabooom");
    }
}
