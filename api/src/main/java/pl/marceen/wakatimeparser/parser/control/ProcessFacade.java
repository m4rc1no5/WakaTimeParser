package pl.marceen.wakatimeparser.parser.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.login.control.LoginProcessor;

import javax.inject.Inject;

/**
 * @author Marcin Zaremba
 */
public class ProcessFacade {
    private static final Logger logger = LoggerFactory.getLogger(ProcessFacade.class);

    @Inject
    private LoginProcessor loginProcessor;

    public void process(String login, String password) {
        logger.info("Process START");

        // TODO: 2019-02-11 create http client

        loginProcessor.login(login, password);

        // TODO: 2019-02-08 summaryProcessor

        // TODO: 2019-02-11 save result

        logger.info("Process FINISH");
    }
}
