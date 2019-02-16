package pl.marceen.wakatimeparser.parser.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.network.entity.NetworkException;
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

        // TODO: 2019-02-11 login and password to database or standalone.xml
        String login = "";
        String password = "";

        try {
            processFacade.process(login, password);
        } catch (NetworkException e) {
            e.printStackTrace();
        }

        logger.info("Process FINISH");
    }
}
