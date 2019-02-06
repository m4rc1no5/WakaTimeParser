package pl.marceen.wakatimeparser.parser.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Marcin Zaremba
 */
public class ProcessFacade {
    private static final Logger logger = LoggerFactory.getLogger(ProcessFacade.class);

    public void process() {
        logger.info("Process START");
        logger.info("Process FINISH");
    }
}
