package pl.marceen.wakatimeparser.login.control;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.network.control.HttpExcecutor;
import pl.marceen.wakatimeparser.network.entity.NetworkException;

import javax.inject.Inject;

/**
 * @author Marcin Zaremba
 */
public class LoginProcessor {
    private static final Logger logger = LoggerFactory.getLogger(LoginProcessor.class);

    @Inject
    private HttpExcecutor<String> httpExcecutor;

    @Inject
    private RequestBuilder requestBuilder;

    @Inject
    private LoginFormParser loginFormParser;

    public void login(OkHttpClient httpClient, String login, String password) throws NetworkException {
        logger.info("Login START");

        Request csrfRequest = requestBuilder.buildCsrfRequest();
        String csrfResponse = httpExcecutor.execute(httpClient, csrfRequest);
        String csrfToken = loginFormParser.findCsrfToken(csrfResponse);

        Request loginRequest = requestBuilder.buildLoginRequest(csrfToken, login, password);
        httpExcecutor.execute(httpClient, loginRequest);

        logger.info("Login FINISH");
    }
}
