package pl.marceen.wakatimeparser.worker;

import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.login.control.LoginFormParser;

import javax.json.bind.JsonbBuilder;
import java.time.LocalDate;

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
    void work() throws Exception {
        OkHttpClient client = OkHttpClientProducer.produce();

        AuthConfig config = convertJsonToAuthConfig(FileReader.read(getClass(), "config/auth.json"));
        logger.info("Config: {}", config);

        logger.info("Getting CSRF Token");
        String csrfResponse = client.newCall(buildCsrfRequest()).execute().body().string();
        String csrfToken = loginFormParser.findCsrfToken(csrfResponse);

        logger.info("Logging");
        client.newCall(buildLoginRequest(csrfToken, config.getLogin(), config.getPassword())).execute();

        logger.info("Getting summary");
        String summaryResponse = client.newCall(buildSummaryRequest()).execute().body().string();
        logger.info("Summary: {}", summaryResponse);
    }

    private AuthConfig convertJsonToAuthConfig(String json) {
        return JsonbBuilder.create().fromJson(json, AuthConfig.class);
    }

    private Request buildCsrfRequest() {
        return new Request.Builder()
                .url("https://wakatime.com/login")
                .get()
                .build();
    }

    private Request buildLoginRequest(String csrfToken, String email, String password) {
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("csrftoken", csrfToken)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();

        return new Request.Builder()
                .url("https://wakatime.com/login")
                .post(multipartBody)
                .header("referer", "https://wakatime.com/")
                .build();
    }

    private Request buildSummaryRequest() {
        LocalDate now = LocalDate.now();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("wakatime.com")
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("users")
                .addPathSegment("current")
                .addPathSegment("summaries")
                .addQueryParameter("start", now.minusDays(14).toString())
                .addQueryParameter("end", now.toString())
                .build();

        return new Request.Builder()
                .url(httpUrl.toString())
                .get()
                .build();
    }
}
