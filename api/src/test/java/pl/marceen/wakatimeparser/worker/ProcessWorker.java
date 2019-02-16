package pl.marceen.wakatimeparser.worker;

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
import pl.marceen.wakatimeparser.login.control.LoginProcessor;
import pl.marceen.wakatimeparser.login.control.RequestBuilder;
import pl.marceen.wakatimeparser.network.control.HttpClientProducer;
import pl.marceen.wakatimeparser.network.control.HttpExcecutor;
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

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private HttpExcecutor httpExcecutor;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private RequestBuilder requestBuilder;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private LoginFormParser loginFormParser;

    @InjectMocks
    private LoginProcessor loginProcessor;

    @BeforeEach
    void setUp() {
        requestBuilder.setUrlBuilder(urlBuilder);
    }

    @Test
    @Disabled
    void work() throws Exception {
        HttpClientProducer httpClientProducer = new HttpClientProducer();
        OkHttpClient client = httpClientProducer.produce();

        AuthConfig config = convertJsonToAuthConfig(FileReader.read(getClass(), "config/auth.json"));
        logger.info("Config: {}", config);

        loginProcessor.login(client, config.getLogin(), config.getPassword());

        logger.info("Getting summary");
        String summaryResponse = client.newCall(buildSummaryRequest()).execute().body().string();
        logger.info("Summary: {}", summaryResponse);
    }

    private AuthConfig convertJsonToAuthConfig(String json) {
        return JsonbBuilder.create().fromJson(json, AuthConfig.class);
    }

    private Request buildSummaryRequest() {
        return new Request.Builder()
                .url(urlBuilder.urlForSummary())
                .get()
                .build();
    }
}
