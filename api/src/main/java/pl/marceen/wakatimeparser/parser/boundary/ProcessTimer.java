package pl.marceen.wakatimeparser.parser.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.parser.control.ProcessFacade;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * @author Marcin Zaremba
 */
@Singleton
public class ProcessTimer {
    private static final Logger logger = LoggerFactory.getLogger(ProcessTimer.class);

    @Inject
    private ProcessFacade processFacade;

    @Schedule(persistent = false, hour = "*")
    public void process() {
        logger.info("Process START");

        processFacade.process();

        logger.info("Process FINISH");
    }
}
