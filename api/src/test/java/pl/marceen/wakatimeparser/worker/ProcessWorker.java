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

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

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
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        logger.info("Getting csrf token");
        String responseLoginForm = httpClient.sendAsync(buildRequestForLoginForm(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();

//        logger.info("Response: {}", responseLoginForm);
//        write("loginForm.html", responseLoginForm);

        String csrfToken = loginFormParser.findCsrfToken(responseLoginForm);

        String responseLogin = httpClient.sendAsync(
                        buildRequestForLogin(csrfToken, config.getLogin(), config.getPassword()),
                        HttpResponse.BodyHandlers.ofString()
                )
                .thenApply(HttpResponse::body)
                .get();

        logger.info("Response: {}", responseLogin);
//        write("login.html", responseLogin);
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

    private HttpRequest buildRequestForLogin(String csrfToken, String login, String password) {
        String post = String.format("csrftoken=%s&email=%s&password=%s", csrfToken, encode(login, UTF_8), encode(password, UTF_8));

        logger.info("POST: {}", post);

        return HttpRequest.newBuilder()
                .uri(URI.create("https://wakatime.com/login"))
//                .timeout(Duration.ofMinutes(1))
                .headers(
//                        "Host", "wakatime.com",
                        "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0",
                        "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                        "Accept-Language", "pl,en-US;q=0.7,en;q=0.3",
                        "Accept-Encoding", "gzip, deflate, br",
//                        "Referer", "https://wakatime.com/",
                        "Content-Type", "application/x-www-form-urlencoded",
                        "Upgrade-Insecure-Requests", "1",
                        "Pragma", "no-cache",
                        "Cache-Control", "no-cache",
                        "TE", "Trailers"
                )
                .POST(HttpRequest.BodyPublishers.ofString(post))
                .build();
    }
}
