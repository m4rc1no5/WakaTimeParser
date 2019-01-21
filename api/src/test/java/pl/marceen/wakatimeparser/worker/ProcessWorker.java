package pl.marceen.wakatimeparser.worker;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.bind.JsonbBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static pl.marceen.wakatimeparser.worker.FileWriter.write;

/**
 * @author Marcin Zaremba
 */
class ProcessWorker {
    private static final Logger logger = LoggerFactory.getLogger(ProcessWorker.class);

    @Test
    @Disabled
    void process() throws Exception {
        logger.info("Process - START");

        AuthConfig config = convertJsonToAuthConfig(FileReader.read(getClass(), "config/auth.json"));
        logger.info("Config: {}", config);

        HttpClient httpClient = HttpClient.newBuilder()
                .build();

        logger.info("Getting csrf token");
        String response = httpClient.sendAsync(buildRequestForCsrfToken(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();

        logger.info("Response: {}", response);

        write("loginForm.html", response);

        // TODO: 2019-01-20 get csrftoken from login (GET)
        // TODO: 2019-01-20 login into panel (POST with form)
    }

    private AuthConfig convertJsonToAuthConfig(String json) {
        return JsonbBuilder.create().fromJson(json, AuthConfig.class);
    }

    private HttpRequest buildRequestForCsrfToken() {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://wakatime.com/login"))
                .timeout(Duration.ofMinutes(1))
                .build();
    }
}
