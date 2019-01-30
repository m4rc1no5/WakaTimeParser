package pl.marceen.wakatimeparser.worker;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.login.control.LoginFormParser;
import pl.marceen.wakatimeparser.parser.control.TimeMachine;
import pl.marceen.wakatimeparser.parser.control.UrlBuilder;

import javax.json.bind.JsonbBuilder;

/**
 * @author Marcin Zaremba
 */
@ExtendWith(MockitoExtension.class)
class ProcessWorker {
    private static final Logger logger = LoggerFactory.getLogger(ProcessWorker.class);

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private TimeMachine timeMachine;

    @InjectMocks
    private UrlBuilder urlBuilder;

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
                .url(urlBuilder.urlForLogin())
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
                .url(urlBuilder.urlForLogin())
                .post(multipartBody)
                .header("referer", urlBuilder.urlForReferer())
                .build();
    }

    private Request buildSummaryRequest() {
        return new Request.Builder()
                .url(urlBuilder.urlForSummary())
                .get()
                .build();
    }
}
