package pl.marceen.wakatimeparser.parser.control;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.login.control.LoginProcessor;
import pl.marceen.wakatimeparser.network.entity.NetworkException;

import javax.inject.Inject;

/**
 * @author Marcin Zaremba
 */
public class ProcessFacade {
    private static final Logger logger = LoggerFactory.getLogger(ProcessFacade.class);

    @Inject
    private OkHttpClient httpClient;

    @Inject
    private LoginProcessor loginProcessor;

    public void process(String login, String password) throws NetworkException {
        logger.info("Process START");

        loginProcessor.login(httpClient, login, password);

        // TODO: 2019-02-08 summaryProcessor

        // TODO: 2019-02-11 save result

        logger.info("Process FINISH");
    }
}
