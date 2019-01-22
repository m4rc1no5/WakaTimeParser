package pl.marceen.wakatimeparser.worker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.login.control.LoginFormParser;

import javax.json.bind.JsonbBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @author Marcin Zaremba
 */
class ProcessWorker {
    private static final Logger logger = LoggerFactory.getLogger(ProcessWorker.class);

    private LoginFormParser loginFormParser;

    @BeforeEach
    void setUp() {
        loginFormParser = new LoginFormParser();
    }

    @Test
    @Disabled
    void process() throws Exception {
        logger.info("Process - START");

        AuthConfig config = convertJsonToAuthConfig(FileReader.read(getClass(), "config/auth.json"));
        logger.info("Config: {}", config);

        HttpClient httpClient = HttpClient.newBuilder()
                .build();

        logger.info("Getting csrf token");
        String responseLoginForm = httpClient.sendAsync(buildRequestForLoginForm(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();

//        logger.info("Response: {}", responseLoginForm);
//        write("loginForm.html", responseLoginForm);

        String csrfToken = loginFormParser.findCsrfToken(responseLoginForm);

        // TODO: 2019-01-20 login into panel (POST with form)
    }

    private AuthConfig convertJsonToAuthConfig(String json) {
        return JsonbBuilder.create().fromJson(json, AuthConfig.class);
    }

    private HttpRequest buildRequestForLoginForm() {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://wakatime.com/login"))
                .timeout(Duration.ofMinutes(1))
                .build();
    }
}
