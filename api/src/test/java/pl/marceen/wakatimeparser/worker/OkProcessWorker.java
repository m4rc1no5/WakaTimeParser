package pl.marceen.wakatimeparser.worker;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.wakatimeparser.login.control.LoginFormParser;

import javax.json.bind.JsonbBuilder;

/**
 * @author Marcin Zaremba
 */
class OkProcessWorker {
    private static final Logger logger = LoggerFactory.getLogger(OkProcessWorker.class);

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
        ResponseBody csrfResponseBody = client.newCall(buildCsrfRequest()).execute().body();
        String csrfResponse = csrfResponseBody.string();

        String csrfToken = loginFormParser.findCsrfToken(csrfResponse);
        logger.info("CSRF Token: {}", csrfToken);

        String loginResponse = client.newCall(buildLoginRequest(csrfToken, config.getLogin(), config.getPassword())).execute().body().string();
        logger.info("Login response: {}", loginResponse);

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
}
