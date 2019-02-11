package pl.marceen.wakatimeparser.login.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.network.control.HttpExcecutor;

import javax.inject.Inject;

/**
 * @author Marcin Zaremba
 */
public class LoginProcessor {
    private static final Logger logger = LoggerFactory.getLogger(LoginProcessor.class);

    @Inject
    private HttpExcecutor<String> httpExcecutor;

    public void login(String login, String password) {
        logger.info("Login START");

        // TODO: 2019-02-11 get csrf token and login into WakaTime

    }
}
